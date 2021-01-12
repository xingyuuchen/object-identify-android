#ifndef OI_SHORTLINK_H
#define OI_SHORTLINK_H

#include "task.h"
#include "thread.h"
#include "unix_socket.h"
#include "autobuffer.h"


class ShortLink {
  public:
    explicit ShortLink(Task &_task, bool _use_proxy = false);

    int Connect();

    int SendRequest();

    int GetNetId() const;

    pthread_t GetTid() const;

  private:
    int __ReadWrite();
    void __Run();

  private:
    Task        task_;
    Thread      thread_;
    const bool  use_proxy_;
    SOCKET      socket_;
    AutoBuffer  send_body_;
    AutoBuffer  send_extend_;


};


#endif //OI_SHORTLINK_H
