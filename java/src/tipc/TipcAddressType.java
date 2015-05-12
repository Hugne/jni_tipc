package tipc;
public enum TipcAddressType
{
	TIPC_ADDR_NAMESEQ(1),
	TIPC_ADDR_MCAST(1),
	TIPC_ADDR_NAME(2),
	TIPC_ADDR_ID(3);

	private final int value;
	private TipcAddressType(int value)
	{
		this.value = value;
	}
	
}