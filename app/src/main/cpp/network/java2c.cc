#include <jni.h>
#include "task.h"
#include <thread>
#include "shortlink.h"
#include <pthread.h>
#include "c2java.h"
#include "util.h"

#define DEBUG

typedef pthread_t thread_tid;

extern jint CreateJvm(JavaVM** jvm, JNIEnv** env);


int StartTask(Task &_task, JNIEnv* env) {
    LogI("StartTask");

#ifdef DEBUG
    ShortLink shortLink(_task);
#else
    ShortLink shortLink(_task, "49.235.29.121");
#endif
    int ret = shortLink.SendRequest();

    // callback
    C2Java_OnTaskEnd(env, shortLink.GetNetId(), ret);
    return 0;
}


extern "C" {


int Java_com_cxy_oi_kernel_network_NativeNetTaskAdapter_startTask(JNIEnv *env, jclass clazz,
                          jobject jtask) {
    // clazz: NativeNetTaskAdapter
    jclass clzz = env->GetObjectClass(jtask);
    jfieldID field_id_net_id = env->GetFieldID(clzz, "netID", "I");
    jfieldID field_id_cgi = env->GetFieldID(clzz, "cgi", "Ljava/lang/String;");
    jfieldID field_id_retry_cnt = env->GetFieldID(clzz, "retryCount", "I");

    jint netid = env->GetIntField(jtask, field_id_net_id);
    jstring cgi = (jstring) env->GetObjectField(jtask, field_id_cgi);
    jint retry_cnt = env->GetIntField(jtask, field_id_retry_cnt);

    Task task;
    task.netid_ = netid;
    task.retry_cnt_ = retry_cnt;
    if (cgi != NULL) {
        task.cgi_ = env->GetStringUTFChars(cgi, NULL);
    }

    return StartTask(task, env);
}


}
