#include "shortlink.h"
#include <pthread.h>
#include <boost/bind.hpp>
#include "log.h"
#include "http/httprequest.h"
#include "http/httpresponse.h"
#include <map>
#include "shotlinkmanager.h"
#include "socket/blocksocket.h"


const size_t kBuffSize = 1024;

ShortLink::ShortLink(Task &_task, std::string _svr_inet_addr, u_short _port, bool _use_proxy)
        : use_proxy_(_use_proxy)
        , task_(_task)
        , thread_(boost::bind(&ShortLink::__Run, this))
        , port_(_port)
        , svr_inet_addr_(std::move(_svr_inet_addr))
        , err_code_(0)
        , socket_(INVALID_SOCKET) {
    LogI("new ShortLink: %s %d %d", task_.cgi_.c_str(), task_.netid_, task_.retry_cnt_)
}


int ShortLink::DoTask() {
    thread_.Start();
    LogI("[Thread::Start] pthread_join ret = %d", pthread_join(GetTid(), NULL));
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
        LogE("Connect FAILED, errCode: %d", ret);
        close(socket_);
        err_code_ = CONNECT_FAILED;
        return;
    }
    LogI("connect succeed!");
}


void ShortLink::__ReadWrite() {
    AutoBuffer out_buff;
    std::map<std::string, std::string> empty;
    http::request::Pack(svr_inet_addr_, task_.cgi_, empty, send_body_,
                        out_buff);
    LogI("http req header length: %zd", out_buff.Length() - send_body_.Length())
//    for (size_t i = 0; i < out_buff.Length() - send_body_.Length(); i++) {
//        LogI("0x%x %c", *out_buff.Ptr(i), *out_buff.Ptr(i))
//    }
    send_body_.Reset();

    int ret = send(socket_, out_buff.Ptr(), out_buff.Length(), 0);
    if (ret < 0) {
        LogE("[__ReadWrite] send, errno: %d: \"%s\"", errno, strerror(errno));
        err_code_ = SEND_FAILED;
        return;
    }

    ShotLinkManager::GetInstance().GetSocketPoll().SetEventRead(socket_);
    ShotLinkManager::GetInstance().GetSocketPoll().SetEventError(socket_);
    http::response::Parser parser(&recv_body_);

    AutoBuffer recv_buff;

    while (true) {
        size_t nsize = BlockSocketReceive(socket_, recv_buff,
                ShotLinkManager::GetInstance().GetSocketPoll(), kBuffSize);
        if (nsize <= 0) {
            LogE("[__ReadWrite] BlockSocketReceive ret: %zd", nsize);
            break;
        }

        parser.Recv(recv_buff);

        if (parser.IsEnd()) {
            break;
        } else if (parser.IsErr()) {
            LogE("[__ReadWrite] parser.IsErr()")
            err_code_ = RECV_FAILED;
            break;
        }
    }
    LogI("[__ReadWrite] http total Len: %zd", recv_buff.Length());
    LogI("[__ReadWrite] http body Len: %zd", recv_body_.Length());

    close(socket_);
}

thread_tid ShortLink::GetTid() const { return thread_.GetTid(); }

int ShortLink::GetNetId() const { return task_.netid_; }

u_short ShortLink::GetPort() const { return port_; }

AutoBuffer &ShortLink::GetSendBody() { return send_body_; }

AutoBuffer &ShortLink::GetRecvBody() { return recv_body_; }

int ShortLink::GetErrCode() const { return err_code_; }

std::string &ShortLink::GetCgi() { return task_.cgi_; }

std::string &ShortLink::GetHost() { return svr_inet_addr_; }

