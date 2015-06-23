import tipc.ServiceDiscoveryAgent;

public class TestClient extends ServiceDiscoveryAgent {
	public void event(ServiceEvent e)
	{
		System.out.println("Service discovery callback triggered: "+e.toString());

	}
}
