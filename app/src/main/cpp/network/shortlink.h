#ifndef OI_SHORTLINK_H
#define OI_SHORTLINK_H

#include "task.h"
#include "thread.h"
#include "socket/unix_socket.h"
#include "utils/autobuffer.h"

typedef pthread_t thread_tid;


class ShortLink {
  public:
    explicit ShortLink(Task &_task, std::string _svr_inet_addr = "127.0.0.1",
            u_short _port = 5002, bool _use_proxy = false);

    void DoConnect();

    AutoBuffer &GetSendBody();

    AutoBuffer &GetRecvBody();

    int DoTask();

    int GetNetId() const;

    u_short GetPort() const;

    thread_tid GetTid() const;

    int GetErrCode() const;

    std::string &GetCgi();

    std::string &GetHost();

  private:
    void __ReadWrite();
    int __Run();

  private:
    Task            task_;
    Thread          thread_;
    const bool      use_proxy_;
    int             err_code_;
    SOCKET          socket_;
    AutoBuffer      send_body_;
    AutoBuffer      recv_body_;
    u_short         port_;
    std::string     svr_inet_addr_;

};


#endif //OI_SHORTLINK_H
