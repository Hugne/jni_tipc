#ifndef COMMON_H_
#define COMMON_H_
jobject getObjFromSignature(JNIEnv *env, jobject obj, const char *name,
	       			   const char *signature);
jint getIntFromSignature(JNIEnv *env, jobject obj, const char *name);

int TipcAddressToSockaddr(JNIEnv *env, jobject addr,
				 struct sockaddr_tipc *sa);
int sockaddrToTipcAddress(JNIEnv *env, struct sockaddr_tipc *sa,
				 jobject addr);

#endif
