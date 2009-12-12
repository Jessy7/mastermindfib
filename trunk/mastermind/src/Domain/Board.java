
package Domain;

/**
 * Board class has attributes specific of Mastermind
 *
 * @author Samuel Gomez
 */
public class Board extends GenericBoard {

    /**
     * A red keypeg indicates that a codepeg matches on both color and position
     */
    public static final Integer KEYPEG_RED = 2;

    /**
     * A white keypeg indicates that a codepeg matches on color, but not on position
     */
    public static final Integer KEYPEG_WHITE = 1;

    /**
     * A void keypeg indicates that a codepeg does not match on color, thus, neither in position
     */
    public static final Integer KEYPEG_VOID = 0;

    private Integer[][] codePegs;

    /**
     * Keypegs: There is a set of keypegs per row. They indicate how many codepegs
     * do match () with the pattern.
     *
     * number of rows: rows
     * number of columns: 2
     * - Column 1: Number of red keypegs
     * - Column 2: Number of white keypegs
     */
    private Integer[][] keyPegs;
    private PatternPeg[] pattern;


    public Board()
    {
        
    }

    /**
     *  Creates a new board for the game
     */
    public Board(final int newRows, final int newColumns)
    {
        super(newRows, newColumns);

        codePegs = new Integer[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                codePegs[i][j] = new Integer(0);
            }
        }

        keyPegs = new Integer[rows][2];
        for (int i = 0; i < rows; i++) {
            keyPegs[i][0] = new Integer(0);
            keyPegs[i][1] = new Integer(0);
        }

