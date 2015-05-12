#include <jni.h>
#include <stdio.h>
 
JNIEXPORT void JNICALL Java_Hello_hello(JNIEnv *env, jobject thisObj) {
	printf("Hello World!\n");
}
