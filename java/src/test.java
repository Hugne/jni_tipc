import tipc.ServiceException;

public class test {
	
	public static void main(String[] args) throws InterruptedException, ServiceException {
		TestService sa = new TestService();
		sa.publish("Banana");
		sa.start();
		
		String h = "Hello world!";
		sa.send(h.getBytes(), h.length(), "Banana");
		Thread.sleep(2000);
		sa.stop();
	}

}