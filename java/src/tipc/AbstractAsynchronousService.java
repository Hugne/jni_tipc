package tipc;

/*
 * Spawns a thread that waits for data on the socket, will invoke the
 * receive method with any received data.
 */
public abstract class AbstractAsynchronousService extends AbstractService implements Runnable {

	private Thread sthread;


	public void run() {
		while (true) {
			if (TipcJniServiceAdaptor.poll(fd, TipcJniServiceAdaptor.POLLIN |
										   TipcJniServiceAdaptor.POLLPRI, 0) > 0)
					recv();

			if (sthread.isInterrupted())
				return;
		}
	}

	
	public void start() throws ServiceException {
		if (fd == 0) {
			throw new ServiceException("Failed to start service, socket is not open");
		}
		sthread = new Thread(this);
		sthread.start();
	}

	public void stop() {
		sthread.interrupt();
		try {
			sthread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.terminate();
	}
	
	private void recv() {
		//TODO capture sender address
		int len = 66000;
		byte buf[] = new byte[len];
		len = TipcJniServiceAdaptor.recv(fd,  buf, len);
		if (len >= 0) {
			receive(buf, len);
		} else {
			System.out.println("An error occured while receiving data");				
		}
	}
	public abstract void receive(byte[] buf, int len);
	
}
