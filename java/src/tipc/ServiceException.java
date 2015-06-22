package tipc;

public class ServiceException extends Exception {
	String ex;
	ServiceException(String e) {
		ex = e;
	}
	public String toString() {
		return ex;
	}
}