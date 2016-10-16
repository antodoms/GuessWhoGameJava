
public class BestAttribute {
	public String name; // specifies the name of the attribute
	public String value;  // specifies the attribute value
	public int count; // specifies no of players who has the same <attribute, value>
	
	public BestAttribute(String name,String value,int count){
		this.name = name;
		this.value = value;
		this.count = count;
	}
} // End of Best Attribute Class
