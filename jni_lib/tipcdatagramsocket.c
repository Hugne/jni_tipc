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
		printf("Nameseq\n");
		obj = getObjFromSignature(env, addr, "nameseq",
					  "Ltipc/TipcNameSeq;");
		if (!obj)
			goto out_err;
		sa->addr.nameseq.type = getIntFromSignature(env, obj, "type");
		sa->addr.nameseq.lower = getIntFromSignature(env, obj, "lower");
		sa->addr.nameseq.upper = getIntFromSignature(env, obj, "upper");
	break;
	case TIPC_ADDR_NAME:
		printf("Name\n");
		sa->addr.name.domain = getIntFromSignature(env, addr, "domain");
		obj = getObjFromSignature(env, addr, "name", "Ltipc/TipcName;");
		if (!obj)
			goto out_err;
		sa->addr.name.name.type = getIntFromSignature(env, obj, "type");
		sa->addr.name.name.instance = getIntFromSignature(env, obj,
								  "instance");
	break;
	case TIPC_ADDR_ID:
		printf("Id\n");
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
	struct sockaddr_tipc sa;
	printf("Hello World!\n");

	if (TipcAddressToSockaddr(env, addr, &sa)) {
		fprintf(stderr, "bind: Address error\n");
		return -1;
	}
	if (bind(fd, (struct sockaddr*) &sa, sizeof(sa)))
		perror("bind");
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
