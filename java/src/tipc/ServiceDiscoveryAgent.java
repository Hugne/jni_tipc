package tipc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class ServiceDiscoveryAgent implements Runnable {
	
	private Thread sthread; 
	private int fd = 0;
	private static final int TIPC_SUB_PORTS = 0x01;
	private static final int TIPC_SUB_SERVICE = 0x02;
	private static final int TIPC_SUB_CANCEL = 0x04;
	private static final int TIPC_PUBLISHED = 0x01;
	private static final int TIPC_WITHDRAWN = 0x02;
	private static final int TIPC_SUBSCR_TIMEOUT = 0x03;
	
	
	public enum ServiceEvent {
		INSTANCE_UP,
		INSTANCE_DOWN,
		SERVICE_UP,	//TODO
		SERVICE_DOWN, //TODO
		TIMEOUT	//TODO
	}
	
	@Override
	public void run() {
		while (true) {
			if (TipcJniServiceAdaptor.poll(fd, TipcJniServiceAdaptor.POLLIN |
										   TipcJniServiceAdaptor.POLLPRI, 200) > 0)
					recv();

			if (sthread.isInterrupted())
				return;
		}
	}
	
	private void recv() {
		int len = 66000;
		byte buf[] = new byte[len];
		len = TipcJniServiceAdaptor.recv(fd,  buf, len);
		if (len >= 0) {
			ServiceEvent e = parseevent(buf, len);
			event(e);
		} else {
			System.out.println("An error occured while receiving data");				
		}
	}
	private ServiceEvent parseevent(byte[] b, int len)
	{
		ServiceEvent evt = null;
		ByteArrayInputStream data = new ByteArrayInputStream(b);
		DataInputStream s = new DataInputStream(data);
		try {
		int event = Integer.reverseBytes(s.readInt());
		/*
		int found_lower = Integer.reverseBytes(s.readInt());
		int found_upper = Integer.reverseBytes(s.readInt());
		int port_ref = Integer.reverseBytes(s.readInt());
		int port_node = Integer.reverseBytes(s.readInt());
		System.out.println("Event: "+event+"\nFound lower:"+found_lower+"\nFound_upper:"+found_upper+
				   "\nPort ref:"+port_ref+"\nPort_node"+port_node);
		*/
		switch (event) {
		case TIPC_PUBLISHED:
			evt = ServiceEvent.INSTANCE_UP;
			break;
		case TIPC_WITHDRAWN:
			evt = ServiceEvent.INSTANCE_DOWN;
			break;
		case TIPC_SUBSCR_TIMEOUT:
			evt = ServiceEvent.TIMEOUT;
			break;
		}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return evt;
	}
	
	private ByteArrayOutputStream writesub(int type, int lower, int upper, int timeout, int filter) throws IOException
	{
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		DataOutputStream stream = new DataOutputStream(data);
		stream.writeInt(Integer.reverseBytes(type));
		stream.writeInt(Integer.reverseBytes(lower));
		stream.writeInt(Integer.reverseBytes(upper));
		stream.writeInt(Integer.reverseBytes(timeout));
		stream.writeInt(Integer.reverseBytes(filter));
		stream.writeLong(Integer.reverseBytes(0));	//User ref
		return data;
	}
	
	public void subscribe(String name) throws ServiceException
	{
		if (fd == 0) {
			fd = TipcJniServiceAdaptor.socket(TipcJniServiceAdaptor.SOCK_SEQPACKET);
			TipcJniServiceAdaptor.connect(fd, 1, 1);
			if (fd == 0) {
				throw new ServiceException("Failed subscribe to service, topology socket is not open");
			}
			sthread = new Thread(this);
			sthread.start();
		}
		ByteArrayOutputStream sub;
		try {
			sub = writesub(AbstractService.AUTOGEN_TYPE, name.hashCode(),name.hashCode(), 0xFFFFFFFF, TIPC_SUB_PORTS);
		} catch (IOException e) {
			throw new ServiceException("Failed subscribe to service, an error occured while writing the subscription");
		}
		TipcJniServiceAdaptor.send(fd, sub.toByteArray(), sub.size());
	}
	
	public void stop()
	{
		sthread.interrupt();
		try {
			sthread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TipcJniServiceAdaptor.close(fd);
	}
	
	public abstract void event(ServiceEvent e);

}
