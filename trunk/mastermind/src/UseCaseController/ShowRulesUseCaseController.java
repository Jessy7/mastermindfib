package UseCaseController;

/**
 * Class that is used to show the rules of the game in the interface.
 *
 * @author Oriol Bellet
 */
public class ShowRulesUseCaseController extends GenericShowRulesUCC {

    public ShowRulesUseCaseController () {
        Rules = "The game is played using: <br> * a decoding board, with a shield at one end covering a row of four large holes, and ten additional rows containing four large holes next to a set of four small holes; \n\r " +
                "* code pegs of six different colors, with round heads, which will be placed in the large holes on the board; and \n\r " +
                "* key pegs, black and white, smaller than the code pegs; they will be placed in the small holes on the board. \n\r " +
                "Six rounds will be played. On each round, one player becomes the codemaker, the other the codebreaker, alternatively. \n" +
                "Codemaker \n";
    }

/*Codebreaker

1
	The codemaker chooses a pattern of four code pegs. Duplicates are allowed, so the player could even choose four code pegs of the same color. The chosen pattern is placed in the four holes covered by the shield, visible to the codemaker but not to the codebreaker.

2



The codebreaker tries to guess the pattern, in both order and color, within ten turns. Each guess is made by placing a row of code pegs on the decoding board.

3


Once placed, the codemaker provides feedback by placing from zero to four key pegs in the small holes of the row with the guess.

    * A black key peg is placed for each code peg from the guess which is correct in both color and position.
    * A white peg indicates the existence of a correct color peg placed in the wrong position.

If there are duplicate colours in the guess, they cannot all be awarded a key peg unless they correspond to the same number of duplicate colours in the pattern.

For example,

    * if the pattern is green-green-red-red
    * and the player guesses green-green-green-red

the codemaker will award

    * two black pegs for the two correct greens,
    * nothing for the third green as there is not a third green in the code,
    * and a black peg for the red.

No indication is given of the fact that the pattern also includes a second red.

4



Once feedback is provided, another guess is made; guesses and feedback continue to alternate until either the codebreaker guesses correctly, or ten incorrect guesses are made.

Scoring

    *

      Depending on the difficulty level of the game, the codemaker earns a variable amount of points for each guess a codebreaker makes
          o

            Easy level: 20 points
          o

            Medium level: 15 points
          o

            Hard level: 10 points
    *

      The codemaker gets 10 extra points if the codebreaker doesn't guess the pattern exactly in the last guess.
    *

      The winner is the one who has the most points after all six rounds are played.";
    }
*/

    /**
     * Method that reads the rules stored in the class and returns the string.
     *
     * @return A string containing the rules of the game
     */
    @Override
    public String getRules(){
        return Rules;
    }

    @Override
    public void setRules(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

