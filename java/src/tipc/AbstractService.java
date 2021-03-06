package tipc;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * The basic service class, can be subclassed if you
 * want to write the JNI glue yourself
 *
 */
public abstract class AbstractService {

	private ArrayList<String> pnames = new ArrayList<String>();
	protected int fd = 0;
	public static final int AUTOGEN_TYPE = 979797;
	
	public static final int TIPC_ZONE_SCOPE = 1;
	public static final int TIPC_CLUSTER_SCOPE = 2;
	public static final int TIPC_NODE_SCOPE = 3;
	public static final int TIPC_WITHDRAW = -1;

	public AbstractService()
	{
		fd = TipcJniServiceAdaptor.socket(TipcJniServiceAdaptor.SOCK_RDM);
		if (fd == 0) 
			throw new UnsupportedOperationException("Failed to open TIPC socket, is the module loaded?");
	}
	
	public void publish(String name) throws ServiceException {
		/*It's actually possible and legal to publish the same {type, lower, upper}
		 * in TIPC*/
		Iterator<String> i = pnames.iterator();
		while (i.hasNext()) {
			if (i.next().equals(name)) {
				return;
			}
		}
		int instance = name.hashCode();
		//TODO .... this is not enough, but works for the simple development/testing case
		if (TipcJniServiceAdaptor.bind(fd, AUTOGEN_TYPE, instance, instance, TIPC_ZONE_SCOPE) != 0)
			throw new ServiceException("Could not publish service, failed to bind socket");
		pnames.add(name);
	}
	
	public void withdraw(String name) throws ServiceException
	{
		int instance = name.hashCode();
		if (TipcJniServiceAdaptor.bind(fd, AUTOGEN_TYPE, instance, instance, TIPC_WITHDRAW) != 0)
			throw new ServiceException("Could not publish service, failed to bind socket");
		Iterator<String> i = pnames.iterator();
		while (i.hasNext()) {
			if (i.next().equals(name)) {
				i.remove();
			}
		}
	}
	
	public void publish(int type, int lower, int upper, int scope) throws ServiceException {
		if (TipcJniServiceAdaptor.bind(fd, type, lower, upper, scope) != 0)
			throw new ServiceException("Could not publish service, failed to bind socket");
	}

	public void withdraw(int type, int lower, int upper) throws ServiceException {
		if (TipcJniServiceAdaptor.bind(fd, type, lower, upper, TIPC_WITHDRAW) != 0)
			throw new ServiceException("Could not withdraw service, failed to bind socket");
	}
	public void terminate() {
		TipcJniServiceAdaptor.close(fd);
	}

	public void send(byte[] buf, int len, String name) throws ServiceException {
		int instance = name.hashCode();
		if (TipcJniServiceAdaptor.sendto(fd, buf, len, AUTOGEN_TYPE, instance, instance) < 0)
			throw new ServiceException("Send failed");
	}	
	
}
