
package DomainController;

import CPUAlgorithm.MastermindAI4Holes;
import CPUAlgorithm.MastermindAI5Holes;
import Conversors.IntegerArrayString;
import Domain.Game;
import java.util.Arrays;
import java.util.Random;
import Enum.DifficultyLevel;
import Enum.KeyPeg;
import Conversors.IntegerMatrixStringConverter;
import Conversors.StringBooleanArray;

/**
 * This singleton class extends GenericGameDC and it's the responsable to transmit data between
 * UseCaseController and DataLayer to DomainLayer
 * It is the unique who can acces to DomainLayer, but it's just coupling with Game classe.
 * It is also responsable to trasport Data between Domain and Data layer.
 * @author Oriol Bellet
 */
public class GameDomainController extends GenericGameDC {

   private static GameDomainController INSTANCE = null;
   private GameDomainController() {}

   /**
    * Obtains the instance of this singleton classe
    * @return the instance of the singleton
    */
   public static GameDomainController getInstance() {
       if (INSTANCE == null) {
           INSTANCE = new GameDomainController();
       }
       return INSTANCE;
   }

   /**
    * Creates a new instance of Game
    * @return 0 if oK, -1 if error
    */
   public int newGame() {
       g = new Game();
       return 0;
   }

   /**
    * This method returns an array of codePegs for the indicated row of the codePegs table
    * @param row Row to check
    * @return Array of codePegs for the indicated row
    */
   public Integer[] getCodePegsRow (int row) {
       return ((Game)g).getCodePegsRow(row);
   }

   /**
    * This method returns an array of keyPegs for the indicated row of the keyPegs table
    * @param row Row to check
    * @return Array of keyPegs for the indicated row. In the first element of the array indicates number of white pegs
    * and in the second one indicates number of red pegs in the row
    */
   public KeyPeg[] getKeyPegsRow (int row) {
       return ((Game)g).getKeyPegsRow(row);
   }

   /**
    * This method save the current Domain and calls data layer to save the Game
    * @param path System directory where the user wants to save the game
    * @param name File's name
    * @return 0 if ok, -1 if error
    */
   @Override
   public int saveGame(String path, String name) {
       String data = saveDomain();
       return super.saveGame(data, path, name);
   }

   /**
    * This method allows to user to have a hint. If is the first time that user ask for a hint, the hint is given
    * @return 0 if ok, -1 if the hint is already given.
    */
   @Override
   public int giveHint() {

       int codeBreakerPlayer = ((Game)g).getCodeBreaker();

       if (!((Game)g).getHintWasGiven(codeBreakerPlayer))
               return ((Game)g).giveHint();
       else
               return -1;
   }

   /**
    * This method checks if the finished row if the last one. If is the last one, the game is finished
    * @return true if the game if finished, false otherwise
    */
   @Override
   public Boolean isGameFinished() {
       return (((Game)g).getCurrentRound() == ((Game)g).getTotalRounds());
   }

   /**
    * This method checks if the current round is finished. A round is finished 
    * when the codebreakers successes the pattern o when the maximum number of
    * attempts are done.
    * @return 0 if the round is not finished, 1 if the round is finished and
    * codebreaker wins, 2 if the round is finished and codemaker wins
    */
   public int isRoundFinished() {
       
       if (((Game)g).getCurrentRow() == 0)
           return 0;

       Integer[] pattern = ((Game)g).getPatternColor();
       Integer[] solutionAttempt = ((Game)g).getCodePegsLastRow();

       if (Arrays.equals(pattern, solutionAttempt)) {
           return 1;
       }

       else if (((Game)g).getCurrentRow() == 10) {
           return 2;
       }

       return 0;
   }

   /**
    * This method updates the round's pattern inserted by the player in the domain
    * @param pattern Round's pattern
    * @return 0 if ok, -1 if error
    */
   public int setPattern(String pattern) {
       ((Game)g).setPatternColor(IntegerArrayString.toIntegerArray(pattern));
       return 0;
   }

   /**
    * This method generates a random pattern. It's necessary when the codemaker is CPU
    */
   public void generatePattern() {
       DifficultyLevel level= ((Game)g).getLevel();
       Integer[] pattern;

       if (level == DifficultyLevel.Hard)
            pattern = generatePatternWithDuplicates();
       else
            pattern = generatePatternWithoutDuplicates();

       ((Game)g).setPatternColor(pattern);
   }

   /**
    * This method generates a random pattern allowing duplicates
    * @return The generated pattern
    */
   private Integer[] generatePatternWithDuplicates() {
       int columns = ((Game)g).getColumns();
       int colors = ((Game)g).getNColors();
       Integer[] pattern = new Integer[columns];
       Random r  = new Random();

       for (int i = 0; i < columns; i++)
           pattern[i] = r.nextInt(colors) + 1;

       return pattern;
   }

