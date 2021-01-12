#include "c2java.h"


jint CreateJvm(JavaVM** jvm, JNIEnv** env) {
    JavaVMInitArgs initArgs;
    JavaVMOption options[1];

    initArgs.version = JNI_VERSION_1_6;
    initArgs.nOptions = 1;
    options[0].optionString = "-Djava.class.path=./";
    initArgs.options = options;
    initArgs.ignoreUnrecognized = JNI_FALSE;

//    return JNI_CreateJavaVM(jvm, env, &initArgs);
    return 0;
}

jint C2Java_OnTaskEnd(JNIEnv* env, int _netid, int _err_code) {
    jclass clz = env->FindClass("com/cxy/oi/kernel/network/NativeNetTaskAdapter");
    jmethodID onTaskEnd_id = env->GetStaticMethodID(clz, "onTaskEnd", "(II)I");
    jint ret = env->CallStaticIntMethod(clz, onTaskEnd_id, _netid, _err_code);
    return ret;
}

