
public class TipcPortId {
	public int ref;
	public int node;
	
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		result.append("<");
		result.append(node>>24);
		result.append(".");
		result.append((node>>12)&0xfff);
		result.append(".");
		result.append(node&0xfff);
		result.append(":");
		result.append(ref);
		result.append(">");
		return result.toString();
	}
}
