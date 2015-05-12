package tipc;
import java.io.IOException;



public class Test {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TipcDatagramSocket ds = new TipcDatagramSocket(true);
		TipcAddress addr = new TipcAddress(new TipcNameSeq(1000,0,100), TipcScope.TIPC_ZONE_SCOPE);
		System.out.println("Bind: "+addr.toString());
		ds.bind(addr);
		try {
			ds.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
