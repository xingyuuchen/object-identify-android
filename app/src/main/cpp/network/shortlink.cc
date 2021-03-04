#include "shortlink.h"
#include "utils/log.h"
#include "http/httprequest.h"
#include "http/httpresponse.h"
#include <map>
#include <utility>
#include "shotlinkmanager.h"
#include "socket/blocksocket.h"


const size_t kBuffSize = 1024;

const char *const ShortLink::TAG = "ShortLink";

ShortLink::ShortLink(Task &_task, std::string _svr_inet_addr, u_short _port, bool _use_proxy)
        : use_proxy_(_use_proxy)
        , task_(_task)
        , port_(_port)
        , svr_inet_addr_(std::move(_svr_inet_addr))
        , err_code_(0)
        , socket_(INVALID_SOCKET) {
    LogI(TAG, "new ShortLink: %s %d %d", task_.cgi_.c_str(), task_.netid_, task_.retry_cnt_)
}


int ShortLink::DoTask() {
    // TODO: async call __Run()
    __Run();
    return 0;
}


int ShortLink::__Run() {
    DoConnect();
    if (err_code_ < 0) {
        return err_code_;
    }
    __ReadWrite();
    return err_code_;
}


void ShortLink::DoConnect() {
    socket_ = socket(AF_INET, SOCK_STREAM, 0);
    if (socket_ <= 0) {
        err_code_ = INVALID_SOCKET;
        return;
    }

    struct sockaddr_in sockaddr{};
    memset(&sockaddr, 0, sizeof(sockaddr));
    sockaddr.sin_family = AF_INET;
    sockaddr.sin_port = htons(5002);
    sockaddr.sin_addr.s_addr = inet_addr(svr_inet_addr_.c_str());

    if (int ret = connect(socket_, (struct sockaddr *) &sockaddr, sizeof(sockaddr)) < 0) {
        LogE(TAG, "Connect FAILED, errCode: %d", ret);
        close(socket_);
        err_code_ = CONNECT_FAILED;
        return;
    }
    LogI(TAG, "connect succeed!");
}


void ShortLink::__ReadWrite() {
    AutoBuffer out_buff;
    std::map<std::string, std::string> empty;
    http::request::Pack(svr_inet_addr_, task_.cgi_, empty, send_body_,
                        out_buff);
    LogI(TAG, "http req header length: %zd", out_buff.Length() - send_body_.Length())
//    for (size_t i = 0; i < out_buff.Length() - send_body_.Length(); i++) {
//        LogI(TAG, "0x%x %c", *out_buff.Ptr(i), *out_buff.Ptr(i))
//    }
    send_body_.Reset();

    ssize_t nsend = 0;
    ssize_t ntotal = out_buff.Length();
    while (true) {
        // FIXME: 不是真实进度
        ssize_t n = BlockSocketSend(socket_, out_buff, 2 * kBuffSize);
        if (n < 0) {
            LogE(TAG, "[__ReadWrite] send, errno（%d）: %s", errno, strerror(errno));
            err_code_ = SEND_FAILED;
            ::close(socket_);
            return;
        }
        nsend += n;
        LogI(TAG, "[__ReadWrite] send %d/%d bytes", nsend, ntotal)
        if (task_.care_about_progress_) {
            (*progress_cb_)(nsend, ntotal);
        }
        if (ntotal <= nsend) {
            break;
        }
    }

    ShotLinkManager::GetInstance().GetSocketPoll().SetEventRead(socket_);
    ShotLinkManager::GetInstance().GetSocketPoll().SetEventError(socket_);
    http::response::Parser parser(&recv_body_);

    AutoBuffer recv_buff;

    while (true) {
        ssize_t nsize = BlockSocketReceive(socket_, recv_buff,
                ShotLinkManager::GetInstance().GetSocketPoll(), kBuffSize);
        if (nsize <= 0) {
            LogE(TAG, "[__ReadWrite] BlockSocketReceive ret: %zd", nsize);
            break;
        }

        parser.Recv(recv_buff);

        if (parser.IsEnd()) {
            break;
        } else if (parser.IsErr()) {
            LogE(TAG, "[__ReadWrite] parser.IsErr()")
            err_code_ = RECV_FAILED;
            break;
        }
    }
    LogI(TAG, "[__ReadWrite] http total Len: %zd", recv_buff.Length());
    LogI(TAG, "[__ReadWrite] http body Len: %zd", recv_body_.Length());

    ::close(socket_);
}

void ShortLink::SetUploadProgressCallback(std::shared_ptr<std::function<void (long, long)>> _progress_cb) {
    progress_cb_ = _progress_cb;
}

int ShortLink::GetNetId() const { return task_.netid_; }

u_short ShortLink::GetPort() const { return port_; }

AutoBuffer &ShortLink::GetSendBody() { return send_body_; }

AutoBuffer &ShortLink::GetRecvBody() { return recv_body_; }

int ShortLink::GetErrCode() const { return err_code_; }

std::string &ShortLink::GetCgi() { return task_.cgi_; }

std::string &ShortLink::GetHost() { return svr_inet_addr_; }

