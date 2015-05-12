package tipc;

public class TipcSubscription {
	public TipcNameSeq sequence;
	public int timeout;
	public TipcSubscriptionFilter filter;
	char callback_args[];
	
	public TipcSubscription(TipcNameSeq sequence, int timeout,
			TipcSubscriptionFilter filter)
	{
		this.sequence = sequence;
		this.timeout = timeout;
		this.filter = filter;
	}
}
