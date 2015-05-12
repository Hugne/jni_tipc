package tipc;

public enum TipcEventId {
	TIPC_PUBLISHED(1),
	TIPC_WITHDRAWN(2),
	TIPC_TIMEOUT(3);
	
	private final int value;
	private TipcEventId(int value)
	{
		this.value = value;
	}
}
