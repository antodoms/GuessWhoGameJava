import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * Your customised guessing player.
 * This player is for bonus task.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class CustomGuessPlayer implements Player
{
	HashMap<String, PlayerObject> playerList;
	HashMap<String, AttributeObject> attributeList;
	ArrayList<String> remainingplayer;
	PlayerObject playerSelect,guessPlayer;
	Guess guessAttributes;
	
    /**
     * Loads the game configuration from gameFilename, and also store the chosen
     * person.
     *
     * @param gameFilename Filename of game configuration.
     * @param chosenName Name of the chosen person for this player.
     * @throws IOException If there are IO issues with loading of gameFilename.
     *    Note you can handle IOException within the constructor and remove
     *    the "throws IOException" method specification, but make sure your
     *    implementation exits gracefully if an IOException is thrown.
     */
    public CustomGuessPlayer(String gameFilename, String chosenName)
        throws IOException
    {	
    	playerList = new HashMap<String,PlayerObject>();
    	//attributeList = new HashMap<String, AttributeObject>();
    	remainingplayer = new ArrayList<String>();
    	try {
            // load chosen persons for both players
            BufferedReader assignedReader = new BufferedReader(new FileReader(gameFilename));
            String currentName = null;
            String line;
            while((line = assignedReader.readLine()) != null){
            	List<String> fields = new ArrayList<String>(Arrays.asList(line.trim().split(" ")));
            	
            	fields.removeAll(Arrays.asList("", null," "));
            	if(fields.size() == 2 && currentName!=null){
			  		if(playerList.containsKey(currentName)){
			  			PlayerObject tempplayer = playerList.get(currentName);
			  			tempplayer.addattributes(fields.get(0),fields.get(1));
			  		}
			  		
			  	}else if(fields.size() == 1){
			  		if(!playerList.containsKey(fields.get(0))){
			  			playerList.put(fields.get(0), new PlayerObject(fields.get(0)));
			  			currentName = fields.get(0);
			  			remainingplayer.add(fields.get(0));
			  		}
			  	}else{
			  		
			  	}
            }
            
            playerSelect = playerList.get(chosenName);
            
    	}catch(FileNotFoundException ex) {
			System.err.println("Missing file " + ex.getMessage() + ".");
		}
		catch(IOException ex) {
			System.err.println(ex.getMessage());	
		}

    } // end of CustomGuessPlayer()

    
    private void updateAttributeList(){
    	attributeList = new HashMap<String, AttributeObject>();
    	for(int i=0; i< remainingplayer.size();i++){
    		String playername = remainingplayer.get(i);
			HashMap<String,String> tempattributes = playerList.get(playername).getattributes();
			ArrayList<String> tempattributekeys = playerList.get(playername).getattributekeys();
    		for(int j=0; j< tempattributekeys.size(); j++){
    			if(attributeList.containsKey(tempattributekeys.get(j))){
      				attributeList.get(tempattributekeys.get(j)).addAttributeValues(tempattributes.get(tempattributekeys.get(j)));
      			}else{
      				attributeList.put(tempattributekeys.get(j), new AttributeObject(tempattributekeys.get(j)));
      			}
    		}
    		
    	}
    }

    public Guess guess() {

updateAttributeList();
    	
    	if (remainingplayer.size() == 1) {
            PlayerObject person = playerList.get(remainingplayer.get(0));
            return new Guess(Guess.GuessType.Person, "", person.getName());
        }
    	
    	int bestDiff = remainingplayer.size() + 1;
        String bestAttrID = playerList.get(remainingplayer.get(0)).getattributekeys().get(0);
        String bestValueID = playerList.get(remainingplayer.get(0)).getattributes().get(bestAttrID);
        
        BestAttribute temp= new BestAttribute(bestAttrID,bestValueID, bestDiff);
        
        for(Entry<String, AttributeObject> entry : attributeList.entrySet()) {
		    String key = entry.getKey();
		    AttributeObject value = entry.getValue();
		    temp = value.getBestAttribute();
		    int count = temp.count;
		    int diff = Math.abs(count - (remainingplayer.size() - count));
	                
		    if (diff <= bestDiff) {
                // update best
                bestDiff = diff;
                bestAttrID = temp.name;
                bestValueID = temp.value;
            }
		}
	
        return new Guess(Guess.GuessType.Attribute, bestAttrID, bestValueID);
    } // end of guess()


    public boolean answer(Guess currGuess) {

    	if(currGuess.getType().equals(Guess.GuessType.Attribute)){
    		if(playerSelect.equalsAttribute(currGuess.getAttribute(),currGuess.getValue())){
    			return true;
    		}
    	}else if(currGuess.getType().equals(Guess.GuessType.Person)){
    		if(playerSelect.getName().equals(currGuess.getValue())){
    			return true;
    		}
    	}
        // placeholder, replace
        return false;
    } // end of answer()


	public boolean receiveAnswer(Guess currGuess, boolean answer) {
		
		if(currGuess.getType().equals(Guess.GuessType.Attribute)){
			for(Entry<String, PlayerObject> entry : playerList.entrySet()) {
			    String playername = entry.getKey();
			    PlayerObject playerobject = entry.getValue();
	
			    if(answer==true && !playerobject.equalsAttribute(currGuess.getAttribute(),currGuess.getValue())){
			    	remainingplayer.remove(playername);
			    }else if(answer==false && playerobject.equalsAttribute(currGuess.getAttribute(),currGuess.getValue())){
			    	remainingplayer.remove(playername);
			    }
			    // do what you have to do here
			    // In your case, an other loop.
			}
			return false;
		}else if(currGuess.getType().equals(Guess.GuessType.Person) && answer==false){
			return false;
		}
        // placeholder, replace
        return true;
    } // end of receiveAnswer()

} // end of class CustomGuessPlayer
