#ifndef OI_VARCACHE_H
#define OI_VARCACHE_H

#include "../utils/singleton.h"
#include <string>
#include <map>
#include <jni.h>


const char *const kJavaClassNativeNetTaskAdapter = "com/cxy/oi/kernel/network/NativeNetTaskAdapter";


class VarCache {

    SINGLETON(VarCache, )

  public:

    ~VarCache();

    void CacheJvm(JavaVM* _jvm);

    void CacheClass(JNIEnv *_env, const char *_clz_path, jclass _clz);

    jclass GetClass(const char *_clz_path);

    JavaVM *GetJvm();

  private:
    static const char *const        TAG;
    JavaVM*                         jvm_;
    std::map<std::string, jclass>   class_map_;

};


#endif //OI_VARCACHE_H
