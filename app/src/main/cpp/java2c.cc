#include <jni.h>
#include "task.h"
#include <thread>
#include "shortlink.h"
#include <pthread.h>
#include "util.h"


typedef pthread_t thread_tid;

extern jint CreateJvm(JavaVM** jvm, JNIEnv** env);

JNIEnv *glob_env = NULL;


int StartTask(Task &_task, JNIEnv *env) {
    LogI("StartTask");
    ShortLink shortLink(_task, false);

    shortLink.SendRequest();
    return 0;
}


extern "C" {


int Java_com_cxy_oi_kernel_network_NativeNetTaskAdapter_startTask(JNIEnv *env, jclass clazz,
                          jobject jtask) {
    // clazz: NativeNetTaskAdapter
    glob_env = env;

    jclass clzz = env->GetObjectClass(jtask);
    jfieldID field_id_task_id = env->GetFieldID(clzz, "taskID", "I");
    jfieldID field_id_cgi = env->GetFieldID(clzz, "cgi", "Ljava/lang/String;");
    jfieldID field_id_retry_cnt = env->GetFieldID(clzz, "retryCount", "I");

    jint task_id = env->GetIntField(jtask, field_id_task_id);
    jstring cgi = (jstring) env->GetObjectField(jtask, field_id_cgi);
    jint retry_cnt = env->GetIntField(jtask, field_id_retry_cnt);

    Task task;
    task.type_ = task_id;
    task.retry_cnt_ = retry_cnt;
    if (cgi != NULL) {
        task.cgi_ = env->GetStringUTFChars(cgi, NULL);
    }

    return StartTask(task, env);
}


}