   /**
    * This method generates a random pattern. Duplicates are not allowed
    * @return The generate pattern
    */
   private Integer[] generatePatternWithoutDuplicates() {
       int nColumns = ((Game)g).getColumns();
       int nColors = ((Game)g).getNColors();
       Integer[] pattern = new Integer[nColumns];
       Integer[] usedColor = new Integer[nColors];

       for (int i = 0; i < usedColor.length; i++) {
           usedColor[i] = new Integer(0);
       }

       Random r = new Random();
       int tryColor;

       int i = 0;
       while (i < pattern.length) {
           tryColor = r.nextInt(nColors);
           if (usedColor[tryColor] == 0) {
               usedColor[tryColor] = 1;
               pattern[i] = tryColor + 1;
               i++;
           }
       }

       return pattern;
   }

   /**
    * This method indicates if in the current row the codemaker is human
    * @return 1 if codemaker if human, 0 if codemaker is cpu
    */
   public Boolean isCodemakerHuman() {
       return ((Game)g).isCodemakerHuman();
   }

   /**
    * This method indicates if in the current row the codemaker is human
    * @return 1 if codemaker if human, 0 if codemaker is cpu
    */
   public Boolean isCodebreakerHuman() {
       return ((Game)g).isCodebreakerHuman();
   }

   /**
    * This method indicates to domain if the player2 is a human or a cpu
    * @param isPlayer2CPU Thi parameter indicates if player is a human
    */
   public void setPlayer2(Boolean isPlayer2CPU) {
       ((Game)g).setVsCPU(isPlayer2CPU);
   }

   /**
    * This method obtains all information needed to save the game from domain layer
    * @return The information needed to save the game
    */
   private String saveDomain() {
       String data = "";
       int rows = g.getRows();

       data += String.valueOf(((Game)g).getVsCPU()) + ",";
       data += String.valueOf(((Game)g).getLevel()) + ",";
       data += String.valueOf(((Game)g).getCurrentRound()) + ",";
       data += String.valueOf(((Game)g).getP1Points()) + ",";
       data += String.valueOf(((Game)g).getP2Points()) + ",";
       data += IntegerArrayString.toString(((Game)g).getPatternColor()) + ",";
       data += convertIntegerMatrixToString(g.getBoard()) + ",";
       data += IntegerMatrixStringConverter.toString(((Game)g).getKeyPegsAs2Cols(),rows,2) + ",";
       data += StringBooleanArray.toString(((Game)g).getPatternVisibility());

       return data;
   }

   /**
    * This method updates the domain with the data saved in a file.
    * This method is used when the user wants to load a game
    * @param data Data nedeed to load a prevoius saved game
    */
   @Override
   protected void loadDomain(String data) {

       String dataArray[] = splitAttributes(data);
       

       g = new Game();
       Boolean vsCpu = Boolean.valueOf(dataArray[0]);
       DifficultyLevel dl = DifficultyLevel.valueOf(dataArray[1]);
       ((Game)g).setSettings(dl, vsCpu);
       int rows = g.getRows();

       System.out.println("Current round: " + dataArray[2]);
       ((Game)g).setCurrentRound(Integer.valueOf(dataArray[2]));
       ((Game)g).setP1Points(Integer.valueOf(dataArray[3]));
       ((Game)g).setP2Points(Integer.valueOf(dataArray[4]));
       ((Game)g).setPatternColor(IntegerArrayString.toIntegerArray(dataArray[5]));
       ((Game)g).setBoard(convertStringToIntegerMatrix(dataArray[6]));

       ((Game)g).setKeyPegs(IntegerMatrixStringConverter.toIntegerMatrix(dataArray[7],rows,2));
       ((Game)g).setPatternVisibility(StringBooleanArray.toBooleanArray(dataArray[8]));
   }

   /**
    * This method gets the first element of the string. Each element is a field of the domain layer
    * @param data String with all data saved waiting for load
    * @return The field element of the string
    */
   private String[] splitAttributes (String data) {
       String[] dataArray = null;
       dataArray = data.split(",");
       return dataArray;
   }

 





   /**
    * This method obtains the color of the elements of the pattern
    * @return Color of the elements of the pattern
    */
   public Integer[] getPatternColor() {
       return ((Game)g).getPatternColor();
   }