        pattern = new PatternPeg[columns];
        for (int j = 0; j < columns; j++) {
            pattern[j] = new PatternPeg(0, true);
        }
    }





    /*
     *
     * CodePegs
     *
     */

    /*
     * getters
     */

    public Integer[][] getBoard()
    {
        return getCodePegs();
    }
    
    public Integer[][] getCodePegs()
    {
        return codePegs;
    }

    public Integer[] getCodePegsRow(int row)
    {
        return codePegs[row];
    }

    public Integer[] getCodePegsLastRow()
    {
        return codePegs[getCodePegsLastRowNumber().intValue()];
    }

    /**
     * @return Ordinal of the board's last played row. From 0 to N - 1.
     */
    public Integer getCodePegsLastRowNumber()
    {
        int i = 0;
        int lastrow = 0;

        while (i < rows && codePegs[i][0] > 0) {
            i++;
        }

        lastrow = i - 1;
        return lastrow;
    }
    
    /**
     * @return Ordinal of the board's current row. From 0 to N - 1.
     */
    public Integer getCurrentRow()
    {
        return getCodePegsLastRowNumber() + 1;
    }

    public Integer getCell(final int row, final int column)
    {
        return getCodePeg(row, column);
    }

    public Integer getCodePeg(final int row, final int column)
    {
        if (row >= rows) return -1;
        if (column >= columns) return -1;
        
        return codePegs[row][column];
    }



    /*
     * setters
     */

    public void setBoard(final Integer[][] _codePegs)
    {
        setCodePegs(_codePegs);
    }

    public void setCodePegs(final Integer[][] _codePegs)
    {
        codePegs = new Integer[_codePegs.length][_codePegs[0].length];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++) {
                codePegs[i][j] = new Integer(_codePegs[i][j]);
            }
        }
    }

    public Boolean setCell(final Integer _codePeg, final int row, final int column)
    {
        return setCodePeg(_codePeg, row, column);
    }

    public Boolean setCodePeg(final Integer _codePeg, final int row, final int column)
    {
        if (row >= rows) return false;
        if (column >= columns) return false;

        codePegs[row][column] = new Integer(_codePeg);
        return true;
    }

    /**
     *
     * @pre board is already initialized
     */
    public void resetBoard()
    {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                codePegs[i][j] = 0;
            }
            keyPegs[i][0] = 0;
            keyPegs[i][1] = 0;
        }

        for (int j = 0; j < columns; j++) {
            pattern[j].setColor(0);
            pattern[j].setVisible(false);
        }
    }


    


    /*
     *
     * KeyPegs
     *
     */

    /*
     * getters
     */

    public Integer[][] getKeyPegs()
    {
        Integer[][] res = new Integer[rows][columns];

        for (int i = 0; i < rows; i++) {
            res[i] = getKeyPegsRow(i);
        }

        return res;
    }

    /**
     * Returns the row of key pegs for a given row. Red keypegs first, white
     * keypegs after, "void" keypegs finally.
     *
     * @return
     * eg: if keypeg[row] contains:
     * - Column 1: 2 (red keypegs)
     * - Column 2: 1 (white keypegs)
     *
     * this function will return [RED][RED][WHITE][VOID]
     */
    public Integer[] getKeyPegsRow(int row)
    {
        Integer[] res = new Integer[columns];
        int j = 0;

        // red keypegs
        for (int i = 0; i < keyPegs[row][0]; i++) {
            res[j] = new Integer(KEYPEG_RED);
            j++;
        }
        // now res contains [RED][RED][][]

        // white keypegs
        for (int i = 0; i < keyPegs[row][1]; i++) {
            res[j] = new Integer(KEYPEG_WHITE);
            j++;
        }
        // now res contains [RED][RED][WHITE][]

        // void keypegs
        for (; j < columns; j++) {
            res[j] = new Integer(KEYPEG_VOID);
        }
        // now res contains [RED][RED][WHITE][VOID]

        return res;
    }

    /**
     * Sets keypegs without ordering them.
     */
    public void setKeyPegs(final Integer[][] _keyPegs)
    {
        int nred;
        int nwhite;

        keyPegs = new Integer[_keyPegs.length][_keyPegs[0].length];

        for (int i = 0; i < rows; i++) {
            nred = 0;
            nwhite = 0;
            for (int j = 0; j < columns; j++) {
                if (_keyPegs[i][j] == KEYPEG_RED) {
                    nred++;
                } else if (_keyPegs[i][j] == KEYPEG_WHITE) {
                    nwhite++;
                }
            }
            keyPegs[i][0] = new Integer(nred);
            keyPegs[i][1] = new Integer(nwhite);
        }
    }

    /**
     * Places a new keypeg on the board and orders it in the context of its row
     *
     * @param _keyPeg color of the keypeg 
     * @param row Row of the keypeg
     */
    public void addKeyPeg(final Integer _keyPeg, final int row)
    {
        if (_keyPeg == KEYPEG_RED) {
            keyPegs[row][0]++;
        } else if (_keyPeg == KEYPEG_WHITE) {
            keyPegs[row][1]++;
        }
    }

    /**
     * Places a new keypeg on the board withour ordering it
     *
     * @param _keyPeg color of the keypeg
     * @param row Row of the keypeg
     * @param column Column of the keypeg
     */
    public void setKeyPeg(final Integer _keyPeg, final int row, final int column)
    {
        keyPegs[row][column] = new Integer(_keyPeg);
    }



    /*
     *
     * Pattern
     *
     */

     public void setPatternColor(Integer[] colors)
     {
         for (int j = 0; j < columns; j++) {
            pattern[j].setColor(colors[j]);
         }
     }

     public Integer[] getPatternColor()
     {
         Integer[] colors = new Integer[columns];

         for (int j = 0; j < columns; j++) {
             colors[j] = new Integer(pattern[j].getColor());
         }

         return colors;
     }

     public void setPatternVisibility(Boolean[] visibility)
     {
         for (int j = 0; j < columns; j++) {
            pattern[j].setVisible(visibility[j]);
         }
     }

     public Boolean[] getPatternVisibility()
     {
         Boolean[] visibility = new Boolean[columns];

         for (int j = 0; j < columns; j++) {
             visibility[j] = new Boolean(pattern[j].getVisible());
         }

         return visibility;
     }

}
