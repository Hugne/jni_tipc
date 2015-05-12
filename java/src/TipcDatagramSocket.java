import java.io.Closeable;
import java.io.IOException;


public class TipcDatagramSocket implements Closeable, AutoCloseable {
	/*static {
		try {
			System.loadLibrary("jnitipc");
		} catch(UnsatisfiedLinkError e) {
			System.out.println("Failed to load TIPC JNI bindings" +e);
		}
	}*/
	
	private int fd;
	
	private native int jnidgramsocket();
	private native int jnirdmsocket();
	private native int jnibind(int fd);
	
	public TipcDatagramSocket(boolean reliable) {
		if (reliable)
			fd = jnirdmsocket();
		else
			fd = jnidgramsocket();
		System.out.println("TIPC Socket created #" + fd);
	}
	
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}
	
	public void bind(TipcAddress addr)
	{
		this.jnibind(fd);
	}

}
