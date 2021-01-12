#ifndef OI_C2JAVA_H
#define OI_C2JAVA_H

#include <jni.h>


jint CreateJvm(JavaVM** jvm, JNIEnv** env);

jint C2Java_OnTaskEnd(JNIEnv* env, int _netid, int _err_code);



#endif //OI_C2JAVA_H
