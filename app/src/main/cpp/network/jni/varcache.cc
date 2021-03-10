#include "varcache.h"
#include "scopejenv.h"
#include "log.h"


const char *const VarCache::TAG = "VarCache";

VarCache::VarCache()
    : jvm_(NULL) {}

void VarCache::CacheJvm(JavaVM *_jvm) {
    if (_jvm != NULL) {
        jvm_ = _jvm;
    }
}

JavaVM *VarCache::GetJvm() { return jvm_; }

VarCache::~VarCache() {
    ScopeJEnv scope_jenv;
    JNIEnv *env = scope_jenv.GetEnv();
    for (auto & it : class_map_) {
        env->DeleteGlobalRef(it.second);
    }
}

void VarCache::CacheClass(JNIEnv *_env, const char *_clz_path, jclass _clz) {
    if (_clz == NULL || _clz_path == NULL || _env == NULL) {
        LogE(TAG, "[CacheClass] cache failed.")
        return;
    }
    auto global_ref = (jclass) _env->NewGlobalRef(_clz);

    class_map_.insert(std::make_pair(_clz_path, global_ref));
}

jclass VarCache::GetClass(const char *_clz_path) {
    for (auto & it : class_map_) {
        if (strcmp(it.first.c_str(), _clz_path) == 0) {
            return it.second;
        }
    }
    return NULL;
}

