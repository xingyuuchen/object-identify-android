#include "util.h"
#include <android/log.h>


extern JNIEnv *glob_env;

//void Log(const char *_msg) {
//    if (glob_env == NULL) {
//        return;
//    }
//
//    jclass clz;
//    jmethodID jmethodId;
//    clz = glob_env->FindClass("com/cxy/oi/kernel/util/Log");
//    if (clz == NULL) {
//        return;
//    }
//    jmethodId = glob_env->GetStaticMethodID(clz, "i",
//                       "(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V");
//    jstring TAG = glob_env->NewStringUTF("OI.JNI");
//    jobjectArray strings = {};
//    glob_env->CallStaticVoidMethod(clz, jmethodId, TAG, glob_env->NewStringUTF(_msg), strings);
//}

