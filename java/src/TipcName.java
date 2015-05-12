
public class TipcName {

	public int type;
	public int instance;
	
	public String toString()
	{
		StringBuilder result = new StringBuilder();
		result.append("{");
		result.append(type);
		result.append(", ");
		result.append(instance);
		result.append("}");
		return result.toString();
	}
}
