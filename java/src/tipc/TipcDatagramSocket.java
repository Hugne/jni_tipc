package tipc;
import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.nio.channels.NotYetConnectedException;

public class TipcDatagramSocket implements Closeable, AutoCloseable {
	static {
		try {
			System.loadLibrary("jnitipc");
		} catch(UnsatisfiedLinkError e) {
			System.out.println("Failed to load TIPC JNI bindings" +e);
		}
	}
	
	private int fd;
	
	private native int jnidgramsocket();
	private native int jnirdmsocket();
	private native int jnibind(int fd, TipcAddress addr);
	private native int jniconnect(int fd, TipcAddress addr);
	private native int jnisend(int fd, byte[] buf, int len);
	private native int jnisendto(int fd, byte[] buf, int len, TipcAddress addr);
	private native int jnirecv(int fd, byte[] buf, int len);
	private native int jnirecvfrom(int fd, byte[] buf, int len, TipcAddress addr);
	private native int jniclose(int fd);


	public TipcDatagramSocket(boolean reliable) {
		if (reliable)
			fd = jnirdmsocket();
		else
			fd = jnidgramsocket();
		System.out.println("TIPC Socket created #" + fd);
	}
	
	@Override
	public void close() throws IOException {
		this.jniclose(fd);
	}
	
	public void bind(TipcAddress addr)
	{
		this.jnibind(fd, addr);
	}
	public void connect(TipcAddress addr)
	{
		this.jniconnect(fd, addr);
	}
	public void send(byte[] buf, int len)
	{
		this.jnisend(fd, buf, len);
	}
	public void sendto(byte[] buf, int len, TipcAddress addr)
	{
		this.jnisendto(fd, buf, len, addr);
	}
	public void recv(byte[] buf, int len)
	{
		this.jnirecv(fd, buf, len);
	}
	public void recvfrom(byte[] buf, int len, TipcAddress addr)
	{
		this.jnirecvfrom(fd, buf, len, addr);
	}
}