   /**
    * This method updates the score of the codemaker adding to his score the points of the current row
    */
   public void setCodemakerPoints() {

      if (((Game)g).isCodemakerHuman()) {

        int levelPointsPerGuess = ((Game)g).getLevelPointsPerGuess(((Game)g).getLevel());

        int lastRow = ((Game)g).getCodePegsLastRowNumber();

        int player = ((Game)g).getCodeMaker();
        if (player == 1) {
          ((Game)g).addP1Points(levelPointsPerGuess * lastRow);
        }
        else {
          ((Game)g).addP2Points(levelPointsPerGuess * lastRow);
        }
      }
   }

   /**
    * This method is used by the cpu to guess the pattern. Is the algorithm for cpu to success the game
    */
   public void cpuAttempt() 
   {
       int currentRow = ((Game)g).getCurrentRow();
       int column = ((Game)g).getColumns();
       Integer[][] codePegs = g.getBoard();
       KeyPeg[][] keyPegs = ((Game)g).getKeyPegsAsBoard();
       Integer[] cpuGuess;
       DifficultyLevel dl = g.getLevel();
       
       if (dl == DifficultyLevel.Easy) {
            MastermindAI4Holes mh = new MastermindAI4Holes(codePegs,keyPegs);
            cpuGuess = mh.MakeGuess();
            for (int i = 0; i < column; i++) {
                ((Game)g).setCell(currentRow, i, cpuGuess[i]);
            }
       }
       else if (dl == DifficultyLevel.Normal) {
           MastermindAI5Holes mh = new MastermindAI5Holes(codePegs,keyPegs);
           cpuGuess = mh.MakeGuess();
           for (int i = 0; i < column; i++) {
                ((Game)g).setCell(currentRow, i, cpuGuess[i]);
           }
       }
   }


   /**
    *This method obtains the last row of the codePeg board full of codepegs
    * @return The last row of the codePeg board with codePegs
    */
   public int getCodePegsLastRowNumber() {
       return ((Game)g).getCodePegsLastRowNumber();
   }

   /**
    * This method obtains the score of the selected player
    * @param player Player to obtain the score
    * @return The score of the indicated player
    */
   public int getScore(int player) {
       int score;

       if (player == 1)
           score = ((Game)g).getP1Points();
       else
           score = ((Game)g).getP2Points();

       return score;

   }


   /**
    * This method generates the keyPegs row of the current row.
    * First gets the pattern and codemaker attempt. It checks them and generates the keypegs
    */
   public void generateKeyPegs() {
       int lastRow = ((Game)g).getCodePegsLastRowNumber();
       Integer[] patternColor = ((Game)g).getPatternColor();
       Integer[] guess = ((Game)g).getCodePegsLastRow();
       Boolean[] patternChecked = new Boolean[((Game)g).getColumns()];

       for (int i = 0; i < patternChecked.length; i++)
           patternChecked[i] = false;

       for (int i = 0; i < guess.length; i++) {
           if (guess[i].equals(patternColor[i])) {
               ((Game)g).addKeyPeg(KeyPeg.RED, lastRow);
               patternChecked[i] = true;
           }
       }

       for (int i = 0; i < guess.length; i++) {
           for (int j = 0; j < patternColor.length; j++) {
                if (guess[i].equals(patternColor[j]) && (patternChecked[j] == false)) {
                    ((Game)g).addKeyPeg(KeyPeg.WHITE, lastRow);
                    patternChecked[j] = true;
                }
           }
       }
   }

   public void setSettings(DifficultyLevel dl, Boolean isPlayer2CPU) {
       ((Game)g).setSettings(dl,isPlayer2CPU);
   }

   public void closeRound() {
       this.setCodemakerPoints();
       ((Game)g).increaseRound();
       ((Game)g).resetBoard();
       
   }

   public int getCurrentRound() {
       return ((Game)g).getCurrentRound();
   }

   public int getCurrentRow() {
       return ((Game)g).getCurrentRow();
   }

   public int getPatternLength()
   {
       return ((Game)g).getColumns();
   }

   public int getP1Points()
   {
       return ((Game)g).getP1Points();
   }

   public int getP2Points()
   {
       return ((Game)g).getP2Points();
   }

   public Boolean getVsCpu()
   {
       return ((Game)g).getVsCPU();
   }

    public KeyPeg[][] getKeyPegs()
    {
        return ((Game)g).getKeyPegsAsBoard();
    }

    public Boolean[] getPatternVisibility()
    {
        return ((Game)g).getPatternVisibility();
    }

    public int getColumns() {
        return ((Game)g).getColumns();
    }

    public int getRows() {
        return ((Game)g).getRows();
    }

    public Boolean hintWasGiven(int player) {
        return ((Game)g).getHintWasGiven(player);
    }

    public Boolean areDuplicatesAllowed() {
        return ((Game)g).areDuplicatesAllowed();
    }
}