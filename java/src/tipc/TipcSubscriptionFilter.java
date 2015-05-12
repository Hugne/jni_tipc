package tipc;

public enum TipcSubscriptionFilter {
	TIPC_SUB_PORTS(0x01),
	TIPC_SUB_SERVICE(0x02),
	TIPC_SUB_CANCEL(0x04);
	
	private final int value;
	private TipcSubscriptionFilter(int value)
	{
		this.value = value; 
	}
}
