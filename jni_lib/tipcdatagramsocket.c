#include <jni.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <sys/socket.h>
#include <linux/tipc.h>

#include "common.h"

JNIEXPORT jint JNICALL
Java_tipc_TipcDatagramSocket_jnidgramsocket(JNIEnv *env, jobject thisObj)
{
	int fd = socket(AF_TIPC, SOCK_DGRAM, 0);

	if (fd < 0)
		perror("socket");
	return fd;
}
JNIEXPORT jint JNICALL
Java_tipc_TipcDatagramSocket_jnirdmsocket(JNIEnv *env, jobject thisObj)
{
	int fd = socket(AF_TIPC, SOCK_RDM, 0);

	if (fd < 0)
		perror("socket");
	return fd;
}

JNIEXPORT jint JNICALL
Java_tipc_TipcDatagramSocket_jnibind(JNIEnv *env, jobject thisObj, jint fd,
				     jobject addr) {
	struct sockaddr_tipc sa;

	if (TipcAddressToSockaddr(env, addr, &sa)) {
		fprintf(stderr, "bind: Address error\n");
		return -1;
	}
	if (bind(fd, (struct sockaddr*) &sa, sizeof(sa)))
		perror("bind");
	return 0;
}

JNIEXPORT jint JNICALL
Java_tipc_TipcDatagramSocket_jniconnect(JNIEnv *env, jobject thisObj, jint fd,
					jobject addr)
{
	struct sockaddr_tipc sa;

	if (TipcAddressToSockaddr(env, addr, &sa)) {
		fprintf(stderr, "connect: Address error\n");
		return -1;
	}
	if (connect(fd, (struct sockaddr*) &sa, sizeof(sa)))
		perror("connect");
	return 0;
}

JNIEXPORT jint JNICALL
Java_tipc_TipcDatagramSocket_jnisend(JNIEnv *env , jobject thisObj, jint fd,
				     jbyteArray buf, jint len)
{
	jboolean isCopy;
	char *data;
	/*TIPC dgram/rdm sockets can be 'connected' in the sense that
	 * a previous call to connect() have associated a peer address
	 * with the socket */
	/*TODO: Something smarter that does not create buffer copies*/
	data = (char*)(*env)->GetByteArrayElements(env, buf, &isCopy);
	if (send(fd, data, len, 0) < 0)
		perror("sendto");
	if (isCopy)
		(*env)->ReleaseByteArrayElements(env, buf, data, JNI_ABORT);
	return 0;
}

JNIEXPORT jint JNICALL
Java_tipc_TipcDatagramSocket_jnisendto(JNIEnv *env,jobject thisObj, jint fd,
				       jbyteArray buf, jint len, jobject addr)
{
	struct sockaddr_tipc sa;
	jboolean isCopy;
	char *data;

	if (TipcAddressToSockaddr(env, addr, &sa)) {
		fprintf(stderr, "sendto: Address error\n");
		return -1;
	}
	/*TODO: Something smarter that does not create buffer copies*/
	data = (char*)(*env)->GetByteArrayElements(env, buf, &isCopy);
	if (sendto(fd, data, len, 0, (struct sockaddr*) &sa, sizeof(sa)) < 0)
		perror("sendto");
	if (isCopy)
		(*env)->ReleaseByteArrayElements(env, buf, data, JNI_ABORT);
	return 0;
}
JNIEXPORT jint JNICALL
Java_tipc_TipcDatagramSocket_jnirecv(JNIEnv *env, jobject thisObj, jint fd,
				     jbyteArray buf, jint len)
{
	char *data;
	jboolean isCopy;
	int i;

	/*TODO: Something smarter that does not create buffer copies*/
	/*TODO2: Capture sender address*/
	data = (*env)->GetByteArrayElements(env, buf, &isCopy);
	if (recv(fd, data, len, 0) < 0)
		perror("recvfrom");
	(*env)->SetByteArrayRegion(env, buf, 0, len, data);
	if (isCopy)
		(*env)->ReleaseByteArrayElements(env, buf, data, JNI_ABORT);
	return 0;
}
JNIEXPORT jint JNICALL
Java_tipc_TipcDatagramSocket_jnirecvfrom(JNIEnv *env, jobject thisObj, jint fd,
					 jbyteArray buf, jint len, jobject addr)
{
	struct sockaddr_tipc sa;
	socklen_t sa_len = sizeof(sa);
	char *data;
	jboolean isCopy;
	int i;

	/*TODO: Something smarter that does not create buffer copies*/
	data = (*env)->GetByteArrayElements(env, buf, &isCopy);
	if (recvfrom(fd, data, len, 0, (struct sockaddr*)&sa, &sa_len) < 0)
		perror("recvfrom");
	(*env)->SetByteArrayRegion(env, buf, 0, len, data);
	if (isCopy)
		(*env)->ReleaseByteArrayElements(env, buf, data, JNI_ABORT);
	return 0;
}

JNIEXPORT jint JNICALL
Java_tipc_TipcDatagramSocket_jniclose(JNIEnv *env, jobject thisObj, jint fd)
{
	if (close(fd))
		perror("close");
	return 0;
}
