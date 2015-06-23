package tipc;

public class TipcJniServiceAdaptor {
	public static final int TIPC_ADDR_NAME = 2;
	public static final int TIPC_ADDR_NAMESEQ = 1;
	
	public static final int TIPC_ZONE_SCOPE = 1;
	public static final int TIPC_WITHDRAW = -1;
	
	public static final int POLLIN = 0x001;
	public static final int POLLPRI = 0x002;
	
	
	public static final int SOCK_RDM = 4;
	public static final int SOCK_SEQPACKET = 5;
	
	static {
		try {
			System.loadLibrary("jnitipc");
		} catch(UnsatisfiedLinkError e) {
			System.out.println("Failed to load TIPC JNI bindings");
		}
		System.out.println("JNI lib loaded\n");
	}
	private static native int jnisocket(int socktype);
	private static native int jnibind(int fd, int addrtype, int type, int lower, int upper, int scope);
	private static native int jniconnect(int fd, int type, int instance);
	private static native int jnisend(int fd, byte[] buf, int len);
	private static native int jnisendto(int fd, byte[] buf, int len, int type, int lower, int upper);
	private static native int jnirecv(int fd, byte[] buf, int len);
	private static native int jnipoll(int fd, int events, int timeout);
	private static native int jniclose(int fd);
	
	static int socket(int type) {
		return jnisocket(type);
	}
	
	static int bind(int fd, int type, int lower, int upper, int scope)
	{
		return jnibind(fd, TIPC_ADDR_NAMESEQ, type, lower, upper, scope);
	}
	
	static int connect(int fd, int type, int instance)
	{
		return jniconnect(fd, type, instance);
	}
	
	static int recv(int fd, byte[] buf, int len)
	{
		return jnirecv(fd, buf, len);
	}
	
	static int poll(int fd, int events, int timeout)
	{
		return jnipoll(fd, events, timeout);
	}

	static int send(int fd, byte[] buf, int len) {
			return jnisend(fd, buf, len);
	}
	
	static int sendto(int fd, byte[] buf, int len, int type, int lower, int upper)
	{
		return jnisendto(fd, buf, len, type, lower, upper);
	}
	static int close(int fd) {
		return jniclose(fd);
		
	}
}
