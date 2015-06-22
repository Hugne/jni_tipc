import tipc.AbstractAsynchronousService;


public class TestService extends AbstractAsynchronousService {
	
	@Override
	public void receive(byte[] buf, int len) {
		// TODO Auto-generated method stub
		String s = new String(buf);
		System.out.println("Hello receive callback::\n");
		System.out.println(s);

	}
}
