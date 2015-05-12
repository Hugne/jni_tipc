package tipc;
import java.net.SocketAddress;

public class TipcAddress extends SocketAddress {
	static final short family = 30;	//AF_TIPC
	TipcAddressType type;
	TipcScope scope;
	TipcPortId portid;
	TipcNameSeq nameseq;
	TipcName name;
	int domain;
	
	public TipcAddress(TipcNameSeq nameseq, TipcScope scope)
	{
		this.type = TipcAddressType.TIPC_ADDR_NAMESEQ;
		this.nameseq = nameseq;
		this.scope = scope;
	}
	
	public TipcAddress(TipcName name, TipcScope scope)
	{
		this.type = TipcAddressType.TIPC_ADDR_NAME;
		this.name = name;
		this.scope = scope;
		this.domain = 0;
	}
	
	public TipcAddress(TipcPortId id)
	{
		this.type = TipcAddressType.TIPC_ADDR_ID;
		this.scope = TipcScope.TIPC_ZONE_SCOPE;	
	}
	//TODO: more constructors
	
	public String toString()
	{
		System.out.println(this.getClass().getName());
		//TODO: print scope and domain aswell
		switch(this.type) {
		case TIPC_ADDR_ID:
			return portid.toString();
		case TIPC_ADDR_NAME:
			return name.toString();
		case TIPC_ADDR_NAMESEQ:
			return nameseq.toString();
		default:
			break;
		}
		return new String("Foo");
	}
}
