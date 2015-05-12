
public class TipcNameSeq {
	public int type;
	public int lower;
	public int upper;
	
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		result.append("{");
		result.append(type);
		result.append(", ");
		result.append(lower);
		result.append(", ");
		result.append(upper);
		result.append("}");
		return result.toString();
	}
}
