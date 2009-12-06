
package Domain;

import Enum.DifficultyLevel;

/**
 * This class will have attributes that define whole problem elements and characteristics
 * 
 * vsCPU: Player 1 is human, player 2 is CPU
 * !vsCPU: Player 1 is human, player 2 is human
 *
 * round 0: Player 1 is codemaker, player 2 is codebreaker
 * round 1: Player 2 is codemaker, player 1 is codebreaker
 * round 2: Player 1 is codemaker, player 2 is codebreaker
 * round 3: ...
 *
 * @author Samuel
 */
public class Game extends GenericGame {

    private static final Integer NROWS = 10;

    private static final Integer NCOLS_EASY = 4;
    private static final Integer NCOLS_MEDIUM = 5;
    private static final Integer NCOLS_HARD = 6;

    private static final Integer NCOLORS = 6;

    private static final Integer POINTS_PER_GUESS_EASY = 20;
    private static final Integer POINTS_PER_GUESS_MEDIUM = 15;
    private static final Integer POINTS_PER_GUESS_HARD = 10;
    private static final Integer POINTS_EXTRA = 10;

    private static final Boolean ALLOW_DUPLICATES_EASY = false;
    private static final Boolean ALLOW_DUPLICATES_MEDIUM = false;
    private static final Boolean ALLOW_DUPLICATES_HARD = true;

    private static final Integer NROUNDS = 6;

    private Integer currentRound; // 1..NROUNDS
    private Integer P1Points;
    private Integer P2Points;
    private Boolean HintWasGiven;

    /**
     * Called during load game use case
     */
    public Game()
    {
        set = new Settings();
        board = new Board();

        currentRound = new Integer(0);
        P1Points = new Integer(0);
        P2Points = new Integer(0);
        HintWasGiven = new Boolean(false);
    }

    /**
     * Called during new game use case
     */
    public Game(Settings _set)
    {
        set = new Settings(_set);

        Integer ncols;

        switch (set.getLevel()) {
            case Easy: ncols = NCOLS_EASY; break;
            case Normal: ncols = NCOLS_MEDIUM; break;
            case Hard: ncols = NCOLS_HARD; break;
            default: ncols = NCOLS_EASY; break;
        }

        board = new Board(NROWS, ncols);

        currentRound = new Integer(0);
        P1Points = new Integer(0);
        P2Points = new Integer(0);
        HintWasGiven = new Boolean(false);
    }

    /**
     * Copy game constructor.
     *
     * @param g Game to be copied.
     */
    public Game(Game g){
        //TODO
    }

    /**
     * This constructor creates a game throuh given settings and board.
     *
     * @param b GenericBoard to be copied to this.board.
     * @param s Settings to be copied to this.set.
     */
    public Game(GenericBoard b, Settings s){
//        board = new GenericBoard(b);
//        set = new Settings(s);
    }



    @Override
    public void setSettings(GenericSettings s){

        DifficultyLevel level = s.getLevel();

        Integer ncols;

        switch (level) {
            case Easy: ncols = NCOLS_EASY; break;
            case Normal: ncols = NCOLS_MEDIUM; break;
            case Hard: ncols = NCOLS_HARD; break;
            default: ncols = NCOLS_EASY; break;
        }

        board = new Board(NROWS, ncols);
    }

    public void setSettings(DifficultyLevel _level, Boolean _vsCPU) {
        set = new Settings(_level, _vsCPU);

        Integer ncols;

        switch (_level) {
            case Easy: ncols = NCOLS_EASY; break;
            case Normal: ncols = NCOLS_MEDIUM; break;
            case Hard: ncols = NCOLS_HARD; break;
            default: ncols = NCOLS_EASY; break;
        }

        board = new Board(NROWS, ncols);

    }



    @Override
    public int getColumns()
    {
        int res;

        switch(set.getLevel()) {
            case Easy: res = NCOLS_EASY.intValue(); break;
            case Normal: res = NCOLS_MEDIUM.intValue(); break;
            case Hard: res = NCOLS_HARD.intValue(); break;
            default: res = 0; break;
        }

        return res;
    }

    @Override
    public int getRows()
    {
        return NROWS.intValue();
    }
    

    public int getLevelPointsPerGuess(DifficultyLevel level)
    {
        Integer res;

        switch(level) {
            case Easy: res = new Integer(POINTS_PER_GUESS_EASY); break;
            case Normal: res = new Integer(POINTS_PER_GUESS_MEDIUM); break;
            case Hard: res = new Integer(POINTS_PER_GUESS_HARD); break;
            default: res = new Integer(-1);
        }

        return res.intValue();
    }



    public Boolean getLevelDuplicatesAllowed(DifficultyLevel level)
    {
        Boolean res;

        switch(level) {
            case Easy: res = new Boolean(ALLOW_DUPLICATES_EASY); break;
            case Normal: res = new Boolean(ALLOW_DUPLICATES_MEDIUM); break;
            case Hard: res = new Boolean(ALLOW_DUPLICATES_HARD); break;
            default: res = new Boolean(false);
        }

        return res;
    }



