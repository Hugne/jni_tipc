#include <jni.h>
#include <stdio.h>
#include <errno.h>
#include <sys/socket.h>
#include <linux/tipc.h>

JNIEXPORT jint JNICALL Java_TipcDatagramSocket_jnidgramsocket(JNIEnv *env, jobject thisObj)
{
	int fd = socket(AF_TIPC, SOCK_DGRAM, 0);

	if (fd < 0)
		perror("socket");
	return fd;
}
JNIEXPORT jint JNICALL Java_TipcDatagramSocket_jnirdmsocket(JNIEnv *env, jobject thisObj)
{
	int fd = socket(AF_TIPC, SOCK_RDM, 0);

	if (fd < 0)
		perror("socket");
	return fd;
}

JNIEXPORT jint JNICALL Java_TipcDatagramSocket_jnibind(JNIEnv *env, jobject thisObj, jint fd) {
	printf("Hello World!\n");
	return 0;
}
