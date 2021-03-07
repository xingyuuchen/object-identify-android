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

void ShotLinkManager::StartTask(ShortLink *_cmd) {
    _cmd->DoTask();
    __RunOnRetryFailedTask();
    __RunOnClearInvalidTask();
}

void ShotLinkManager::__RunOnClearInvalidTask() {
    std::unique_lock<std::mutex> lock(mutex_);

    if (lst_cmd_.empty()) { return; }

    bool anyone_valid = false;
    int clear_cnt = 0;
    auto it = lst_cmd_.begin();
    auto end = lst_cmd_.end();

    while (it != end) {
        auto next = it;
        next++;

        if ((*it) == NULL) { continue; }

        if ((*it)->IsInvalid()) {
            delete (*it);
            lst_cmd_.erase(it);
            (*it) = NULL;
            ++clear_cnt;
        } else {
            anyone_valid = true;
        }
        it = next;
    }
    LogI(TAG, "[__RunOnClearInvalidTask] clear %d tasks", clear_cnt)

    if (anyone_valid) {
        ThreadPool::Instance().ExecuteAfter(1000,
                std::bind(&ShotLinkManager::__RunOnClearInvalidTask, this));
    }
}

void ShotLinkManager::__RunOnRetryFailedTask() {
    std::unique_lock<std::mutex> lock(mutex_);

    if (lst_cmd_.empty()) { return; }

    int retry_num = 0;
    auto it = lst_cmd_.begin();
    auto end = lst_cmd_.end();

    while (it != end) {
        if ((*it) == NULL) { continue; }

        if ((*it)->StatusErr() && (*it)->CanRetry()) {
            (*it)->DoTask();
            ++retry_num;
        }
        ++it;
    }
    LogI(TAG, "[__RunOnRetryFailedTask] retry %d tasks", retry_num)
    if (retry_num > 0) {
        ThreadPool::Instance().ExecuteAfter(2000,
            std::bind(&ShotLinkManager::__RunOnRetryFailedTask, this));
    }
}

SocketPoll &ShotLinkManager::GetSocketPoll() { return socket_poll_; }
