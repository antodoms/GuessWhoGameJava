import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Map.Entry;

public class AttributeObject {
	private String attributeName; //attribute name
	private HashMap<String, Integer> attributeValueList; //attribute value list
	
	//constructor
	public AttributeObject(String string){
		attributeName = string;
		attributeValueList = new HashMap<String,Integer>();
	}
	
	// get attribute name
	public String getAttributeName(){
		return attributeName;
	}
	
	//add attribute value to hashmap
	public void addAttributeValues(String value){
		if(attributeValueList.containsKey(value)){
			attributeValueList.put(value, attributeValueList.get(value)+1);
		}else{
			attributeValueList.put(value, 0);
		}
	}
	
	//get number of players the attributes and value contains
	public int getAttributeCount(String value){
		return attributeValueList.get(value);
	}
	
	//return the arraylist of attribute values
	public ArrayList<String> getAttributeValueInOrder(){
		ArrayList<String> finallist = new ArrayList<String>();
		PriorityQueue<String> patientQueue = new PriorityQueue<String>(1, new Comparator<String>() {
	        public int compare(String value1, String value2) {
	        	return attributeValueList.get(value1).compareTo(attributeValueList.get(value2));
	       }
	    });
		finallist.addAll(patientQueue);
		return new ArrayList<>();
	}
	
	// get the best attribute among the attributes
	public BestAttribute getBestAttribute(){
		String bigValue = "";
		int bigCount =-1;
		for(Entry<String, Integer> entry : attributeValueList.entrySet()) {
		    String key = entry.getKey();
		    int value = entry.getValue();
		    if(value > bigCount){
		    	bigValue = key;
		    	bigCount = value;
		    }
		}	
		return new BestAttribute(attributeName,bigValue,bigCount);
	}
	
	// if the given value of attribute is valid
	public boolean isValidAttributeValue(String value){
		if(attributeValueList.containsKey(value))
			return true;
		
		return false;
	}

}
