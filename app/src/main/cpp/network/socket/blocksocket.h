#ifndef OI_SVR_BLOCKSOCKET_H
#define OI_SVR_BLOCKSOCKET_H

#include "socketpoll.h"
#include "unix_socket.h"
#include "../utils/autobuffer.h"


size_t BlockSocketReceive(SOCKET _socket,
                            AutoBuffer &_recv_buff,
                            SocketPoll &_socket_poll,
                            size_t _buff_size,
                            int _timeout_mills = 5000,
                            bool _wait_full = false);


#endif //OI_SVR_BLOCKSOCKET_H
