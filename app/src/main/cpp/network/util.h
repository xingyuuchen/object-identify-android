#ifndef OI_UTIL_H
#define OI_UTIL_H

#include <jni.h>
#include <android/log.h>


#define TAG "OIJNI"

#define LogD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
#define LogI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LogW(...) __android_log_print(ANDROID_LOG_WARN, TAG, __VA_ARGS__)
#define LogE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)



#endif //OI_UTIL_H
