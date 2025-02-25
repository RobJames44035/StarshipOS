#include <jni.h>
#include <stdio.h>
#include "org_starship_CLibJNI.h"

JNIEXPORT void JNICALL Java_NativeLibrary_exampleMethod(JNIEnv *env, jobject obj) {
    printf("Hello from JNI!\n");
}
