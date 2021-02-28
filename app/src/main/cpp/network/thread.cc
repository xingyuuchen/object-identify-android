#include "thread.h"

const char *const Thread::TAG = "Thread";

void Thread::Start() {
    LogI(TAG, "thread running...");
    if (runnable_ != nullptr) {
        int ret = pthread_create(&tid_, &attr_, StartRoutine, runnable_);
        if (ret == 0) {
            LogI(TAG, "pthread_create succeed, tid: %ld", tid_);
        } else {
            LogE(TAG, "[Thread::start] pthread_create ret = %d", ret);
        }
    }
}

pthread_t Thread::GetTid() const {
    return tid_;
}

