#include "shortlink.h"
#include <pthread.h>
#include <boost/bind.hpp>
#include "util.h"


extern int errno;

ShortLink::ShortLink(Task &_task, bool _use_proxy)
        : use_proxy_(_use_proxy)
        , task_()
        , thread_(boost::bind(&ShortLink::__Run, this))
        , socket_(INVALID_SOCKET) {
    LogI("new ShortLink");
    task_.cgi_ = _task.cgi_;
    task_.retry_cnt_ = _task.retry_cnt_;
    task_.netid_ = _task.netid_;
    LogI("[ShortLink] task_.cgi_.c_str: %s, len: %d", task_.cgi_.c_str(), task_.cgi_.length());
}


int ShortLink::SendRequest() {
    thread_.Start();
    int ret = pthread_join(GetTid(), NULL);
    LogI("[Thread::Start] pthread_join ret = %d", ret);
    return 0;
}


void ShortLink::__Run() {
    LogI("[__Run] task_.cgi_.c_str: %s, len: %d", task_.cgi_.c_str(), task_.cgi_.length());
    int ret = Connect();
    if (ret < 0) {
        return;
    }
    LogI("[__Run] task_.cgi_.c_str: %s, len: %d", task_.cgi_.c_str(), task_.cgi_.length());

    ret = __ReadWrite();
    close(socket_);
}


int ShortLink::Connect() {
    char svrInetAddr[] = "127.0.0.1";

    socket_ = socket(AF_INET, SOCK_STREAM, 0);
    if (socket_ <= 0) {
        return INVALID_SOCKET;
    }

    struct sockaddr_in sockaddr{};
    memset(&sockaddr, 0, sizeof(sockaddr));
    sockaddr.sin_family = AF_INET;
    sockaddr.sin_port = htons(5002);
    sockaddr.sin_addr.s_addr = inet_addr(svrInetAddr);

    if (connect(socket_, (struct sockaddr *) &sockaddr, sizeof(sockaddr)) < 0) {
        LogE("connect failed");
        close(socket_);
        return CONNECT_FAILED;
    }
    LogI("connect succeed!");

    return 0;
}


int ShortLink::__ReadWrite() {
//    send_body_.Write((unsigned char *) task_.cgi_.c_str(), task_.cgi_.length());
//    int ret = send(socket_, send_body_.Ptr(), send_body_.Length(), 0);
    LogI("[__ReadWrite] task_.cgi_.c_str: %s, len: %d", task_.cgi_.c_str(), task_.cgi_.length());
    int ret = send(socket_, task_.cgi_.c_str(), task_.cgi_.length(), 0);
    if (ret < 0) {
        LogE("[__ReadWrite] send, errno: %d: \"%s\"", errno, strerror(errno));
        return -1;
    }

    unsigned char recv_ba[100] = {};
    size_t len = recv(socket_, recv_ba, 100, 0);
    LogI("[__ReadWrite] recv Len: %d: \"%s\"", len, recv_ba);
    return 0;
}

pthread_t ShortLink::GetTid() const {
    return thread_.GetTid();
}

int ShortLink::GetNetId() const {
    return task_.netid_;
}

