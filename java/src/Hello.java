

public class Hello {

	static {
		try {
			System.loadLibrary("hello");
		} catch(UnsatisfiedLinkError e) {
			System.out.println("library error" +e);
		}

	}
	private native void hello();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Hello().hello();
		TipcDatagramSocket ds = new TipcDatagramSocket(true);
		
	}
	

}
