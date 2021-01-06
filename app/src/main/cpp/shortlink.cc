#include "shortlink.h"
#include <pthread.h>
#include <boost/bind.hpp>


ShortLink::ShortLink()
        : use_proxy_(false)
        , task_(1, 2)
        , thread_(boost::bind(&ShortLink::__Run, this))
        , socket_(INVALID_SOCKET) {


}


void ShortLink::__Run() {
    int ret = Connect();
    if (ret < 0) {
        return;
    }

    ReadWrite();
}


int ShortLink::Connect() {
    char svrInetAddr[] = "127.0.0.1";
    int n;

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
        close(socket_);
    }

    return 0;
}

int ShortLink::SendRequest() {
    thread_.Start();
    return 0;
}


void ShortLink::ReadWrite() {

}

