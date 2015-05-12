package tipc;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.net.SocketException;



public class Test {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TipcDatagramSocket drecv = new TipcDatagramSocket(true);
		TipcAddress addr = new TipcAddress(new TipcNameSeq(1001,0,100), TipcScope.TIPC_ZONE_SCOPE);
		System.out.println("Bind: "+addr.toString());
		drecv.bind(addr);

		TipcDatagramSocket dsend = new TipcDatagramSocket(true);
		System.out.println("send data to: "+addr.toString());
		String bajs = new String("Datadatadata!   ");
		byte[] b = bajs.getBytes();
		dsend.sendto(b, b.length, addr);

		TipcAddress arecv = null;
		byte[] rcvbuf = new byte[255];
		drecv.recvfrom(rcvbuf, 255, arecv);
		String str = new String(rcvbuf);
		System.out.println("Received: "+str);
		try {
			drecv.close();
			dsend.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
