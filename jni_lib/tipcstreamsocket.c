#include <jni.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <sys/socket.h>
#include <linux/tipc.h>

JNIEXPORT jint JNICALL
Java_tipc_TipcServerSocket_jnistreamsocket(JNIEnv *env, jobject thisObj)
{
	return 0;
}

/*
 * Class:     tipc_TipcServerSocket
 * Method:    jnibind
 * Signature: (ILtipc/TipcAddress;)I
 */
JNIEXPORT jint JNICALL
Java_tipc_TipcServerSocket_jnibind(JNIEnv *env, jobject thisObj, jint fd,
				   jobject TipcAddress)
{
	return 0;
}

/*
 * Class:     tipc_TipcServerSocket
 * Method:    jniconnect
 * Signature: (ILtipc/TipcAddress;)I
 */
JNIEXPORT jint JNICALL
Java_tipc_TipcServerSocket_jniconnect(JNIEnv *env, jobject thisObj, jint fd,
				      jobject TipcAddress)
{
	return 0;
}

/*
 * Class:     tipc_TipcServerSocket
 * Method:    jnisend
 * Signature: (I[BI)I
 */
JNIEXPORT jint JNICALL
Java_tipc_TipcServerSocket_jnisend(JNIEnv *env, jobject thisObj, jint fd,
				   jbyteArray buf, jint len)
{
	return 0;
}

/*
 * Class:     tipc_TipcServerSocket
 * Method:    jnirecv
 * Signature: (I[BI)I
 */
JNIEXPORT jint JNICALL
Java_tipc_TipcServerSocket_jnirecv(JNIEnv *env, jobject thisObj, jint fd,
				   jbyteArray buf, jint len)
{
	return 0;
}

/*
 * Class:     tipc_TipcServerSocket
 * Method:    jnirecvfrom
 * Signature: (I[BILtipc/TipcAddress;)I
 */
JNIEXPORT jint JNICALL
Java_tipc_TipcServerSocket_jnirecvfrom(JNIEnv *env, jobject thisObj, jint fd,
				       jbyteArray buf, jint len,
				       jobject TipcAddress)
{
	return 0;
}

/*
 * Class:     tipc_TipcServerSocket
 * Method:    jniclose
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL
Java_tipc_TipcServerSocket_jniclose(JNIEnv *env, jobject thisObj, jint fd)
{
	return 0;
}


