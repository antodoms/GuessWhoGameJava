import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * Binary-search based guessing player.
 * This player is for task C.
 *
 * You may implement/extend other interfaces or classes, but ensure ultimately
 * that this class implements the Player interface (directly or indirectly).
 */
public class BinaryGuessPlayer implements Player
{
    private int totalNumberOfAttributes; //total number of attributes
    private PlayerObject playerSelect; // user selected player
    private List<PlayerObject> playerList; // list of all players in the board
    private String[][] attributeList;  // attribute list
    private String[] guessAttributes;    //guessed attribute
	
    /**
     * Loads the game configuration from gameFilename, and also store the chosen
     * playerNode.
     *
     * @param gameFilename Filename of game configuration.
     * @param chosenName Name of the chosen playerNode for this player.
     * @throws IOException If there are IO issues with loading of gameFilename.
     *    Note you can handle IOException within the constructor and remove
     *    the "throws IOException" method specification, but make sure your
     *    implementation exits gracefully if an IOException is thrown.
     */
    public BinaryGuessPlayer(String gameFilename, String chosenName)
        throws IOException
    {
    	try {
	    	BufferedReader assignedReader = new BufferedReader(new FileReader(gameFilename));
	        
	        List<String[]> attributes = new ArrayList<String[]>();
	        String line, playerName;
	        while (true) {
	            line = assignedReader.readLine();
	            if (line.equals("")) break;
	            attributes.add(line.split(" "));
	        }
	        
	        this.attributeList = attributes.toArray(new String[0][]);
	        
	        totalNumberOfAttributes = this.attributeList.length;
	        
	        playerList = new ArrayList<PlayerObject>();
	        while ((playerName = assignedReader.readLine()) != null) {
	            
	            PlayerObject playerNode = new PlayerObject(playerName);
	            while ((line = assignedReader.readLine()) != null) {
	                if (line.equals("")) break;
	                String[] fields = line.split(" ");
	                playerNode.addattributes(fields[0], fields[1]);
	            }
	            playerList.add(playerNode);
	            
	           // Select Me i.e. The player
	            if (playerName.equals(chosenName)) playerSelect = playerNode;
	        }
	        
	        
	       // This reflects the guessed information 
	        guessAttributes = new String[totalNumberOfAttributes];
	        for (int i = 0; i < totalNumberOfAttributes; i++) 
	        	guessAttributes[i] = null;
    	}catch(FileNotFoundException ex) {
			System.err.println("Missing file " + ex.getMessage() + ".");
		}
		catch(IOException ex) {
			System.err.println(ex.getMessage());	
		}

    } // end of BinaryGuessPlayer()



    public Guess guess() {
        // placeholder, replace
    	if (playerList.size() == 1) {
    		PlayerObject playerNode = playerList.get(0);
            return new Guess(Guess.GuessType.Person, "", playerNode.getName());
        }
        
        int bestDiff = Integer.MAX_VALUE;
        int bestAttribute = 0, bestValue = 1;
        
        for (int i = 0; i < totalNumberOfAttributes; i++) {
            if (guessAttributes[i] != null) continue; // Attribute that has been already guessed
            for (int j = 1; j < attributeList[i].length; j++) {
                String attribute = attributeList[i][0];
                String value = attributeList[i][j];
                int count = 0; // number of players i.e. satisfying the condition
                for (int k = 0; k < playerList.size(); k++) {
                	PlayerObject playerNode = playerList.get(k);
                    if (playerNode.equalsAttribute(attribute,value)) {
                        count = count + 1;
                    }
                }
                
                // To calculate the best attribute for dividing the playerList to half
                int diff = Math.abs(2*count - playerList.size());
                
                if (diff < bestDiff) {
                    // Update the Best information
                    bestDiff = diff;
                    bestAttribute = i;
                    bestValue = j;
                }
            }
        }
        
        return new Guess(Guess.GuessType.Attribute, attributeList[bestAttribute][0], attributeList[bestAttribute][bestValue]);
    } 
    // End of guess()


	public boolean answer(Guess currGuess) {
		 if (currGuess.getType() == Guess.GuessType.Person) {
	            return currGuess.getValue().equals(playerSelect.getName());
	     } 
	     
		 return playerSelect.equalsAttribute(currGuess.getAttribute(),currGuess.getValue());
    } // end of answer()


	public boolean receiveAnswer(Guess currGuess, boolean answer) {
        if (currGuess.getType() == Guess.GuessType.Person && answer) {
            return true;
        }
        
        if (currGuess.getType() == Guess.GuessType.Attribute) {
            String attribute = currGuess.getAttribute();
            String value = currGuess.getValue();
            List<PlayerObject> newplayerList = new ArrayList<PlayerObject>();
            
            if (answer) {
               // This extracts the playerNodes with correct attribute value
                for (int i = 0; i < playerList.size(); i++) {
                	PlayerObject playerNode = playerList.get(i);
                    if (playerNode.equalsAttribute(attribute,value)) newplayerList.add(playerNode);
                }
                
                // this attribute is successfully guessed
                for (int i = 0; i < attributeList.length; i++)
                    if (attributeList[i][0].equals(attribute)) guessAttributes[i] = value;
            } else {
                // extract playerNodes with correct attribute value
                for (int i = 0; i < playerList.size(); i++) {
                	PlayerObject playerNode = playerList.get(i);
                    if (!playerNode.equalsAttribute(attribute,value)) newplayerList.add(playerNode);
                }
            }
            
            playerList = newplayerList;
        }
        
        return false;
    } // end of receiveAnswer()

} // end of class BinaryGuessPlayer
