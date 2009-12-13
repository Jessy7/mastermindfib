
package Domain;

/**
 * Board class has specific attributes for Mastermind
 *
 * Publicly, it represents a real Mastermind board with:
 * - A matrix of N*M code pegs
 * - A matrix of N*M key pegs
 * - A row of M pattern pegs
 *
 * Accessing code pegs is done through these methods:
 * - setCodePeg*
 * - getCodePeg*
 *
 * Accessing key pegs is done through these methods:
 * - addKeyPeg
 * - getKeyPeg*
 *
 * Accessing pattern pegs is done through these methods:
 * - setPatternColor
 * - setPatternVisibility
 * - getPatternColor
 * - getPatternVisibility
 *
 * @author Samuel Gómez
 */
public class Board extends GenericBoard {

    /**
     * A red keypeg indicates that a codepeg matches on both color and position
     */
    public static final Integer KEYPEG_RED = 2;

    /**
     * A white keypeg indicates that a codepeg matches on color,
     * but not on position
     */
    public static final Integer KEYPEG_WHITE = 1;

    /**
     * A void keypeg indicates that a codepeg does not match on color,
     * thus, neither in position
     */
    public static final Integer KEYPEG_VOID = 0;

    private static final Integer COLOREDKP_COL = 0;
    private static final Integer WHITEKP_COL = 1;

    /**
     * Code Pegs
     *
     * number of rows: GenericBoard::rows
     * number of columns: GenericBoard::columns
     */
    private Integer[][] codePegs;

    /**
     * Key Pegs: There is a set of keypegs per row. They indicate how many
     * codepegs do match with the pattern.
     *
     * Number of rows: GenericBoard::rows
     * Number of columns: 2
     * - Column 1: Number of red keypegs
     * - Column 2: Number of white keypegs
     *
     * However, this is only the internal representation; on the API related
     * to key pegs, a matrix of GenericBoard::rows * GenericBoard::columns is
     * the model.
     *
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
            keyPegs[i][COLOREDKP_COL] = new Integer(0);
            keyPegs[i][WHITEKP_COL] = new Integer(0);
        }

        pattern = new PatternPeg[columns];
        for (int j = 0; j < columns; j++) {
            pattern[j] = new PatternPeg(0, true);
        }
    }





    /*
     *
     * Code pegs
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
            keyPegs[i][COLOREDKP_COL] = 0;
            keyPegs[i][WHITEKP_COL] = 0;
        }

        for (int j = 0; j < columns; j++) {
            pattern[j].setColor(0);
            pattern[j].setVisible(false);
        }
    }


    


    /*
     *
     * Key pegs
     *
     */

    /*
     * getters
     */

    /**
     * @return The matrix of key pegs of the board.
     * - Number of rows: GenericBoard::rows
     * - Number of rows: GenericBoard::columns
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
     * @param row Row of the board from 0 to GenericBoard::rows - 1
     * @return The array of key pegs for a given row of the board.
     *
     * Red keypegs first, white keypegs after, "void" keypegs finally.
     *
     * Examples:
     * 
     * If keyPegs[row] contains [2][1],
     * this function will return [RED][RED][WHITE][VOID]
     *
     * If keyPegs[row] contains [0][3],
     * this function will return [WHITE][WHITE][WHITE][VOID]
     *
     */
    public Integer[] getKeyPegsRow(int row)
    {
        Integer[] res = new Integer[columns];
        int j = 0;

        // red keypegs
        for (int i = 0; i < keyPegs[row][COLOREDKP_COL]; i++) {
            res[j] = new Integer(KEYPEG_RED);
            j++;
        }
        // now res contains [RED][RED][][]

        // white keypegs
        for (int i = 0; i < keyPegs[row][WHITEKP_COL]; i++) {
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
     * 
     * @param _keyPegs Key peg matrix of the Mastermind board. Allowed values:
     * KEYPEG_RED, KEYPEG_WHITE
     */
    public void setKeyPegs(final Integer[][] _keyPegs)
    {
        int nred;
        int nwhite;

        keyPegs = new Integer[_keyPegs.length][_keyPegs[0].length];

        // for each row
        for (int i = 0; i < rows; i++) {
            nred = 0;
            nwhite = 0;

            // for each column
            for (int j = 0; j < columns; j++) {
                if (_keyPegs[i][j].equals(KEYPEG_RED)) {
                    nred++;
                } else if (_keyPegs[i][j].equals(KEYPEG_WHITE)) {
                    nwhite++;
                }
            } // ffor column

            keyPegs[i][COLOREDKP_COL] = new Integer(nred);
            keyPegs[i][WHITEKP_COL] = new Integer(nwhite);

        } // ffor row

    }

    /**
     * Adds a new keypeg to the board
     *
     * @param _keyPeg color of the key peg
     * @param row Row of the key peg
     */
    public void addKeyPeg(final Integer _keyPeg, final int row)
    {
        if (_keyPeg.equals(KEYPEG_RED)) {
            keyPegs[row][COLOREDKP_COL]++;

        } else if (_keyPeg.equals(KEYPEG_WHITE)) {
            keyPegs[row][WHITEKP_COL]++;

        }
    }

    // http://java.sun.com/j2se/1.5.0/docs/guide/javadoc/deprecation/deprecation.html
    /**
     * Places a new keypeg on the board without ordering it
     *
     * @deprecated
     * <p>
     * Use {@link #addKeyPeg()} instead.
     * </p>
     * <p>
     * Currently, setKeyPeg couples the user with the internal representation
     * of the key pegs, which changed from the external one during development.
     * </p>
     * <p>
     * setKeyPeg could be re-implemented to recover its original meaning, but
     * probably this will not happen due to its very low value/cost ratio:
     * </p>
     * <ul>
     *  <li>
     *   "column" parameter is not trivial to compute in the context of key pegs
     *   computation; this makes this method hard to use.
     *  </li>
     *  <li>
     *   It is complex to implement.
     *  </li>
     *  <li>
     *   It is inefficient to run; the complex implementation does not simplify
     *   further calculations.
     *  </li>
     * </ul>
     * 
     * @param _keyPeg Color of the keypeg
     * @param row Row of the keypeg
     * @param column Column of the keypeg
     */
    @Deprecated
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
