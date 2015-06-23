#include <jni.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <sys/socket.h>
#include <poll.h>
#include <linux/tipc.h>

JNIEXPORT jint JNICALL
Java_tipc_TipcJniServiceAdaptor_jnisocket(JNIEnv *env, jobject thisObj, jint socktype)
{
	int fd = socket(AF_TIPC, socktype, 0);

	if (fd < 0)
		perror("socket");
	return fd;
}

JNIEXPORT jint JNICALL
Java_tipc_TipcJniServiceAdaptor_jnibind(JNIEnv *env, jobject thisObj, jint fd,
				     jint addrtype, jint type, jint lower,
				     jint upper, jint scope) {
	struct sockaddr_tipc sa = {
		.family = AF_TIPC,
		.addrtype = TIPC_ADDR_NAMESEQ,
		.scope = scope,
		.addr.nameseq.type = type,
		.addr.nameseq.lower = lower,
		.addr.nameseq.upper = upper
	};

	if (bind(fd, (struct sockaddr*) &sa, sizeof(sa))) {
		perror("bind");
		return -errno;
	}
	return 0;
}

JNIEXPORT jint JNICALL
Java_tipc_TipcJniServiceAdaptor_jniconnect(JNIEnv *env, jobject thisObj, jint fd,
					jint type, jint instance)
{
	struct sockaddr_tipc sa = {
		.family = AF_TIPC,
		.addrtype = TIPC_ADDR_NAME,
		.addr.name.name.type = type,
		.addr.name.name.instance = instance
	};
	fprintf(stderr, "Connect to type %d instance %d\n", type, instance);
	if (connect(fd, (struct sockaddr*) &sa, sizeof(sa))){
		perror("connect");
		return -errno;
	}
	return 0;
}
static void print_sub(const char *str, struct tipc_subscr *s)
{
       fprintf(stderr, "%s {%u,%u,%u}, timeout %u, user ref: %u\n",
               str, ntohl(s->seq.type), ntohl(s->seq.lower),
               ntohl(s->seq.upper), ntohl(s->timeout),
               (unsigned int)s->usr_handle[0]);
}

JNIEXPORT jint JNICALL
Java_tipc_TipcJniServiceAdaptor_jnisend(JNIEnv *env , jobject thisObj, jint fd,
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
	print_sub("SUB:", (struct tipc_subscr*)data);
	if (isCopy)
		(*env)->ReleaseByteArrayElements(env, buf, data, JNI_ABORT);
	return 0;
}

JNIEXPORT jint JNICALL
Java_tipc_TipcJniServiceAdaptor_jnisendto(JNIEnv *env,jobject thisObj, jint fd,
				       jbyteArray buf, jint len, jint type,
				       jint lower, jint upper)
{
	struct sockaddr_tipc sa = {
		.family = AF_TIPC,
		.addrtype = TIPC_ADDR_NAMESEQ,
		.scope = TIPC_ZONE_SCOPE,
		.addr.nameseq.type = type,
		.addr.nameseq.lower = lower,
		.addr.nameseq.upper = upper
	};

	jboolean isCopy;
	char *data;
	/*TODO: Something smarter that does not create buffer copies*/
	data = (char*)(*env)->GetByteArrayElements(env, buf, &isCopy);
	if (sendto(fd, data, len, 0, (struct sockaddr*) &sa, sizeof(sa)) < 0)
		perror("sendto");
	if (isCopy)
		(*env)->ReleaseByteArrayElements(env, buf, data, JNI_ABORT);
	return 0;
}

JNIEXPORT jint JNICALL
Java_tipc_TipcJniServiceAdaptor_jnirecv(JNIEnv *env, jobject thisObj, jint fd,
				     jbyteArray buf, jint len)
{
	char *data;
	jboolean isCopy;
	int i;

	/*TODO: Something smarter that does not create buffer copies*/
	/*TODO2: Capture sender address*/
	data = (*env)->GetByteArrayElements(env, buf, &isCopy);
	if (recv(fd, data, len, 0) < 0)
		perror("recv");
	(*env)->SetByteArrayRegion(env, buf, 0, len, data);
	if (isCopy)
		(*env)->ReleaseByteArrayElements(env, buf, data, JNI_ABORT);
	return 0;
}
/*
JNIEXPORT jint JNICALL
Java_tipc_TipcJniServiceAdaptor_jnirecvfrom(JNIEnv *env, jobject thisObj, jint fd,
					 jbyteArray buf, jint len)
{
	struct sockaddr_tipc sa;
	socklen_t sa_len = sizeof(sa);
	char *data;
	jboolean isCopy;
	int i;

	/*TODO: Something smarter that does not create buffer copies
	data = (*env)->GetByteArrayElements(env, buf, &isCopy);
	if (recvfrom(fd, data, len, 0, (struct sockaddr*)&sa, &sa_len) < 0)
		perror("recvfrom");
	(*env)->SetByteArrayRegion(env, buf, 0, len, data);
	if (isCopy)
		(*env)->ReleaseByteArrayElements(env, buf, data, JNI_ABORT);
	return 0;
} */
JNIEXPORT jint JNICALL
Java_tipc_TipcJniServiceAdaptor_jnipoll(JNIEnv *env, jobject thisObj, jint fd,
					jint events, jint timeout)
{
	struct pollfd pfd;
	int res;

	pfd.fd = fd;
	pfd.events = events;
	res = poll(&pfd, 1, timeout);
	if (res > 0)
		return pfd.revents;
	else if (res < 0)
		perror("poll");
	return 0;
}
JNIEXPORT jint JNICALL
Java_tipc_TipcJniServiceAdaptor_jniclose(JNIEnv *env, jobject thisObj, jint fd)
{
	if (close(fd))
		perror("close");
	return 0;
}
