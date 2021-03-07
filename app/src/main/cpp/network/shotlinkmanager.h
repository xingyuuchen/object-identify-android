#ifndef OI_SHOTLINKMANAGER_H
#define OI_SHOTLINKMANAGER_H

#include "socket/socketpoll.h"
#include "shortlink.h"
#include "utils/singleton.h"
#include <list>
#include <mutex>


class ShotLinkManager {

    SINGLETON(ShotLinkManager, )

  public:
    ~ShotLinkManager();

    SocketPoll &GetSocketPoll();

    ShortLink *CreateShortLink(Task &_task, std::string _svr_inet_addr = "127.0.0.1",
                               u_short _port = 5002);

    void StartTask(ShortLink *_cmd);

  private:
    void __RunOnClearInvalidTask();

  private:
    static const char* const    TAG;
    std::list<ShortLink *>      lst_cmd_;
    std::mutex                  mutex_;
    SocketPoll                  socket_poll_;

};


#endif //OI_SHOTLINKMANAGER_H
