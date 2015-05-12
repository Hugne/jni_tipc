package tipc;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.net.SocketException;



public class Test {
	/**
	 * @param args
	 */
	public static void test1()
	{
		TipcDatagramSocket drecv = new TipcDatagramSocket(true);
		TipcAddress addr = new TipcAddress(new TipcNameSeq(1001,0,100), TipcScope.TIPC_ZONE_SCOPE);
		System.out.println("Bind: "+addr.toString());
		drecv.bind(addr);

		TipcDatagramSocket dsend = new TipcDatagramSocket(true);
		System.out.println("send data to: "+addr.toString());
		String test = new String("Datadatadata!   ");
		byte[] b = test.getBytes();
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
	public static void test2()
	{
		TipcDatagramSocket drecv = new TipcDatagramSocket(true);
		TipcAddress addr = new TipcAddress(new TipcNameSeq(1001,0,100), TipcScope.TIPC_ZONE_SCOPE);
		System.out.println("Bind: "+addr.toString());
		drecv.bind(addr);
		TipcDatagramSocket dsend = new TipcDatagramSocket(true);
		System.out.println("send data to: "+addr.toString());
		String test = new String("Datadatadata!   ");
		byte[] b = test.getBytes();
		dsend.connect(addr);
		dsend.send(b, b.length);

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
	public static void test3()
	{
		TipcDatagramSocket drecv = new TipcDatagramSocket(true);
		TipcAddress addr = new TipcAddress(new TipcNameSeq(1001,0,100), TipcScope.TIPC_ZONE_SCOPE);
		System.out.println("Bind: "+addr.toString());
		drecv.bind(addr);
		TipcDatagramSocket dsend = new TipcDatagramSocket(true);
		System.out.println("send data to: "+addr.toString());
		String test = new String("Datadatadata!   ");
		byte[] b = test.getBytes();
		dsend.sendto(b, b.length, addr);

		TipcAddress arecv = null;
		byte[] rcvbuf = new byte[255];
		drecv.recv(rcvbuf, 255);
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
	public static void main(String[] args) {
		System.out.println("Test 1: socket, bind, sendto and recvfrom");
		test1();
		//System.out.println("Test 2: socket, bind, connect, send and recv (requires a recent kernel)");
		//test2();
		System.out.println("Test 3: socket, bind sendto and recv");
		test3();

	}
}
