#include "shortlink.h"
#include "log.h"
#include "c2java.h"
#include "httprequest.h"
#include "httpresponse.h"
#include "shotlinkmanager.h"
#include "blocksocket.h"
#include "threadpool.h"
#include "scopejenv.h"
#include "timeutil.h"
#include <map>
#include <utility>
#include <unistd.h>


const char *const ShortLink::TAG = "ShortLink";
const uint64_t ShortLink::kTimeoutMillis = 60 * 1000;
const size_t ShortLink::kRecvBuffSize = 1024;
const size_t ShortLink::kSendBuffSize = 20480;

ShortLink::ShortLink(Task &_task, std::string _svr_inet_addr, u_short _port)
        : status_(kNotStart)
        , task_(_task)
        , start_(::gettickcount())
        , curr_retry_cnt_(0)
        , port_(_port)
        , svr_inet_addr_(std::move(_svr_inet_addr))
        , err_code_(0)
        , socket_(INVALID_SOCKET) {
    LogI(TAG, "[ShortLink] %s %d %d", task_.cgi_.c_str(), task_.netid_, task_.retry_cnt_)
}


int ShortLink::DoTask() {
    ++curr_retry_cnt_;
    start_ = ::gettickcount();
    status_ = kRunning;
    ScopeJEnv scope_jenv;
    JNIEnv *env = scope_jenv.GetEnv();
    C2Java_ReqToBuffer(env, send_body_, GetNetId());
    async_ret_ = ThreadPool::Instance().Execute([this] () -> int {
        return this->__Run();
    });
    return 0;
}


int ShortLink::__Run() {
    DoConnect();
    if (err_code_ != 0) {
        status_ = kError;
        return err_code_;
    }
    __ReadWrite();
    return err_code_;
}


void ShortLink::DoConnect() {
    socket_ = socket(AF_INET, SOCK_STREAM, 0);
    if (socket_ <= 0) {
        status_ = kError;
        err_code_ |= (uint32_t) INVALID_SOCKET;
        return;
    }

    struct sockaddr_in sockaddr{};
    memset(&sockaddr, 0, sizeof(sockaddr));
    sockaddr.sin_family = AF_INET;
    sockaddr.sin_port = htons(5002);
    sockaddr.sin_addr.s_addr = inet_addr(svr_inet_addr_.c_str());

    if (int ret = connect(socket_, (struct sockaddr *) &sockaddr, sizeof(sockaddr)) < 0) {
        LogE(TAG, "Connect FAILED, errCode: %d", ret);
        CLOSE_SOCKET(socket_);
        err_code_ |= (uint32_t) CONNECT_FAILED;
        status_ = kError;
        return;
    }
    LogI(TAG, "connect SUCCEED!");
}


void ShortLink::__ReadWrite() {
    AutoBuffer out_buff;
    std::map<std::string, std::string> empty;
    http::request::Pack(svr_inet_addr_, task_.cgi_, empty, send_body_,
                        out_buff);
    LogI(TAG, "[__ReadWrite] http req header length: %zd", out_buff.Length() - send_body_.Length())
//    for (size_t i = 0; i < out_buff.Length() - send_body_.Length(); i++) {
//        LogI(TAG, "0x%x %c", *out_buff.Ptr(i), *out_buff.Ptr(i))
//    }
    send_body_.Reset();

    ScopeJEnv scope_jenv;
    JNIEnv *env = scope_jenv.GetEnv();

    ssize_t nsend = 0;
    ssize_t ntotal = out_buff.Length();
    while (true) {
        ssize_t n = BlockSocketSend(socket_, out_buff, kSendBuffSize);
        if (n < 0) {
            LogE(TAG, "[__ReadWrite] send, errno（%d）: %s", errno, strerror(errno));
            status_ = kError;
            err_code_ |= (uint32_t) SEND_FAILED;
            CLOSE_SOCKET(socket_);
            return;
        }
        nsend += n;
        LogI(TAG, "[__ReadWrite] send %d/%zd bytes", nsend, ntotal)
        if (task_.care_about_progress_) {
            C2Java_OnUploadProgress(env, GetNetId(), nsend, ntotal);
        }
        if (nsend >= ntotal) {
            break;
        }
    }

    ShotLinkManager::Instance().GetSocketPoll().SetEventRead(socket_);
    ShotLinkManager::Instance().GetSocketPoll().SetEventError(socket_);
    http::response::Parser parser(&recv_body_);

    AutoBuffer recv_buff;

    while (true) {
        ssize_t nsize = BlockSocketReceive(socket_, recv_buff,
                ShotLinkManager::Instance().GetSocketPoll(), kRecvBuffSize);
        if (nsize < 0) {
            LogE(TAG, "[__ReadWrite] BlockSocketReceive ret: %zd", nsize)
            status_ = kError;
            err_code_ |= (uint32_t) RECV_FAILED;
            break;
        }

        if (::gettickcount() - start_ > kTimeoutMillis) {
            status_ = kTimeout;
            err_code_ |= (uint32_t) OPERATION_TIMEOUT;
            break;
        }

        parser.Recv(recv_buff);

        if (parser.IsEnd()) {
            break;
        } else if (parser.IsErr()) {
            LogE(TAG, "[__ReadWrite] parser.IsErr()")
            status_ = kError;
            err_code_ |= (uint32_t) RECV_FAILED;
            break;
        }
    }
    LogI(TAG, "[__ReadWrite] http total Len: %zd", recv_buff.Length())
    LogI(TAG, "[__ReadWrite] http body Len: %zd", recv_body_.Length())

    CLOSE_SOCKET(socket_);

    C2Java_BufferToResp(env, GetRecvBody(), GetNetId());
    C2Java_OnTaskEnd(env, GetNetId(), GetErrCode());
    status_ = kFinished;
}

void ShortLink::__WaitForWorker() {
    if (async_ret_.valid()) {
        LogI(TAG, "[__WaitForWorker] waiting...")
        async_ret_.get();
        LogI(TAG, "[__WaitForWorker] done")
    }
}

int ShortLink::GetNetId() const { return task_.netid_; }

int ShortLink::GetStatus() const { return status_; }

bool ShortLink::IsInvalid() const { return status_ == kFinished || (status_ == kError && !CanRetry()); }

bool ShortLink::StatusErr() const { return status_ == kError; }

bool ShortLink::CanRetry() const { return curr_retry_cnt_ < task_.retry_cnt_; }

u_short ShortLink::GetPort() const { return port_; }

AutoBuffer &ShortLink::GetSendBody() { return send_body_; }

AutoBuffer &ShortLink::GetRecvBody() { return recv_body_; }

uint32_t ShortLink::GetErrCode() const { return err_code_; }

std::string &ShortLink::GetCgi() { return task_.cgi_; }

std::string &ShortLink::GetHost() { return svr_inet_addr_; }

ShortLink::~ShortLink() { __WaitForWorker(); }
