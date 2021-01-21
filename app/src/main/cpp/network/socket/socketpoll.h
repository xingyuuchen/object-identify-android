#ifndef OI_SVR_SOCKETSELECT_H
#define OI_SVR_SOCKETSELECT_H

#include <algorithm>
#include <vector>
#include <poll.h>
#include "unix_socket.h"


class SocketPoll {
  public:
    
    SocketPoll();
    
    int Poll();
    
    int Poll(int _msec);
    
    void SetEventRead(SOCKET _socket);
    
    void SetEventWrite(SOCKET _socket);
    
    void SetEventError(SOCKET _socket);
    
    bool IsReadSet(SOCKET _socket);
    
    bool IsErrSet(SOCKET _socket);
    
    void ClearEvents();
    
    void RemoveSocket(SOCKET _socket);
    
    int GetErrno() const;

  private:
    
    inline std::vector<pollfd>::iterator FindPollfd(SOCKET _socket) {
        return std::find_if(pollfds_.begin(), pollfds_.end(),
                    [&_socket] (const pollfd &_fd) -> bool {
            return _socket == _fd.fd;
        });
    }
    
  private:
    std::vector<pollfd> pollfds_;
    int                 errno_;
    
};


#endif //OI_SVR_SOCKETSELECT_H
