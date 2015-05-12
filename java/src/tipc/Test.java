package tipc;
import java.io.IOException;



public class Test {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		TipcDatagramSocket ds = new TipcDatagramSocket(true);
		try {
			ds.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
