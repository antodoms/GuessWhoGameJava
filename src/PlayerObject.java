import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class PlayerObject {
	
	private String name;
	private HashMap<String, String> attributes = new HashMap<>();
	
	public PlayerObject(String name) {
		this.name = name;
	}
	// Getter for the name
	public String getName(){
		return name;
	}
	
	public void addattributes(String attributeName, String attributeValue){
		attributes.put(attributeName, attributeValue);// Adds the attribute value
	}
	// to check whether the attribute value is present 
	public boolean isAttributeValuePresent(String attributeName, String attributeValue){
		
		if(attributes.containsKey(attributeName)){
			if(attributes.get(attributeName).equals(attributeValue)){
				return true;
			}
		}
		return false;	
	}
	
	public boolean equalsAttribute(String attrib, String value){
		if(attributes.containsKey(attrib) && attributes.get(attrib).equals(value))
			return true;
		return false;
	}
	
	// gets the attribute keys
	public ArrayList<String> getattributekeys(){
		ArrayList<String> temparray = new ArrayList<String>();
		for(Entry<String, String> entry : attributes.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
		    temparray.add(key);
		}
		return temparray;
	}
	
	// returns the attributes
	public HashMap<String, String> getattributes(){
		return attributes;
	}
	
	
	 // this generates the random attribute value to guess
	public Guess randomAttributeandValue(){
		Random generator = new Random();
		Object[] Values = attributes.keySet().toArray();
		String randomKey = (String) Values[generator.nextInt(Values.length)];
		String randomValue = attributes.get(randomKey);
		
		return new Guess(Guess.GuessType.Attribute,randomKey,randomValue);
	}// end of randomAttributandValue()

}// end of playerObject class
 
