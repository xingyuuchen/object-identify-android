#ifndef OI_C2JAVA_H
#define OI_C2JAVA_H

#include <jni.h>
#include "utils/autobuffer.h"


jint CreateJvm(JavaVM** jvm, JNIEnv** env);

jint C2Java_OnTaskEnd(JNIEnv* env, int _netid, int _err_code);

int C2Java_ReqToBuffer(JNIEnv *env, AutoBuffer &_send_body, int _net_id);

int C2Java_BufferToResp(JNIEnv *env, AutoBuffer &_recv_buffer, int _net_id);

int C2Java_OnUploadProgress(JNIEnv *env, int _net_id, jlong _curr, jlong _total);

#endif //OI_C2JAVA_H
