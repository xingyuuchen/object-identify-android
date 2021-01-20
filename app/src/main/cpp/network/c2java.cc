#include <string.h>
#include "c2java.h"
#include "log.h"


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

int C2Java_ReqToBuffer(JNIEnv *env, AutoBuffer &_send_buffer, int _net_id) {
    jclass clz = env->FindClass("com/cxy/oi/kernel/network/NativeNetTaskAdapter");
    jmethodID reqToBuffer_id = env->GetStaticMethodID(clz, "reqToBuffer", "(I)[B");
    jbyteArray ret = (jbyteArray) env->CallStaticObjectMethod(clz, reqToBuffer_id, _net_id);
    if (ret == NULL) {
        LogE("[C2Java_ReqToBuffer] ret == null");
    }
    jbyte *jba = env->GetByteArrayElements(ret, NULL);
    jsize len_ = env->GetArrayLength(ret);


    jmethodID onTaskEnd_id = env->GetStaticMethodID(clz, "getReqBufferSize", "(I)J");
    jlong len = env->CallStaticLongMethod(clz, onTaskEnd_id, _net_id);
    LogI("[C2Java_ReqToBuffer] len = %d, len = %ld", len_, len);
    if (len == len_) {
        _send_buffer.Write((char *) jba, len_);
        LogI("_send_buffer.Length = %d", _send_buffer.Length());
        return _send_buffer.Length();
    }
    return -1;
}


int C2Java_BufferToResp(JNIEnv *env, AutoBuffer &_recv_buffer, int _net_id) {
    jclass clz = env->FindClass("com/cxy/oi/kernel/network/NativeNetTaskAdapter");
    jmethodID bufferToResp_id = env->GetStaticMethodID(clz, "bufferToResp", "(I[B)I");

    jbyteArray jba = env->NewByteArray(_recv_buffer.Length());
    env->SetByteArrayRegion(jba, 0,
            _recv_buffer.Length(),reinterpret_cast<const jbyte *>(_recv_buffer.Ptr()));
    LogI("[C2Java_BufferToResp] _recv_buffer.Length: %d", _recv_buffer.Length());
    jint ret = env->CallStaticIntMethod(clz, bufferToResp_id, _net_id, jba);
    env->DeleteLocalRef(jba);
    return ret;
}
