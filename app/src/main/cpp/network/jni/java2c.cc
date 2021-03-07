#include "../task.h"
#include "../shortlink.h"
#include "c2java.h"
#include "../utils/log.h"
#include "../shotlinkmanager.h"
#include "../jni/varcache.h"
#include "../jni/scopejenv.h"
#include <thread>

//#define DEBUG


int StartTaskImpl(Task &_task) {
    LogI("", "[StartTaskImpl]")

#ifdef DEBUG
    ShortLink *short_link = ShotLinkManager::Instance().CreateShortLink(_task);
#else
    ShortLink *short_link = ShotLinkManager::Instance().CreateShortLink(_task, "49.235.29.121");
#endif
    ShotLinkManager::Instance().StartTask(short_link);
    return 0;
}


extern "C" {


int Java_com_cxy_oi_kernel_network_NativeNetTaskAdapter_startTask(JNIEnv *_env, jclass _clz,
                          jobject _jtask) {
    // clazz: NativeNetTaskAdapter
    jclass clzz = _env->GetObjectClass(_jtask);
    jfieldID field_id_net_id = _env->GetFieldID(clzz, "netID", "I");
    jfieldID field_id_cgi = _env->GetFieldID(clzz, "cgi", "Ljava/lang/String;");
    jfieldID field_id_retry_cnt = _env->GetFieldID(clzz, "retryCount", "I");
    jfieldID field_id_care_about_progress = _env->GetFieldID(clzz, "careAboutProgress", "Z");

    jint netid = _env->GetIntField(_jtask, field_id_net_id);
    jstring cgi = (jstring) _env->GetObjectField(_jtask, field_id_cgi);
    jint retry_cnt = _env->GetIntField(_jtask, field_id_retry_cnt);
    jboolean care_about_progress = _env->GetBooleanField(_jtask, field_id_care_about_progress);

    Task task;
    task.netid_ = netid;
    task.care_about_progress_ = care_about_progress;
    task.retry_cnt_ = retry_cnt;
    if (cgi != NULL) {
        task.cgi_ = _env->GetStringUTFChars(cgi, NULL);
    }

    return StartTaskImpl(task);
}

JNIEXPORT jint JNICALL
JNI_OnLoad(JavaVM *jvm, void *reserved) {
    LogI("NativeNetTaskAdapter", "[JNI_OnLoad] JVM* is saved.")
    VarCache::Instance().CacheJvm(jvm);
    ScopeJEnv scope_jenv;
    JNIEnv *env = scope_jenv.GetEnv();
    jclass clz = env->FindClass(kJavaClassNativeNetTaskAdapter);
    VarCache::Instance().CacheClass(env, kJavaClassNativeNetTaskAdapter, clz);
    return JNI_VERSION_1_6;
}

}
