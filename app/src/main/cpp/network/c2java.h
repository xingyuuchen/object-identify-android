#ifndef OI_C2JAVA_H
#define OI_C2JAVA_H

#include <jni.h>
#include "autobuffer.h"


jint CreateJvm(JavaVM** jvm, JNIEnv** env);

jint C2Java_OnTaskEnd(JNIEnv* env, int _netid, int _err_code);

int C2Java_ReqToBuffer(JNIEnv *env, AutoBuffer &_send_buffer, int _net_id);

#endif //OI_C2JAVA_H