    public Integer getNColors()
    {
        return NCOLORS;
    }



    /*
     * Rounds
     */

    public Integer getTotalRounds()
    {
        return NROUNDS;
    }

    public Integer getCurrentRound()
    {
        return currentRound;
    }

    public void setCurrentRound(Integer round)
    {
        currentRound = round;
    }

    public void increaseRound()
    {
        currentRound++;
    }



    /*
     * Gateway to Settings
     */

    public Boolean getVsCPU()
    {
        return ((Settings)set).getVsCPU();
    }

    public void setVsCPU(Boolean _vsCPU)
    {
        ((Settings)set).setVsCPU(_vsCPU);
    }



    /**
     * vs CPU, first codemaker is Human
     * @return
     */
    public Boolean isCodemakerHuman()
    {
        return !((Settings)set).getVsCPU() || (currentRound % 2 == 1);
    }

    public Boolean isCodebreakerHuman()
    {
        return !((Settings)set).getVsCPU() || !isCodemakerHuman();
    }



    /*
     * player points
     */

    public Integer getP1Points()
    {
        return P1Points;
    }

    public void setP1Points(Integer points)
    {
        P1Points = points;
    }

    public void addP1Points(Integer additionalpoints)
    {
        P1Points += additionalpoints;
    }

    public Integer getP2Points()
    {
        return P2Points;
    }

    public void setP2Points(Integer points)
    {
        P2Points = points;
    }

    public void addP2Points(Integer additionalpoints)
    {
        P2Points += additionalpoints;
    }



    /*
     * Hint
     */

    public Boolean getHintWasGiven()
    {
        return HintWasGiven;
    }

    /**
     * pre: no previous hint was given.
     *
     * @return the position of the peg of the pattern revealed to the user
     * (between 0 and the number of columns - 1)
     */
    public int giveHint()
    {
        Boolean[] visib = ((Board)board).getPatternVisibility();
        int hint = randomFromXtoY(1, board.getColumns());
        // board.columns is a protected from GenericBoard. it should not be allowed but netbeans allows it

        visib[hint] = true;
        ((Board)board).setPatternVisibility(visib);

        HintWasGiven = true;

        return hint;
    }



    /**
     * @return Number of player who is codemaker
     */

    public Integer getCodeMaker()
    {
        if (currentRound % 2 == 0) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * @return Number of player who is codebreaker
     */

    public Integer getCodeBreaker()
    {
        if (getCodeMaker() == 1) {
            return 2;
        } else {
            return 1;
        }
    }



    /*
     *
     * Gateway to Board, to isolate GameDC from Board
     *
     */

    @Override
    public void setBoard(final Integer[][] board)
    {
        setCodePegs(board);
    }

    public void resetBoard()
    {
        ((Board)board).resetBoard();
    }

    @Override
    public Integer[][] getBoard()
    {
        return getCodePegs();
    }

    private Integer[][] getCodePegs()
    {
        return ((Board)board).getCodePegs();
    }

    public Integer[] getCodePegsRow(int row)
    {
        return ((Board)board).getCodePegsRow(row);
    }

    public Integer[] getCodePegsLastRow()
    {
        return ((Board)board).getCodePegsLastRow();
    }

    public Integer getCodePegsLastRowNumber()
    {
        return ((Board)board).getCodePegsLastRowNumber();
    }

    public Integer getCurrentRow()
    {
        return ((Board)board).getCurrentRow();
    }

    private void setCodePegs(final Integer[][] _codePegs)
    {
        ((Board)board).setCodePegs(_codePegs);
    }

    public Integer[][] getKeyPegs()
    {
        return ((Board)board).getKeyPegs();
    }

    public Integer[] getKeyPegsRow(int row)
    {
        return ((Board)board).getKeyPegsRow(row);
    }
    
    public void setKeyPegs(final Integer[][] _keyPegs)
    {
        ((Board)board).setKeyPegs(_keyPegs);
    }

    public void setKeyPeg(final Integer _keyPeg, final int row, final int column)
    {
        ((Board)board).setKeyPeg(_keyPeg, row, column);
    }

    public void setPatternColor(Integer[] colors)
    {
        ((Board)board).setPatternColor(colors);
    }

    public Integer[] getPatternColor()
    {
        return ((Board)board).getPatternColor();
    }

    public void setPatternVisibility(Boolean[] visibility)
    {
        ((Board)board).setPatternVisibility(visibility);
    }

    public Boolean[] getPatternVisibility()
    {
        return ((Board)board).getPatternVisibility();
    }






    private int randomFromXtoY(final int Lower_Bound, final int Upper_Bound)
    {
        return (int) (Lower_Bound + Math.random() * ( Upper_Bound - Lower_Bound) + 0.5) ;
    }



}
