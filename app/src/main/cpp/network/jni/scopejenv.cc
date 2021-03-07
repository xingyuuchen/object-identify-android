#include "scopejenv.h"
#include "../utils/log.h"
#include <unistd.h>
#include <thread>


const char *const ScopeJEnv::TAG = "ScopeJEnv";

ScopeJEnv::ScopeJEnv()
        : jvm_(NULL)
        , jenv_(NULL) {

    jvm_ = VarCache::Instance().GetJvm();
    if (jvm_ == NULL) {
        LogE(TAG, "[ScopeJEnv] jvm_ == NULL")
        return;
    }

    int ret = jvm_->GetEnv((void **) &jenv_, JNI_VERSION_1_6);
    if (ret == JNI_OK && jenv_ != NULL) {
        LogI(TAG, "[ScopeJEnv] get env succeed")
        return;
    }

    if (ret == JNI_EDETACHED) {
        // JEnv becomes invalid apart from main thread.
        char thread_name[32] = {0, };
        snprintf(thread_name, sizeof(thread_name), "oi-native-thread-%d", (int)gettid());
        JavaVMAttachArgs args;
        args.name = thread_name;
        args.group = NULL;
        args.version = JNI_VERSION_1_6;
        ret = jvm_->AttachCurrentThread(&jenv_, &args);
        if (ret != JNI_OK) {
            LogE(TAG, "[ScopeJEnv] AttachCurrentThread ret: %d", ret)
        }
        LogI(TAG, "[ScopeJEnv] get env succeed")

        return;
    } else {
        LogE(TAG, "[ScopeJEnv] jvm_->GetEnv ret: %d", ret)
    }
}

JNIEnv *ScopeJEnv::GetEnv() { return jenv_; }
