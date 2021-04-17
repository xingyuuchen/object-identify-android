#include "blocksocket.h"
#include "log.h"
#include "timeutil.h"
#include <unistd.h>


ssize_t BlockSocketSend(SOCKET _socket,
                       AutoBuffer &_send_buff,
                       size_t _buff_size,
                       int _timeout_mills/* = 5000*/,
                       bool _wait_full/* =false*/) {
    uint64_t start_time = ::gettickcount();

    ssize_t nsend = 0;
    ssize_t ntotal = _send_buff.Length() - _send_buff.Pos();
    ntotal = (ntotal > _buff_size) ? _buff_size : ntotal;

    uint64_t cost_time = 0;

    while (true) {

        int n = ::write(_socket, _send_buff.Ptr(
                _send_buff.Pos() + nsend), ntotal - nsend);

        if (n <= 0) {
            LogE(__FILE__, "nsend: %d", n)
            return -1;
        }

        nsend += n;

        if (!_wait_full) {
            _send_buff.Seek(_send_buff.Pos() + nsend);
            return nsend;
        }
        if (nsend >= ntotal) {
            return nsend;
        }

        cost_time = ::gettickcount() - start_time;
        if (cost_time > _timeout_mills) {
            _send_buff.Seek(_send_buff.Pos() + nsend);
            return nsend;
        }
    }
}


ssize_t BlockSocketReceive(SOCKET _socket, AutoBuffer &_recv_buff,
                          SocketPoll &_socket_poll,
                          size_t _buff_size,
                          int _timeout_mills/* = 5000*/,
                          bool _wait_full/* = false*/) {
    uint64_t start_time = ::gettickcount();
    
    if (_timeout_mills < -1) { _timeout_mills = -1; }
    size_t available = _recv_buff.AvailableSize();
    if (available < _buff_size) {
        _recv_buff.AddCapacity(_buff_size - available);
    }
    
    ssize_t nrecv = 0;
    int cost_time = 0;
    
    
    while (true) {
        
        int poll_timeout = _timeout_mills > cost_time ? _timeout_mills - cost_time : 0;
        // FIXME: 有别的socket处于ready状态时, 此处不会等待这么多时间
        int ret = _socket_poll.Poll(poll_timeout);

        if (ret < 0) {
            int errno_ = _socket_poll.GetErrno();
            LogE(__FILE__, "[BlockSocketReceive] poll errno: %d", errno_)
            return -1;
        } else if (ret == 0) {
            LogE(__FILE__, "[BlockSocketReceive] timeout, nrecv = %zd, poll_timeout = %d", nrecv, poll_timeout)
            return nrecv;
        } else {
            
            if (_socket_poll.IsReadSet(_socket)) {
                ssize_t n = ::read(_socket, _recv_buff.Ptr(_recv_buff.Length()),
                                 _buff_size - nrecv);
                
                if (n > 0) {
                    _recv_buff.AddLength(n);
                    nrecv += n;
                    if (!_wait_full) { return nrecv; }
                    
                    if (nrecv >= _buff_size) { return nrecv; }
                    
                } else if (n == 0) {
                    LogI(__FILE__, "[BlockSocketReceive] conn closed by peer, n = 0, nrecv:%zd", nrecv)
                    return -1;
                } else {
                    LogE(__FILE__, "[BlockSocketReceive] n:%zd, nrecv:%zd", n, nrecv)
                    return nrecv;
                }
            } else if (_socket_poll.IsErrSet(_socket)) {
                LogI(__FILE__, "[BlockSocketReceive] POLLERR, nrecv:%zd", nrecv)
                return nrecv;
            }
            if (poll_timeout == 0) {
                return 0;
            }
        }
        cost_time = (int) (::gettickcount() - start_time);
    }
    
}
