/**
 * 
 */
package UseCaseController;

import DomainController.GameDomainController;
import DomainController.RankingDomainController;

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
    public Boolean isRoundFinished() {
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
    public Integer[] getCodePegRow(int row)
    {
        return ((GameDomainController)gdc).getCodePegRow(row);
    }

    /**
    * This method returns an array of keyPegs for the indicated row of the keyPegs table
    * @param row Row to check
    * @return Array of keyPegs for the indicated row. In the first element of the array indicates number of white pegs
    * and in the second one indicates number of red pegs in the row
    */
    public Integer[] getKeyPegRow(int row) {
        return ((GameDomainController)gdc).getKeyPegRow(row);
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
    public void setCodemakerPoints()
    {
        ((GameDomainController)gdc).setCodemakerPoints();
    }

    public int getPatternLength()
    {
        return ((GameDomainController)gdc).getPatternLength();
    }

    public int getCurrentRound()
    {
       return ((GameDomainController)gdc).getCurrentRound();
    }

    public int getCurrentRow()
    {
        return ((GameDomainController)gdc).getCurrentRow();
    }

    public void generateKeyPegs()
    {
        ((GameDomainController)gdc).generateKeyPegs();
    }

    public int getCodePegsLastRowNumber()
    {
        return ((GameDomainController)gdc).getCodePegsLastRowNumber();
    }

    public Integer[] getKeyPegsRow(int row)
    {
        return ((GameDomainController)gdc).getKeyPegRow(row);
    }

    public void closeRound()
    {
        ((GameDomainController)gdc).closeRound();
    }

    public Boolean getVsCpu()
    {
        return ((GameDomainController)gdc).getVsCpu();
    }

    public int getScore(int player)
    {
        return ((GameDomainController)gdc).getScore(player);
    }

    public Integer[][] getKeyPegs()
    {
        return ((GameDomainController)gdc).getKeyPegs();
    }

    public Boolean[] getPatternVisibility()
    {
        return ((GameDomainController)gdc).getPatternVisibility();
    }

    public int getRows() {
        return ((GameDomainController)gdc).getRows();
    }

    public int getColumns() {
        return ((GameDomainController)gdc).getColumns();
    }
}