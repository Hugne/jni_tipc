import tipc.ServiceDiscoveryAgent;
import tipc.ServiceException;

public class test {
	
	public static void main(String[] args) throws InterruptedException, ServiceException {
		TestClient cli = new TestClient();
		cli.subscribe("Banana");
		TestService sa = new TestService();
		System.out.println("Test:: Publish the service");
		sa.publish("Banana");
		Thread.sleep(2000);
		sa.start();


		String h = "Hello world!";
		System.out.println("Test:: Send a string to the Banana service");
		sa.send(h.getBytes(), h.length(), "Banana");
		Thread.sleep(2000);
		sa.stop();
		cli.stop();
	}

}
