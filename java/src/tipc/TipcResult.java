package tipc;

public enum TipcResult {
	TIPC_OK(0),
	TIPC_ERR_NO_NAME(1),
	TIPC_ERR_NO_PORT(2),
	TIPC_ERR_NO_NODE(3),
	TIPC_ERR_OVERLOAD(4),
	TIPC_CONN_SHUTDOWN(5);
	
	private final int value;
	private TipcResult(int value)
	{
		this.value = value;
	}
}
