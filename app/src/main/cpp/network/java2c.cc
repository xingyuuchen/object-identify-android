#include <jni.h>
#include "task.h"
#include <thread>
#include "shortlink.h"
#include <pthread.h>
#include "c2java.h"
#include "utils/log.h"
#include <memory>

//#define DEBUG


int StartTaskImpl(Task &_task, JNIEnv* env) {
    LogI("", "[StartTaskImpl]");

#ifdef DEBUG
    ShortLink short_link(_task);
#else
    ShortLink short_link(_task, "49.235.29.121");
#endif
    int ret = C2Java_ReqToBuffer(env, short_link.GetSendBody(), short_link.GetNetId());
    LogI("", "[StartTaskImpl] C2Java_ReqToBuffer ret = %d", ret);
    if (ret >= 0) {
        if (_task.care_about_progress_) {
            using namespace std::placeholders;
            short_link.SetUploadProgressCallback(std::make_shared<std::function<void(long, long)>>(
                    std::bind(C2Java_OnUploadProgress, env, _task.netid_, _1, _2)));
        }
        short_link.DoTask();
    }

    C2Java_BufferToResp(env, short_link.GetRecvBody(), short_link.GetNetId());
    C2Java_OnTaskEnd(env, short_link.GetNetId(), short_link.GetErrCode());
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
    jfieldID field_id_care_about_progress = env->GetFieldID(clzz, "careAboutProgress", "Z");

    jint netid = env->GetIntField(jtask, field_id_net_id);
    jstring cgi = (jstring) env->GetObjectField(jtask, field_id_cgi);
    jint retry_cnt = env->GetIntField(jtask, field_id_retry_cnt);
    jboolean care_about_progress = env->GetBooleanField(jtask, field_id_care_about_progress);

    Task task;
    task.netid_ = netid;
    task.care_about_progress_ = care_about_progress;
    task.retry_cnt_ = retry_cnt;
    if (cgi != NULL) {
        task.cgi_ = env->GetStringUTFChars(cgi, NULL);
    }

    return StartTaskImpl(task, env);
}


}
