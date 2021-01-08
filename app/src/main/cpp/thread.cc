#include "thread.h"
#include "util.h"


void Thread::Start() {
    LogI("thread running...");
    if (runnable_ != nullptr) {
        int ret = 0;
        ret = pthread_create(&tid_, NULL, StartRoutine, runnable_);

//        Log(&"pthread_create ret = "[ret]);
        if (ret != 0) {

        }
    }
}

