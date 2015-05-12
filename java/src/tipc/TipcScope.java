package tipc;

public enum TipcScope {
	TIPC_ZONE_SCOPE(1),
	TIPC_CLUSTER_SCOPE(2),
	TIPC_NODE_SCOPE(3);
	
	private final int value;
	private TipcScope(int value)
	{
		this.value = value;
	}
}
