#include <jni.h>
#include <stdio.h>
#include <errno.h>
#include <sys/socket.h>
#include <linux/tipc.h>

JNIEXPORT jint JNICALL Java_tipc_TipcDatagramSocket_jnidgramsocket(JNIEnv *env,
							      jobject thisObj)
{
	int fd = socket(AF_TIPC, SOCK_DGRAM, 0);

	if (fd < 0)
		perror("socket");
	return fd;
}
JNIEXPORT jint JNICALL Java_tipc_TipcDatagramSocket_jnirdmsocket(JNIEnv *env,
							    jobject thisObj)
{
	int fd = socket(AF_TIPC, SOCK_RDM, 0);

	if (fd < 0)
		perror("socket");
	return fd;
}

JNIEXPORT jint JNICALL Java_tipc_TipcDatagramSocket_jnibind(JNIEnv *env,
						       jobject thisObj,
						       jint fd, jobject addr) {
//	if (bind(fd, 
	printf("Hello World!\n");
	return 0;
}

JNIEXPORT jint JNICALL Java_tipc_TipcDatagramSocket_jniconnect(JNIEnv *env,
							  jobject thisObj,
							  jint fd, jobject addr)
{
	return 0;
}

JNIEXPORT jint JNICALL Java_tipc_TipcDatagramSocket_jniclose(JNIEnv *env,
							jobject thisObj, jint fd)
{
	if (close(fd))
		perror("close");
	return 0;
}
