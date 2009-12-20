package UseCaseController;

/**
 * Class that is used to show the rules of the game in the interface.
 *
 * @author Oriol Bellet
 */
public class ShowRulesUseCaseController extends GenericShowRulesUCC {

    public ShowRulesUseCaseController () {
        Rules = "The game is played using:\n" +
                "   * A decoding board, with a shield at one end covering a row of four large holes, and ten additional rows containing four large holes next to a set of four small holes;\n" +
                "   * Code pegs of six different colors, with round heads, which will be placed in the large holes on the board; and \n" +
                "   * key pegs, black and white, smaller than the code pegs; they will be placed in the small holes on the board. \n" +
                "\n" +
                "Six rounds will be played. On each round, one player becomes the codemaker, the other the codebreaker, alternatively. \n" +
                "1. CODEMAKER ACTION: The codemaker chooses a pattern. The number of code pegs and if the duplicates are allowed depends of the level, so the player could even choose four code pegs of the same color. The chosen pattern is placed in the four holes covered by the shield, visible to the codemaker but not to the codebreaker.\n" +
                "2. CODEBREAKER : The codebreaker tries to guess the pattern, in both order and color, within ten turns. Each guess is made by placing a row of code pegs on the decoding board.\n" +
                "3. CODEMAKER : Once placed, the codemaker provides feedback by placing from zero to four key pegs in the small holes of the row with the guess.\n" +
                "   * A black key peg is placed for each code peg from the guess which is correct in both color and position.\n" +
                "   * A white peg indicates the existence of a correct color peg placed in the wrong position.\n" +
                "  If there are duplicate colours in the guess, they cannot all be awarded a key peg unless they correspond to the same number of duplicate colours in the pattern.\n" +
                "  For example,\n" +
                "   * if the pattern is green-green-red-red\n" +
                "   * and the player guesses green-green-green-red\n" +
                "  The codemaker will award\n" +
                "   * two red pegs for the two correct greens,\n" + 
                "   * nothing for the third green as there is not a third green in the code,\n" +
                "   * and a red peg for the red." +
                "  No indication is given of the fact that the pattern also includes a second red.\n" +
                "4. CODEBREAKER : Once feedback is provided, another guess is made; guesses and feedback continue to alternate until either the codebreaker guesses correctly, or ten incorrect guesses are made.\n" +
                "\n" +
                "SCORING\n" +
                "Depending on the difficulty level of the game, the codemaker earns a variable amount of points for each guess a codebreaker makes\n" +
                " · Easy level: 20 points\n" +
                " · Medium level: 15 points\n" +
                " · Hard level: 10 points\n" +
                "The winner is the one who has the most points after all six rounds are played.\n";
    }


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
        throw new UnsupportedOperationException("Not necessary.");
    }
}

