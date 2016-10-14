import java.util.ArrayList;
import java.util.List;

public class AttributeObject {
	private String attributeName;
	private ArrayList<String> attributeValueList;
	
	public AttributeObject(List<String> fields){
		this.attributeName = fields.get(0);
	}
	
	public String getAttributeName(){
		return attributeName;
	}
	
	public void addAttributeValues(String value){
		attributeValueList.add(value);
	}
	
	
	
	public boolean isValidAttributeValue(String value){
		if(attributeValueList.contains(value))
			return true;
		
		return false;
	}

}
