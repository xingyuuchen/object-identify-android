#ifndef OI_SVR_UNIX_SOCKET_H
#define OI_SVR_UNIX_SOCKET_H

#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <fcntl.h>


#define SOCKET int
#define INVALID_SOCKET -1
#define CONNECT_FAILED -2
#define SEND_FAILED -3
#define RECV_FAILED -4

inline int SetNonblocking(SOCKET _fd) {
    int old_flags = ::fcntl(_fd, F_GETFL);
    if (::fcntl(_fd, F_SETFL, old_flags | O_NONBLOCK) == -1) {
        return -1;
    }
    return 0;
}

#endif //OI_SVR_UNIX_SOCKET_H
