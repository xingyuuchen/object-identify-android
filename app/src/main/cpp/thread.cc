#include "thread.h"



void Thread::Start() {
    if (runnable_ != nullptr) {
        int ret = pthread_create(&tid_, NULL, StartRoutine, runnable_);

        if (ret != 0) {

        }
    }
}

