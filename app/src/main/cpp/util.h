#ifndef OI_UTIL_H
#define OI_UTIL_H

#include <jni.h>
#include <android/log.h>


#define TAG "OIJNI"

#define LogI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LogE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)



#endif //OI_UTIL_H
