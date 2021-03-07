#ifndef OI_SCOPEJENV_H
#define OI_SCOPEJENV_H

#include "varcache.h"


class ScopeJEnv {
  public:
    ScopeJEnv();

    JNIEnv *GetEnv();

  private:
    static const char *const    TAG;
    JavaVM *                    jvm_;
    JNIEnv *                    jenv_;

};


#endif //OI_SCOPEJENV_H
