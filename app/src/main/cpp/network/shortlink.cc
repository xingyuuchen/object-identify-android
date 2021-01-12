#include "shortlink.h"
#include <pthread.h>
#include <boost/bind.hpp>
#include "util.h"

const size_t kBuffSize = 1024;

ShortLink::ShortLink(Task &_task, const std::string &_svr_inet_addr, u_short _port, bool _use_proxy)
        : use_proxy_(_use_proxy)
        , task_()
        , thread_(boost::bind(&ShortLink::__Run, this))
        , port_(_port)
        , svr_inet_addr_(_svr_inet_addr)
        , socket_(INVALID_SOCKET) {
    LogI("new ShortLink");
    task_.cgi_ = _task.cgi_;
    task_.retry_cnt_ = _task.retry_cnt_;
    task_.netid_ = _task.netid_;
}


int ShortLink::SendRequest() {
    thread_.Start();
    int ret = pthread_join(GetTid(), NULL);
    LogI("[Thread::Start] pthread_join ret = %d", ret);
    return ret;
}


void ShortLink::__Run() {
    int ret = Connect();
    if (ret < 0) {
        return;
    }
    ret = __ReadWrite();
    close(socket_);
}


int ShortLink::Connect() {
    socket_ = socket(AF_INET, SOCK_STREAM, 0);
    if (socket_ <= 0) {
        return INVALID_SOCKET;
    }

    struct sockaddr_in sockaddr{};
    memset(&sockaddr, 0, sizeof(sockaddr));
    sockaddr.sin_family = AF_INET;
    sockaddr.sin_port = htons(5002);
    sockaddr.sin_addr.s_addr = inet_addr(svr_inet_addr_.c_str());

    if (connect(socket_, (struct sockaddr *) &sockaddr, sizeof(sockaddr)) < 0) {
        LogE("connect failed");
        close(socket_);
        return CONNECT_FAILED;
    }
    LogI("connect succeed!");

    return 0;
}


int ShortLink::__ReadWrite() {

    send_body_.Write((unsigned char *) task_.cgi_.c_str(), task_.cgi_.length());

    int ret = send(socket_, send_body_.Ptr(), send_body_.Length(), 0);
    if (ret < 0) {
        LogE("[__ReadWrite] send, errno: %d: \"%s\"", errno, strerror(errno));
        return -1;
    }

    AutoBuffer recv_buff;
    recv_buff.AddCapacity(128);
    size_t len = recv(socket_, recv_buff.Ptr(), kBuffSize, 0);
    recv_buff.SetLength(len);
    LogI("[__ReadWrite] recv Len: %d: \"%s\", %d", len, recv_buff.Ptr(), recv_buff.Length());
    return 0;
}

pthread_t ShortLink::GetTid() const {
    return thread_.GetTid();
}

int ShortLink::GetNetId() const {
    return task_.netid_;
}

u_short ShortLink::GetPort() const {
    return port_;
}

