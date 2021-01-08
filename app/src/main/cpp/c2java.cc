#include <jni.h>



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


