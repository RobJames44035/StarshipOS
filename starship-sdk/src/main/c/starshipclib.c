#include <jni.h>
#include <stdio.h>
#include "org_starship_CLibJNI.h"

   JNIEXPORT void JNICALL Java_org_starship_CLibJNI_exampleMethod(JNIEnv *env, jobject obj) {
       printf("Hello from JNI!\n");
   }
