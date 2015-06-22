package tipc;

/**
 * 
 * The basic service class, can be subclassed if you
 * want to write the JNI glue yourself
 *
 */
public abstract class AbstractService {

	protected int fd = 0;
	private static final int AUTOGEN_TYPE = 979797;
	
	
	public void publish(String name) throws ServiceException {
		if (fd == 0)
			fd = TipcJniServiceAdaptor.socket();
		if (fd == 0) 
			throw new ServiceException("Could not publish service, failed to open socket");
		int instance = name.hashCode();
		//TODO .... this is not enough, but works for the simple development/testing case
		if (TipcJniServiceAdaptor.bind(fd, AUTOGEN_TYPE, instance, instance,
									   TipcJniServiceAdaptor.TIPC_ZONE_SCOPE) != 0)
			throw new ServiceException("Could not publish service, failed to bind socket");
	}
	
	public void withdraw(String name) throws ServiceException
	{
		if (fd == 0)
			throw new ServiceException("Failed to withdraw, socket is not open");
		int instance = name.hashCode();
		if (TipcJniServiceAdaptor.bind(fd, AUTOGEN_TYPE, instance, instance,
									   TipcJniServiceAdaptor.TIPC_WITHDRAW) != 0)
			throw new ServiceException("Could not publish service, failed to bind socket");
	}

	public void send(byte[] buf, int len, String name) throws ServiceException {
		if (fd == 0)
			fd = TipcJniServiceAdaptor.socket();
		if (fd == 0) 
			throw new ServiceException("Could not send, failed to open socket");
		int instance = name.hashCode();
		if (TipcJniServiceAdaptor.sendto(fd, buf, len, AUTOGEN_TYPE, instance, instance) < 0)
			throw new ServiceException("Send failed");
	}
	
	public abstract void receive(byte[] buf, int len);
	
	
}
