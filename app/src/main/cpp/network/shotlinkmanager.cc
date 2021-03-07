#include "shotlinkmanager.h"
#include "jni/scopejenv.h"
#include "utils/threadpool.h"
#include "utils/log.h"
#include <utility>


const char *const ShotLinkManager::TAG = "ShotLinkManager";

ShotLinkManager::ShotLinkManager() = default;

ShortLink *ShotLinkManager::CreateShortLink(Task &_task, std::string _svr_inet_addr,
                                            u_short _port) {
    auto *short_link = new ShortLink(_task, std::move(_svr_inet_addr), _port);
    std::unique_lock<std::mutex> lock(mutex_);
    lst_cmd_.push_back(short_link);
    return short_link;
}


ShotLinkManager::~ShotLinkManager() {
    std::unique_lock<std::mutex> lock(mutex_);
    for (auto &cmd : lst_cmd_) {
        if (cmd != NULL) {
            delete cmd;
            cmd = NULL;
        }
    }
}

void ShotLinkManager::__RunOnClearInvalidTask() {
    std::unique_lock<std::mutex> lock(mutex_);

    LogI(TAG, "[__RunOnClearInvalidTask] list size: %u", lst_cmd_.size())
    if (lst_cmd_.empty()) { return; }

    bool anyone_running = false;
    auto it = lst_cmd_.begin();
    auto end = lst_cmd_.end();

    while (it != end) {
        auto next = it;
        next++;

        if ((*it) == NULL) { continue; }

        if ((*it)->HasDone()) {
            delete (*it);
            lst_cmd_.erase(it);
            (*it) = NULL;
        } else {
            anyone_running = true;
        }
        it = next;
    }
    if (!anyone_running) { return; }

    ThreadPool::Instance().ExecuteAfter(1000,
            std::bind(&ShotLinkManager::__RunOnClearInvalidTask, this));
}

void ShotLinkManager::StartTask(ShortLink *_cmd) {
    _cmd->DoTask();
    __RunOnClearInvalidTask();
}

SocketPoll &ShotLinkManager::GetSocketPoll() { return socket_poll_; }
