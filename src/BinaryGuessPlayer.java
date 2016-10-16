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
    private int numAttrs;
    private PlayerObject playerSelect;
    private List<PlayerObject> playerList;
    private String[][] attributeList;
    private String[] guessAttributes;
	
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
    public BinaryGuessPlayer(String gameFilename, String chosenName)
        throws IOException
    {
    	
    	FileInputStream fin = new FileInputStream(gameFilename);
        Scanner sc = new Scanner(fin);
        
        List<String[]> attrs = new ArrayList<String[]>();
        
        while (true) {
            String line = sc.nextLine();
            if (line.equals("")) break;
            attrs.add(line.split(" "));
        }
        
        this.attributeList = attrs.toArray(new String[0][]);
        
        numAttrs = this.attributeList.length;
        
        playerList = new ArrayList<PlayerObject>();
        while (sc.hasNext()) {
            String name = sc.nextLine();
            PlayerObject person = new PlayerObject(name);
            while (sc.hasNext()) {
                String line = sc.nextLine();
                if (line.equals("")) break;
                String[] tokens = line.split(" ");
                person.addattributes(tokens[0], tokens[1]);
            }
            playerList.add(person);
            
            // select me
            if (name.equals(chosenName)) playerSelect = person;
        }
        
        sc.close();
        
        // init guessed information
        guessAttributes = new String[numAttrs];
        for (int i = 0; i < numAttrs; i++) 
        	guessAttributes[i] = null;

    } // end of BinaryGuessPlayer()



    public Guess guess() {
        // placeholder, replace
    	if (playerList.size() == 1) {
    		PlayerObject person = playerList.get(0);
            return new Guess(Guess.GuessType.Person, "", person.getName());
        }
        
        int bestDiff = playerList.size() + 1;
        int bestAttrID = 0, bestValueID = 1;
        
        for (int i = 0; i < numAttrs; i++) {
            if (guessAttributes[i] != null) continue; // already guessed this attribute
            for (int j = 1; j < attributeList[i].length; j++) {
                String attr = attributeList[i][0];
                String val = attributeList[i][j];
                int count = 0; // number of candidates satisfying condition
                for (int k = 0; k < playerList.size(); k++) {
                	PlayerObject person = playerList.get(k);
                    if (person.equalsAttribute(attr,val)) {
                        count = count + 1;
                    }
                }
                
                int diff = Math.abs(count - (playerList.size() - count));
                
                if (diff < bestDiff) {
                    // update best
                    bestDiff = diff;
                    bestAttrID = i;
                    bestValueID = j;
                }
            }
        }
        
        return new Guess(Guess.GuessType.Attribute, attributeList[bestAttrID][0], attributeList[bestAttrID][bestValueID]);
    } // end of guess()


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
            String attr = currGuess.getAttribute();
            String val = currGuess.getValue();
            List<PlayerObject> newCandidates = new ArrayList<PlayerObject>();
            
            if (answer) {
                // extract persons with correct attribute value
                for (int i = 0; i < playerList.size(); i++) {
                	PlayerObject person = playerList.get(i);
                    if (person.equalsAttribute(attr,val)) newCandidates.add(person);
                }
                
                // this attribute is successfully guessed
                for (int i = 0; i < attributeList.length; i++)
                    if (attributeList[i][0].equals(attr)) guessAttributes[i] = val;
            } else {
                // extract persons with correct attribute value
                for (int i = 0; i < playerList.size(); i++) {
                	PlayerObject person = playerList.get(i);
                    if (!person.equalsAttribute(attr,val)) newCandidates.add(person);
                }
            }
            
            playerList = newCandidates;
        }
        
        return false;
    } // end of receiveAnswer()

} // end of class BinaryGuessPlayer
