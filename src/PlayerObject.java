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
	
	public String getName(){
		return name;
	}
	
	public void addattributes(String attributeName, String attributeValue){
		attributes.put(attributeName, attributeValue);
	}
	
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
	
	public ArrayList<String> getattributekeys(){
		ArrayList<String> temparray = new ArrayList<String>();
		for(Entry<String, String> entry : attributes.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
		    temparray.add(key);
		}
		return temparray;
	}
	
	public HashMap<String, String> getattributes(){
		return attributes;
	}
	
	
	
	public Guess randomAttributeandValue(){
		Random generator = new Random();
		Object[] Values = attributes.keySet().toArray();
		String randomKey = (String) Values[generator.nextInt(Values.length)];
		String randomValue = attributes.get(randomKey);
		
		return new Guess(Guess.GuessType.Attribute,randomKey,randomValue);
	}

}
