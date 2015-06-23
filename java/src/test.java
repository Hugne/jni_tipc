import tipc.ServiceException;

public class test {
	
	public static void main(String[] args) throws InterruptedException, ServiceException {
		/*Spawn a client and subscribe for the Banana service*/
		TestClient cli = new TestClient();
		System.out.println("Test:: Client started, waiting for a banana");
		cli.subscribe("Banana");
		
		/*Spawn a server and publish Banana
		 * When it becomes available, the TestClient event callback will be invoked*/
		TestService srv = new TestService();
		System.out.println("Test:: Publish the service");
		srv.publish("Banana");
		srv.publish(10000, 400, 900, srv.TIPC_ZONE_SCOPE);
		srv.start();

		Thread.sleep(1000);
		System.out.println("Test:: Shutting down");
		srv.stop();
		cli.stop();
	}

}
