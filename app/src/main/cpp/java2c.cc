#include <jni.h>
#include "task.h"
#include <thread>
#include "shortlink.h"
#include <pthread.h>

typedef pthread_t thread_tid;


extern "C"
int Java_com_cxy_oi_kernel_network_NativeNetTaskAdapter_startTask(JNIEnv *env, jclass clazz,
                                                                  jobject jtask) {

    Task task;
    task.SetCmdId(1);
//    task.SetType(1);
    task.DoSomeThing();

    return 18;
}
