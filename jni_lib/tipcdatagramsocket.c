#include <jni.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <sys/socket.h>
#include <linux/tipc.h>

static jobject getObjFromSignature(JNIEnv *env, jobject obj, const char *name,
	       			   const char *signature)
{
	jclass class;
	jfieldID fid;

	class = (*env)->GetObjectClass(env, obj);
	if (!class)
		return;
	fid = (*env)->GetFieldID(env, class, name, signature);
	if (!fid)
		return;
	return (*env)->GetObjectField(env, obj, fid);
}

static jint getIntFromSignature(JNIEnv *env, jobject obj, const char *name)
{
	jclass class;
	jfieldID fid;

	class = (*env)->GetObjectClass(env, obj);
	if (!class)
		return 0;
	fid = (*env)->GetFieldID(env, class, name, "I");
	if (!fid)
		return 0;
	return (*env)->GetIntField(env, obj, fid);


}

static int TipcAddressToSockaddr(JNIEnv *env, jobject addr,
				 struct sockaddr_tipc *sa)
{
	jobject obj;

	sa->family = AF_TIPC;
	obj = getObjFromSignature(env, addr, "type", "Ltipc/TipcAddressType;");
	if (!obj)
		goto out_err;
	sa->addrtype = getIntFromSignature(env, obj, "value");
	obj = getObjFromSignature(env, addr, "scope", "Ltipc/TipcScope;");
	if (!obj)
		goto out_err;
	sa->scope = (char) getIntFromSignature(env, obj, "value");
	switch (sa->addrtype) {
	case TIPC_ADDR_NAMESEQ:
		obj = getObjFromSignature(env, addr, "nameseq",
					  "Ltipc/TipcNameSeq;");
		if (!obj)
			goto out_err;
		sa->addr.nameseq.type = getIntFromSignature(env, obj, "type");
		sa->addr.nameseq.lower = getIntFromSignature(env, obj, "lower");
		sa->addr.nameseq.upper = getIntFromSignature(env, obj, "upper");
	break;
	case TIPC_ADDR_NAME:
		sa->addr.name.domain = getIntFromSignature(env, addr, "domain");
		obj = getObjFromSignature(env, addr, "name", "Ltipc/TipcName;");
		if (!obj)
			goto out_err;
		sa->addr.name.name.type = getIntFromSignature(env, obj, "type");
		sa->addr.name.name.instance = getIntFromSignature(env, obj,
								  "instance");
	break;
	case TIPC_ADDR_ID:
		obj = getObjFromSignature(env, addr, "portid",
						  "Ltipc/TipcPortId;");
		if (!obj)
			goto out_err;
		sa->addr.id.node = getIntFromSignature(env, obj, "node");
		sa->addr.id.ref = getIntFromSignature(env, obj, "ref");
	break;
	default:
		goto out_err;
	}

	return 0;
out_err:
	fprintf(stderr, "Address error\n");
	memset(sa, 0, sizeof(*sa));
	return -1;
}

static int sockaddrToTipcAddress(JNIEnv *env, struct sockaddr_tipc *sa,
				 jobject addr)
{
	printf("fixme\n");
	return 0;
}


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
	printf("jnisend\n");
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
	printf("jnirecv\n");

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

	if ((i = recvfrom(fd, data, len, 0, (struct sockaddr*)&sa, &sa_len)) < 0)
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
