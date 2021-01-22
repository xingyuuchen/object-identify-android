#include "shotlinkmanager.h"


ShotLinkManager::ShotLinkManager() {

}

SocketPoll &ShotLinkManager::GetSocketPoll() {
    return socket_poll_;
}


