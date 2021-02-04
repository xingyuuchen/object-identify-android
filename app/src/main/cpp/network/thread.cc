#include "thread.h"


void Thread::Start() {
    LogI("thread running...");
    if (runnable_ != nullptr) {
        int ret = pthread_create(&tid_, &attr_, StartRoutine, runnable_);
        if (ret == 0) {
            LogI("pthread_create succeed, tid: %ld", tid_);
        } else {
            LogE("[Thread::start] pthread_create ret = %d", ret);
        }
    }
}

pthread_t Thread::GetTid() const {
    return tid_;
}

