#ifndef OI_SHOTLINKMANAGER_H
#define OI_SHOTLINKMANAGER_H

#include "socket/socketpoll.h"


class ShotLinkManager {
  public:

    static ShotLinkManager& GetInstance() {
        static ShotLinkManager instance;
        return instance;
    }

    ShotLinkManager(ShotLinkManager const &) = delete;

    void operator=(ShotLinkManager const &) = delete;

    SocketPoll &GetSocketPoll();


  private:
    ShotLinkManager();


  private:
    SocketPoll socket_poll_;

};


#endif //OI_SHOTLINKMANAGER_H
