/**
 * 
 */
package UseCaseController;

import DomainController.GameDomainController;
import DomainController.RankingDomainController;
import Enum.KeyPeg;

/**
 * This class is who manage play game use case and extends GenericPlayGamuUCC
 * Its function is to transmit data related with the game between Presentation
 * and Domain layer
 * @author Oriol Bellet
 */
public class PlayGameUseCaseController extends GenericPlayGameUCC {

    /**
     * PlayGameUseCaseController creator
     */
    public PlayGameUseCaseController () {
        gdc = GameDomainController.getInstance();
        rdc = RankingDomainController.getInstance();
    }

   /**
    * This method checks if the current round is finished. A round is finished when the codebreakers
    * successes the pattern o when the maximum number of attempts are done.
    * @return true if the round is finished, 0 otherwise
    */
    public int isRoundFinished() {
        return ((GameDomainController)gdc).isRoundFinished();
    }

    /**
    * This method updates the round's pattern inserted by the player in the domain
    * @param pattern Round's pattern
    * @return 0 if ok, -1 if error
    */
    public int setPattern(String pattern) {
        return ((GameDomainController)gdc).setPattern(pattern);
    }

    /**
     * This method notices Domain to ganerate a random pattern
     */
    public void generatePattern() {
       ((GameDomainController)gdc).generatePattern();
    }

    /**
    * This method indicates if in the current round the codemaker is human
    * @return 1 if codemaker if human, 0 if codemaker is cpu
    */
    public Boolean isCodemakerHuman() {
        return ((GameDomainController)gdc).isCodemakerHuman();
    }

    /**
     * This method indicates if in the current round the codebreaker is human
     * @return 1 if codebreaker if human, 0 if codebreaker is cpu
     */
    public Boolean isCodebreakerHuman() {
        return ((GameDomainController)gdc).isCodebreakerHuman();
    }

    /**
    * This method is used by the cpu to guess the pattern. Is the algorithm for cpu to success the game
    */
    public void cpuAttempt() {
        ((GameDomainController)gdc).cpuAttempt();
    }

    /**
    * This method returns an array of codePegs for the indicated row of the codePegs table
    * @param row Row to check
    * @return Array of codePegs for the indicated row
    */
    public Integer[] getCodePegRow(int row) {
        return ((GameDomainController)gdc).getCodePegsRow(row);
    }

    /**
    * This method returns an array of keyPegs for the indicated row of the keyPegs table
    * @param row Row to check
    * @return Array of keyPegs for the indicated row. In the first element of the array indicates number of white pegs
    * and in the second one indicates number of red pegs in the row
    */
    public KeyPeg[] getKeyPegsRow(int row) {
        return ((GameDomainController)gdc).getKeyPegsRow(row);
    }

    /**
    * This method obtains the color of the elements of the pattern
    * @return Color of the elements of the pattern
    */
    public Integer[] getPatternColor() {
        return ((GameDomainController)gdc).getPatternColor();
    }

    /**
    * This method updates the score of the codemaker adding to his score the points of the current row
    */
    public void setCodemakerPoints() {
        ((GameDomainController)gdc).setCodemakerPoints();
    }

    /**
     * Gets the length of the pattern
     * @return Pattern's length
     */
    public int getPatternLength() {
        return ((GameDomainController)gdc).getPatternLength();
    }

    /**
     * Gets which round of the game is being played
     * @return Number of round of the game that is being played
     */
    public int getCurrentRound() {
       return ((GameDomainController)gdc).getCurrentRound();
    }

    /**
     * gets the number of first row of codePegs board with no pegs
     * @return Number of the first row of codepegs with no pegs
     */
    public int getCurrentRow() {
        return ((GameDomainController)gdc).getCurrentRow();
    }

    /**
     * Gets the last set of pegs inserted in codePegs board and generates the
     * corresponding keyPegs
     */
    public void generateKeyPegs() {
        ((GameDomainController)gdc).generateKeyPegs();
    }

    /**
     * Gets the number of the last row of codePegs board with pegs
     * @return The number of the last row of codePegs board with pegs
     */
    public int getCodePegsLastRowNumber() {
        return ((GameDomainController)gdc).getCodePegsLastRowNumber();
    }

    /**
     * Updates the game state. Increments the number of current round,
     * calculates the scores of the round and resets the codePegs board
     */
    public void closeRound() {
        ((GameDomainController)gdc).closeRound();
    }

    /**
     * gets if the current game is being played against cpu or against human
     * @return false if the game is being played against human, true if is being
     * played against cpu
     */
    public Boolean getVsCpu() {
        return ((GameDomainController)gdc).getVsCpu();
    }

    /**
     * gets the total score of the indicated player
     * @param player Identifier of the player
     * @return total score of the indicated player
     */
    public int getScore(int player) {
        return ((GameDomainController)gdc).getScore(player);
    }

    /**
     * gets the keyPegs board of the current game
     * @return KeyPegs board
     */
    public KeyPeg[][] getKeyPegs() {
        return ((GameDomainController)gdc).getKeyPegs();
    }

    /**
     * gets which elements of the pattern are visibles for the codemaker.
     * The visible elements are a hint given
     * @return for each element of the array, false means invisible and true
     * means visible
     */
    public Boolean[] getPatternVisibility() {
        return ((GameDomainController)gdc).getPatternVisibility();
    }

    /**
     * Gets the number of rows of the codePegs and keyPegs board
     * @return Number of rows of the codePegs and keyPegs board
     */
    public int getRows() {
        return ((GameDomainController)gdc).getRows();
    }

    /**
     * Gets the length of the codePegs board, the keyPegs board and the pattern
     * @return Length (number of columns) of the codePegs board, the keyPegs
     * board and the pattern
     */
    public int getColumns() {
        return ((GameDomainController)gdc).getColumns();
    }

    /**
     * Gets if a hint was given for the indicated player
     * @param player Player identifier (1 or 2)
     * @return true if a hint was given, false if a hint wasn't given yet
     */
    public Boolean hintWasGiven(int player) {
        return ((GameDomainController)gdc).hintWasGiven(player);
    }

    /**
     * gets if the game allows codePegs duplicates in the pattern and in each
     * guess
     * @return true if codePegs duplicates are allowed, false if codePegs
     * duplicates are not allowed
     */
    public Boolean areDuplicatesAllowed() {
        return ((GameDomainController)gdc).areDuplicatesAllowed();
    }
}