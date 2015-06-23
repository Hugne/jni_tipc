import tipc.ServiceDiscoveryAgent;
import tipc.ServiceException;

public class TestClient extends ServiceDiscoveryAgent {


	private void helloworld()
	{
		/*Send something from the client to the server*/
		String h = "Hello world!";
		System.out.println("Test:: Send a string to the Banana service");
		try {
			this.send(h.getBytes(), h.length(), "Banana");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void event(ServiceEvent e)
	{
		System.out.println("Service discovery callback triggered: "+e.toString());
		helloworld();
		

	}
}
