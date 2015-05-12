
public enum TipcImportance {
	TIPC_LOW_IMPORTANCE(0),
	TIPC_MEDIUM_IMPORTANCE(1),
	TIPC_HIGH_IMPORTANCE(2),
	TIPC_CRITICAL_IMPORTANCE(3);
	
	private final int value;
	private TipcImportance(int value)
	{
		this.value = value;
	}
}
