import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import com.sun.javafx.collections.MappingChange.Map;

/**
 * Random guessing player.
 * This player is for task B.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class RandomGuessPlayer implements Player
{
	HashMap<String, PlayerObject> playerList;
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
    public RandomGuessPlayer(String gameFilename, String chosenName)
        throws IOException
    {
    	playerList = new HashMap<String,PlayerObject>();
    	remainingplayer = new ArrayList<String>();
    	try {
             //  Load up the chosen persons for both players
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
 
    } // end of RandomGuessPlayer()


    public Guess guess() {
    	
    	if(remainingplayer.size() >1){
    		Random generator = new Random();
    		guessPlayer = playerList.get(remainingplayer.get(generator.nextInt(remainingplayer.size())));
    		guessAttributes = guessPlayer.randomAttributeandValue();
    		
    	}else if (remainingplayer.size() == 1){
    		guessAttributes = new Guess(Guess.GuessType.Person, "", remainingplayer.get(0));
    	}
        // placeholder, replace
    	return guessAttributes;
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
	
			    if(answer && !playerobject.equalsAttribute(currGuess.getAttribute(),currGuess.getValue())){
			    	remainingplayer.remove(playername);
			    }else if(!answer && playerobject.equalsAttribute(currGuess.getAttribute(),currGuess.getValue())){
			    	remainingplayer.remove(playername);
			    }
			    // do what you have to do here
			    // In your case, an other loop.
			}
			return false;
		}else if(currGuess.getType().equals(Guess.GuessType.Person) && !answer){
			return false;
		}
        // placeholder, replace
        return true;
    } // end of receiveAnswer()

} // end of class RandomGuessPlayer
