
package CPUAlgorithm;

import Enum.KeyPeg;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class offers a method to make a guess from mastermind board with
 * code pegs and key pegs
 *
 * This abstract class implements elements independent from the number of
 * holes and colors.
 *
 * @author Samuel Gomez
 */
public abstract class CommonMastermindAI
{
    /*
     * Sections:
     * - Constants
     * - Attributes
     * - Auxiliary static functions
     * - Constructor
     * - Public API
     * - Data analysis
     * - Knowledge synthesis
     */





    /*
     *
     * Constants
     *
     */
    // public static final Integer COLORED_KEYPEG = 2;
    // public static final Integer KeyPeg.WHITE = 1;
    protected static final Integer KEYPEG_NCOLS = 2;
    protected static final Integer COLORED_COLUMN = 0;
    protected static final Integer WHITE_COLUMN = 1;
    protected static final Boolean DEBUG = true;

    // dynamic here but static at subclass level
        /* although this implementation makes it to physically exist also dinamically
         * at subclass level
         */
    private Integer NHOLES;
    private Integer NCOLORS;





    /*
     *
     * Attributes
     *
     */
    /**
     * N rows, NHOLES columns.
     *
     * Values range from 0 to NCOLORS-1
     */
    protected Integer[][] codePegs;

    /**
     * X rows, KEYPEG_NCOLS columns.
     * - Column COLORED_COLUMN: For colored key pegs.
     *      Indicate some color and position match.
     * - Column WHITE_COLUMN: For white key pegs.
     *      Indicate some color but not position match.
     */
    protected Integer[][] keyPegs;

    protected PegsKnowledge knowledge;





    /*
     *
     * Auxiliary static data derivation functions
     *
     */


    /**
     *
     * @param oldRow old row
     * @param newRow new row
     * @param added ArrayList of added colors
     * @param removed ArrayList of removed colors
     */
    protected static void ColorDiff(final Integer[] oldRow, final Integer[] newRow,
            ArrayList added, ArrayList removed)
    {

        // which were added
        for (int i = 0; i < oldRow.length; i++) {
            if (!Contains(oldRow, newRow[i])) {
                added.add(newRow[i]);
            }
        }

        // which were removed
        for (int i = 0; i < oldRow.length; i++) {
            if (!Contains(newRow, oldRow[i])) {
                removed.add(oldRow[i]);
            }
        }

    }

    /**
     * @pre both rows have the same colors. without duplicates on the same row.
     * @param fromRow origin row of the transition
     * @param toRow destination row of the transition
     * @return ArrayList with sources position and destinations position
     * ordered by the position of the source
     */
    private void SwapDiff(final Integer[] fromRow, final Integer[] toRow,
            ArrayList sources, ArrayList destinations)
    {
        for (int j = 0; j < NHOLES.intValue(); j++) {

            // the peg on the source has changed its position
            if (!fromRow[j].equals(toRow[j])) {

                // actually is a new swap
                if (!destinations.contains(j)) {
                    sources.add(j);

                    // find the new position of th epeg
                    for (int k = 0; k < NHOLES.intValue(); k++) {
                        if (fromRow[j].equals(toRow[k])) {
                            destinations.add(k);
                        }
                    }
                }

            }
        }
    }

    protected static Boolean Contains(final Integer[] hayStack, final Integer needle)
    {
        for (int i = 0; i < hayStack.length; i++) {
            if (hayStack[i].equals(needle)) {
                return true;
            }
        }
        return false;
    }

    protected static Integer FindSwap(Integer pos1, Integer pos2, ArrayList origins,
            ArrayList destinations)
    {
        for (int i = 0; i < origins.size(); i++) {
            if (
                    // from pos1 to pos2
                    (origins.get(i).equals(pos1) && destinations.get(i).equals(pos2)) ||

                    // from pos2 to pos1
                    (origins.get(i).equals(pos2) && destinations.get(i).equals(pos1))
                    ) {
                return i;
            }
        }

        return -1;
    }

    // dynamic here but static on subclass
    /**
     * Which colors are not in the codePegsRow
     * @param codePegsRow
     * @return Array of Integers. One Integer for each color that is not in the
     *      codePegsRow
     */
    protected Integer[] WhichAreNot(Integer[] codePegsRow)
    {
        // which are
        Boolean[] WA_bool = new Boolean[NCOLORS];

        // which are not
        Integer[] WAN = new Integer[NCOLORS - NHOLES];

        int i = 0;

        /*
         * eg: pegs = 1624, colors = 123456
         */

        for (int j = 0; j < NCOLORS.intValue(); j++) {
            WA_bool[j] = new Boolean(false);
        }

        /*
         * WA_bool = [false][false][false][false][false][false]
         */

        for (int j = 0; j < NHOLES.intValue(); j++) {
            WA_bool[codePegsRow[j]] = new Boolean(true);
        }

        /*
         * WA_bool = [true][true][false][true][false][true]
         */

        for (int j = 0; j < NCOLORS.intValue(); j++) {
            if (WA_bool[j].equals(false)) {
                WAN[i] = new Integer(j);
                i++;
            }
        }

        /*
         * WAN = [3][5]
         */

        return WAN;
    }

    protected static Boolean IsRowInMatrix(Integer[] row, Integer[][] matrix)
    {
        Boolean res;

        // for each row
        for (int i = 0; i < matrix.length; i++) {

            // each row is a new oportunity
            res = true;

            // for each column
            for (int j = 0; j < row.length; j++) {
                res = res && row[j].equals(matrix[i][j]);
            }

            if (res) return res;
        }

        return false;
    }

    protected static ArrayList IntegerArrayToArrayList(Integer[] integerArray)
    {
        ArrayList arrayList = new ArrayList();

        for (int i = 0; i < integerArray.length; i++) {
            arrayList.add(integerArray[i]);
        }

        return arrayList;
    }

    protected static Integer[] ArrayListToIntegerArray(ArrayList arrayList)
    {
        Integer[] integerArray = new Integer[arrayList.size()];

        for (int i = 0; i < arrayList.size(); i++) {
            integerArray[i] = (Integer)arrayList.get(i);
        }

        return integerArray;
    }

    protected static Integer[] IntegerArraysIntersection(Integer[] a, Integer[] b)
    {
        ArrayList res = new ArrayList();

        for (int i = 0; i < a.length; i++) {
            if (Contains(b, a[i])) {
                res.add(a[i]);
            }
        }
        
        return ArrayListToIntegerArray(res);
    }

    protected static Boolean IntegerArrayFindDuplicate(Integer[] a)
    {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                if (i != j) {
                    if (a[i].equals(a[j])) {
                        return true;
                    }
                }
            }
        }

        return false;
    }





    private Boolean IsRelieve(final Integer[] fromRow, final Integer[] toRow)
    {
        ArrayList addedList = new ArrayList();
        ArrayList removedList = new ArrayList();
        ColorDiff(fromRow, toRow, addedList, removedList);

        return addedList.size() == 1 && removedList.size() == 1;
    }

    private Boolean IsSwap(final Integer[] fromRow, final Integer[] toRow)
    {
        ArrayList addedList = new ArrayList();
        ArrayList removedList = new ArrayList();
        ColorDiff(fromRow, toRow, addedList, removedList);

        return addedList.size() == 0 && removedList.size() == 0;
    }

    private Boolean IsReliveRemainingEqual(final Integer[] fromRow,
            final Integer[] toRow, Integer added, Integer removed)
    {
        Boolean res = true;

        // all the remaining must be in the same hole
        for (int j = 0; j < NHOLES.intValue(); j++) {
            if (fromRow[j].equals(removed)) {
                // don't check the hole of the relieved peg
            } else {
                if (!fromRow[j].equals(toRow[j])) {
                    res = false;
                }
            }
        }

        return res;
    }

    private Boolean IsReliveRemainingReorder(final Integer[] fromRow,
            final Integer[] toRow, Integer added, Integer removed)
    {
        Boolean res = false;

        // at least one of the remaining must be in a different hole
        for (int j = 0; j < NHOLES.intValue(); j++) {
            if (fromRow[j].equals(removed)) {
                // don't check this hole
            } else {
                if (!fromRow[j].equals(toRow[j])) {
                    res = true;
                }
            }
        }

        return res;
    }

    private Boolean IsReliveReorder(final Integer[] fromRow,
            final Integer[] toRow, Integer added, Integer removed)
    {
        return !PegPosInRow(fromRow, removed).equals(PegPosInRow(toRow, added));
    }

    private static String IntegerArrayToString(Integer[] x)
    {
        String res = new String();

        res += x[0];

        for (int i = 1; i < x.length; i++) {
            res += "," + x[i];
        }
        
        return res;
    }

    /**
     *
     * @param row Array of integers
     * @param Color
     * @return
     * If the Color is in the row
     *  position of the Color in a row
     * Else
     *  -1
     */
    private Integer PegPosInRow(Integer[] row, Integer Color)
    {
        for (int j = 0; j < NHOLES.intValue(); j++) {
            if (row[j].equals(Color)) return j;
        }

        return -1;
    }


    


    /*
     *
     * Constructor
     *
     */

    /**
     * 
     * @param _nHoles
     * @param _nColors
     * @param _codePegs Matrix of N*M cells representing code pegs. Each cell
     * saves the color of the code peg for that board hole. Peg colors range
     * from 1 to NCOLORS. 0 means "empty hole".
     * @param _keyPegs Matrix of N*M cells representing key pegs. Each cell
     * saves a colored key peg (KeyPeg.RED)
     * or a white key peg (KeyPeg.WHITE). 0 means "empty hole".
     */
    public CommonMastermindAI(Integer _nHoles, Integer _nColors,
            Integer[][] _codePegs, KeyPeg[][] _keyPegs)
    {
        NHOLES = _nHoles;
        NCOLORS = _nColors;

        // current row
        Integer currRow = new Integer(0);
        while (DecodePeg(_codePegs[currRow][0]) != null) {
            currRow++;
        }

        // code pegs
        codePegs = new Integer[_codePegs.length][NHOLES];
        for (int i = 0; i < currRow; i++) {
            for (int j = 0; j < NHOLES.intValue(); j++) {
                codePegs[i][j] = new Integer(DecodePeg(_codePegs[i][j]));
            }
        }

        // key pegs
        keyPegs = new Integer[_keyPegs.length][KEYPEG_NCOLS];
        for (int i = 0; i < currRow; i++) {
            
            // colored and white key peg auxiliar counters
            int ckp = 0, wkp = 0;

            for (int j = 0; j < NHOLES.intValue(); j++) {

                // colored key peg
                if (_keyPegs[i][j].equals(KeyPeg.RED)) {

                    ckp++;

                // white key peg
                } else if (_keyPegs[i][j].equals(KeyPeg.WHITE)) {

                    wkp++;

                }
            }
            
            keyPegs[i][COLORED_COLUMN] = new Integer(ckp);
            keyPegs[i][WHITE_COLUMN] = new Integer(wkp);
        }

        // PrintBoard(); // test

        // knowledge
        knowledge = new PegsKnowledge(NCOLORS, NHOLES);
    }

    private void PrintBoard()
    {

        System.out.println("PrintBoard:");

        // for each row
        for (int i = 0; i < codePegs.length; i++) {

            // code pegs
            for (int j = 0; j < NHOLES; j++) {
                System.out.print(EncodePeg(codePegs[i][j]) + ",");
            }

            // visual separator
            System.out.print("    ");

            // key pegs
            System.out.print(keyPegs[i][COLORED_COLUMN] + ",");
            System.out.print(keyPegs[i][WHITE_COLUMN] + ",");

            System.out.println();
        }

    }

    /**
     * @param peg
     * @return An inside-codified value for a code pegs board hole from an
     * outside-codified one.
     *
     * 0 -> null
     * 1..N --> 0..N-1
     */
    private Integer DecodePeg(Integer peg)
    {
        Integer res;

        // 0 -> null
        if (peg == null || peg.intValue() == 0) {
            res = null;

        // 1..N --> 0..N-1
        } else {
            res = new Integer(peg.intValue() - 1);

        }

        return res;
    }





    /*
     *
     * Public API
     *
     */

    /**
     * @pre code pegs of the last row of are not the solution
     * @param codePegs matrix of code pegs represented with integers from 1 to 6
     * @param keyPegs matrix of key pegs.
     * - Column 0 stores how many colored key pegs
     * - Column 1 stores how many white key pegs
     * @return Array containing a guess for 4 holes
     */
    public Integer[] MakeGuess()
    {
        /*
         * As it is read, the strategy for making a guess is:
         * First: Learn. Where data anayslis is performed.
         * Second: Get a successor. Where knowledge synthesis is performed.
         */

        PegsKnowledge oldK;

        do {
            oldK = new PegsKnowledge(knowledge);
            Learn();
        } while (!oldK.equals(knowledge));

        return EncodePegArray(getSuccessor());
    }

    private Integer[] EncodePegArray(Integer[] pegArray)
    {
        Integer[] res = new Integer[pegArray.length];

        for (int i = 0; i < pegArray.length; i++) {
            res[i] = new Integer(EncodePeg(pegArray[i]));
        }

        return res;
    }

    /**
     *
     * @param peg
     * @return if peg == null, return 0. if peg != null, return peg+1.
     */
    private Integer EncodePeg(Integer peg)
    {
        if (peg == null) {
            return new Integer(0);
        } else {
            return new Integer(peg.intValue() + 1);
        }
    }
    

    

    
    /*
     * 
     * Data analysis
     * 
     */

    private void Learn()
    {
        if (DEBUG) System.out.println("Learning...");

        int currRow;

        currRow = CurrentRow();

        // learn from rows
        for (int i = 0; i < currRow; i++) {
            LearnFromRow(i);
        }

        // learn from transitions
        for (int i = 0; i < currRow; i++) {
            for (int j = 0; j < currRow; j++) {
                if (i < j) {
                    LearnFromTransition(i, j);
                }
            }
        }

        // learn from double transitions
        for (int i = 0; i < currRow - 2; i++) {
            LearnFromDoubleTransition(i, i + 1, i + 2);
        }

        if (DEBUG) knowledge.print();

     }
    
    protected void LearnFromRow(Integer row)
    {

        // no colored keypegs -> none of the guessed can be where has been guessed
        if (keyPegs[row][COLORED_COLUMN] == 0) {
            for (int j = 0; j < NHOLES; j++) {
                knowledge.addPegNoEstaEn(codePegs[row][j], j);
            }
        }

        // as many colored keypegs as codepegs in the guess we know are well placed
            // if there are no white keypegs
                // we can infer that the rest of the key pegs are not in the pattern
            // else
                /* at least we can infer that the rest of the guess' codepegs
                 * are not where they are
                 */
        if (knowledge.HowManyInRightHole(codePegs[row]).equals(
                keyPegs[row][COLORED_COLUMN])) {
            if (keyPegs[row][WHITE_COLUMN].equals(0)) {
                for (int j = 0; j < NHOLES; j++) {
                    if (knowledge.getState(j).equals(PegKnowledge.PUEDE_ESTAR)) {
                        knowledge.addPegNoEsta(codePegs[row][j]);
                    }
                }
            } else {
                for (int j = 0; j < NHOLES; j++) {
                   knowledge.addPegNoEstaEn(codePegs[row][j], j);
                }
            }
        }


        // switch nKeyPegs
        Integer nKeyPegs = keyPegs[row][COLORED_COLUMN] + keyPegs[row][WHITE_COLUMN];

        /* the minimum -> all outside the guess are inside the pattern
         * eg: 3 keypegs, 7 holes, 11 colors --> the 4 colors that are not there
         * in the guess, are in the pattern.
         */
        if (((Integer)(NHOLES - nKeyPegs)).equals((Integer)(NCOLORS - NHOLES))) {

            for (Integer IsNot : WhichAreNot(codePegs[row])) {
                knowledge.addPegEstaPeroNoEn(IsNot);
            }

        /* the maximum -> all inside the guess are inside the pattern
         * eg: 5 keypegs, 5 holes, 8 colors --> the 5 colors that are there
         * in the guess are in the pattern and the rest don't.
         */
        } else if (nKeyPegs.equals(NHOLES)) {

            // if no one is on the right position
            if (keyPegs[row][WHITE_COLUMN].equals(NHOLES)) {

                // for each one we know that it is not on that position
                for (Integer j = 0; j < NHOLES.intValue(); j++) {
                    knowledge.addPegEstaPeroNoEn(codePegs[row][j], j);
                }

            // if some one is on the right position
            } else {

                // for each one we know that, at least, is in the pattern
                for (Integer j = 0; j < NHOLES.intValue(); j++) {
                    knowledge.addPegEstaPeroNoEn(codePegs[row][j]);
                }

            }

            for (Integer IsNot : WhichAreNot(codePegs[row])) {
                knowledge.addPegNoEsta(IsNot);
            }

        } else {

            Integer inRow = new Integer(
                    knowledge.HowManyInPattern(codePegs[row]));

            /*
             * if nkeypegs = all known as in the pattern that are
             * in the guess (inRow)
             */
            if (inRow.equals(nKeyPegs)) {

                /* for each color in the row we unknown the state of,
                 * we now know it is not in the pattern. otherwise,
                 * nKeyPegs would have been greater than inRow!
                 */

                for (int j = 0; j < NHOLES.intValue(); j++) {
                    if (knowledge.getState(
                            codePegs[row][j]).equals(
                            PegKnowledge.PUEDE_ESTAR)) {
                        knowledge.addPegNoEsta(codePegs[row][j]);
                    }
                }

                /*
                 * furthermore, if NHOLES == in + unknown
                 * we now know all unknown are in the pattern
                 */
/*
                if (NHOLES == knowledge.HowManyInPattern() +
                        knowledge.HowManyMayBeInPattern()) {
                    for (Integer mayBe : knowledge.WhichMayBeInPattern()) {
                        knowledge.addPegEstaPeroNoEn(mayBe);
                    }
                }
*/
            }

        }

    }

    /*
     * A transition can be a relieve or a swap.
     */

    private void LearnFromTransition(final Integer fromRow, final Integer toRow)
    {

        /*
         * 1 in, 1 out <-> Relieve
         */
        if (IsRelieve(codePegs[fromRow], codePegs[toRow])) {

            ArrayList addedList = new ArrayList();
            ArrayList removedList = new ArrayList();
            ColorDiff(codePegs[fromRow], codePegs[toRow],
                    addedList, removedList);

            Integer added = (Integer)addedList.get(0);
            Integer removed = (Integer)removedList.get(0);

            LearnFromRelieve(fromRow, toRow, added, removed);

        /*
         * 0 in, 0 out
         */
        } else if (IsSwap(codePegs[fromRow], codePegs[toRow])) {

            ArrayList sourcesList = new ArrayList();
            ArrayList destinationsList = new ArrayList();
            SwapDiff(codePegs[fromRow], codePegs[toRow],
                    sourcesList, destinationsList);

            if (sourcesList.size() == 0) {
                System.out.println();
            }

            Integer source = (Integer)sourcesList.get(0);
            Integer destination = (Integer)destinationsList.get(0);

            /*
             * 1 swaped with another 1 <-> individual swap
             */
            if (sourcesList.size() == 1) {

                LearnFromSwap(fromRow, toRow, source, destination);

            }

        }

    }

    /*
     * A relieve can be done keeping the order or not
     */
    private void LearnFromRelieve(final Integer fromRow, final Integer toRow,
            Integer added, Integer removed)
    {

        /*
         * whether the order was kept or didn't, differences in the sum of
         * white + colored key pegs give information
         */
        switch(KeyDiff(fromRow, toRow)) {

            // added peg is in the pattern. the old doesn't.
            case 1:
                if (keyPegs[toRow][COLORED_COLUMN].equals(0)) {
                    knowledge.addPegEstaPeroNoEn(added, PegPosInRow(toRow, added));
                } else {
                    knowledge.addPegEstaPeroNoEn(added);
                }
                knowledge.addPegNoEsta(removed);
                break;

            // the added and the removed pegs are equivalent
                // through the eyes of the key pegs
                    // which are blind to the some things (state details)
            case 0:

                /*
                 * NO_ESTA state is transmited
                 */

                // removed -> added
                if (knowledge.getState(removed).equals(PegKnowledge.NO_ESTA)) {
                    knowledge.addPegNoEsta(added);

                // added -> removed
                } else if (knowledge.getState(added).equals(PegKnowledge.NO_ESTA)) {
                    knowledge.addPegNoEsta(removed);



                /*
                 * ESTA_PERO_NO_EN state is transmited (not its details)
                 */

                // removed -> added
                } else if (knowledge.getState(removed).equals(PegKnowledge.ESTA_PERO_NO_EN)) {
                    knowledge.addPegEstaPeroNoEn(added);

                // added -> removed
                } else if (knowledge.getState(added).equals(PegKnowledge.ESTA_PERO_NO_EN)) {
                    knowledge.addPegEstaPeroNoEn(removed);



                /*
                 * ESTA_EN state is transmited as ESTA_PERO_NO_EN (without details)
                 */

                // removed -> added
                } else if (knowledge.getState(removed).equals(PegKnowledge.ESTA_EN)) {
                    knowledge.addPegEstaPeroNoEn(added);

                // added -> removed
                } else if (knowledge.getState(added).equals(PegKnowledge.ESTA_EN)) {
                    knowledge.addPegEstaPeroNoEn(removed);



                }

                break;

            // removed peg is in the pattern. the new doesn't.
            case -1:
                if (keyPegs[fromRow][COLORED_COLUMN].equals(0)) {
                    knowledge.addPegEstaPeroNoEn(removed, PegPosInRow(fromRow, removed));
                } else {
                    knowledge.addPegEstaPeroNoEn(removed);
                }
                knowledge.addPegNoEsta(added);
                break;

        }

        if (IsReliveRemainingEqual(codePegs[fromRow], codePegs[toRow],
                added, removed)) {

            LearnFromRelieveKeptOrder(fromRow, toRow, added, removed);

        } else {

            LearnFromRelieveNoKeptOrder(fromRow, toRow, added, removed);

        }

    }

    private void LearnFromRelieveKeptOrder(final Integer fromRow, final Integer toRow,
            Integer added, Integer removed)
    {
        /*
         * color key pegs differences give information
         */
        Integer pos = PegPosInRow(toRow, added); // == PegPosInRow(fromRow, removed)
        switch (KeyColoredDiff(fromRow, toRow)) {

            // added peg is exactly in the right hole
            case 1:
                knowledge.addPegEstaEn(added, pos);

                switch (KeyDiff(fromRow, toRow)) {
                    // and the removed peg is in the pattern, although not in that hole
                    case 0:
                        knowledge.addPegEstaPeroNoEn(removed, pos);
                        break;
                }
                break;

            // removed peg was exactly in the right hole
            case -1:
                knowledge.addPegEstaEn(removed, pos);

                // but the new peg is in the pattern
                switch (KeyDiff(fromRow, toRow)) {
                    // and the added peg is in the pattern, although not in that hole
                    case 0:
                        knowledge.addPegEstaPeroNoEn(added, pos);
                        break;
                }
                break;
        }

    }

    private void LearnFromRelieveNoKeptOrder(final Integer fromRow, final Integer toRow,
            Integer added, Integer removed)
    {
        
    }

    private void LearnFromSwap(final Integer fromRow, final Integer toRow,
            Integer source, Integer destination)
    {

        switch(KeyColoredDiff(fromRow, toRow)) {

            // both were right
            case -2:
                knowledge.addPegEstaEn(codePegs[fromRow][source], source);
                knowledge.addPegEstaEn(codePegs[fromRow][destination], destination);
                break;

            // they are not in its new positions
            case 0:
                knowledge.addPegEstaPeroNoEn(codePegs[fromRow][source], destination);
                knowledge.addPegEstaPeroNoEn(codePegs[fromRow][destination], source);
                break;

           // they are in its new positions
            case 2:
                knowledge.addPegEstaEn(codePegs[fromRow][source], destination);
                knowledge.addPegEstaEn(codePegs[fromRow][destination], source);
                break;

        }

    }

    /**
     * @pre movements were swap(x, y) and swap(y, z).
     *  - all the swapped pegs were in the state ESTA_PERO_NO_EN
     * @param firstRow
     * @param secondRow
     * @param thirdRow
     */
    private void LearnFromDoubleTransition(final Integer firstRow,
            final Integer secondRow, final Integer thirdRow)
    {
        ArrayList originsList;
        ArrayList destinationsList;

        /*
         * If first transition is a swap(x,y) and second is a swap(y,z)
         *  If first transition wins a colored peg
         *      If second transition looses a colored peg
         *          y is in position(y)
         *      If the second transition does not modify the colored pegs won
         *          x is position(x)
         */

        if (IsSwap11(codePegs[firstRow], codePegs[secondRow])) {
            if (IsSwap11(codePegs[secondRow], codePegs[thirdRow])) {

                // if first transition won a colored peg
                if (KeyColoredDiff(firstRow, secondRow).equals(1)) {

                    // transition 1
                    originsList = new ArrayList();
                    destinationsList = new ArrayList();

                    SwapDiff(codePegs[firstRow], codePegs[secondRow],
                            originsList, destinationsList);

                    Integer pO1 = new Integer((Integer)originsList.get(0));

                    Integer pD1 = new Integer((Integer)destinationsList.get(0));

                    // transition 2
                    originsList = new ArrayList();
                    destinationsList = new ArrayList();

                    SwapDiff(codePegs[secondRow], codePegs[thirdRow],
                            originsList, destinationsList);

                    Integer pO2 = new Integer((Integer)originsList.get(0));

                    Integer pD2 = new Integer((Integer)destinationsList.get(0));

                    Integer X, Y, Z;
                    Integer pX = new Integer(-1);
                    Integer pY = new Integer(-1);
                    Integer pZ = new Integer(-1);

                    // origen 1 == origen 2
                    if (pO1.equals(pO2)) {
                        pX = new Integer(pD1);
                        pY = new Integer(pO2);
                        pZ = new Integer(pD2);

                    // origen 1 == destí 2
                    } else if (pO1.equals(pD2)) {
                        pX = new Integer(pD1);
                        pY = new Integer(pD2);
                        pZ = new Integer(pO2);

                    // destí 1 == origen 2
                    } else if (pD1.equals(pO2)) {
                        pX = new Integer(pO1);
                        pY = new Integer(pO2);
                        pZ = new Integer(pD2);

                    // destí 1 == destí 2
                    } else if (pD1.equals(pD2)) {
                        pX = new Integer(pO1);
                        pY = new Integer(pD2);
                        pZ = new Integer(pO2);

                    }

                    X = new Integer(codePegs[thirdRow][pX]);
                    Z = new Integer(codePegs[thirdRow][pZ]);

                    switch(KeyColoredDiff(secondRow, thirdRow)) {

                        // if second transition looses a colored peg
                        case -1:
                            knowledge.addPegEstaEn(Z, pY);
                            break;

                        // if second transition does not modify the colored pegs
                        case 0:
                            knowledge.addPegEstaEn(X, pX);
                            break;
                    }
                }
            }
        }
    }

    private Boolean IsSwap11(final Integer[] fromRow, final Integer[] toRow)
    {
        ArrayList addedList = new ArrayList();
        ArrayList removedList = new ArrayList();

        ColorDiff(fromRow, toRow, addedList, removedList);
        if (addedList.size() == 0 && removedList.size() == 0) {
            ArrayList sourcesList = new ArrayList();
            ArrayList destinationsList = new ArrayList();
            SwapDiff(fromRow, toRow, sourcesList, destinationsList);
            if (sourcesList.size() == 1 && destinationsList.size() == 1) {
                return true;
            }
        }
        return false;
    }



    /**
     * 
     * @param row Number of row in codePegs matrix
     * @param Color
     * @return
     * If the Color is in the row
     *  position of the Color in a row
     * Else
     *  -1
     */
    private Integer PegPosInRow(Integer row, Integer Color)
    {
        return PegPosInRow(codePegs[row], Color);
    }

    /**
     *
     * @param row
     * @param Colors
     * @return Positions of the Colors in the row
     */
    private Integer[] PegsPossInRow(Integer row, Integer[] Colors)
    {
        Integer[] pposs = new Integer[Colors.length];

        for (int j = 0; j < Colors.length; j++) {
            pposs[j] = PegPosInRow(row, Colors[j]);
        }

        return pposs;
    }

    private Integer KeyDiff(final Integer fromRow, final Integer toRow)
    {
        return KeyColoredDiff(fromRow, toRow) + KeyWhiteDiff(fromRow, toRow);
    }

    private Integer KeyColoredDiff(final Integer fromRow, final Integer toRow)
    {
        return keyPegs[toRow][COLORED_COLUMN] - keyPegs[fromRow][COLORED_COLUMN];
    }

    private Integer KeyWhiteDiff(final Integer fromRow, final Integer toRow)
    {
        return keyPegs[toRow][WHITE_COLUMN] - keyPegs[fromRow][WHITE_COLUMN];
    }

    protected Integer CurrentRow()
    {
        Integer row;

        for (row = 0;
                (row.intValue() < codePegs.length) && (codePegs[row][0] != null);
                row++) {}

        return row;
    }





    /*
     *
     * Knowledge synthesis
     *
     */

    protected Integer[] getSuccessor()
    {
        /*
         * The successor generation is divided in 2 stages:
         * Stage 1: Selection. Get which colors are in the pattern.
         * Stage 2: Order. Get which position has each color in the pattern.
         *
         * There are several techniques for stage 1, and the depend on the the
         * difference between NCOLORS and NHOLES.
         *
         * There is only one technique for stage 2; it is independent from
         * NCOLORS and NHOLES.
         */

        Integer[] successor = new Integer[NHOLES];

        Integer lastRow = new Integer(CurrentRow() - 1);

        // initializes the successor with the last guess
        if (lastRow.intValue() >= 0) {
            for (int j = 0; j < NHOLES.intValue(); j++) {
                successor[j] = new Integer(codePegs[lastRow][j]);
            }
        }

        // if we already know where the pegs are
        if (knowledge.HowManyInRightHole().equals(NHOLES)) {

            SuccessorWin(successor);

        // if we already know which pegs are in the pattern
        } else if (knowledge.HowManyInPattern().equals(NHOLES)) {

            SuccessorOrder(successor);

        // if we don't know yet which peg are in the pattern
        } else {

            SuccessorSelection(lastRow, successor);

        }


        return successor;
    }

    private void SuccessorWin(Integer[] successor)
    {
        // which pegs in right hole
        Integer[] wrh = knowledge.WhichAreInRightHole();

        for (int j = 0; j < NHOLES; j++) {
            successor[knowledge.WhereIs(wrh[j])] = new Integer(wrh[j]);
        }
    }

    protected abstract void SuccessorSelection(Integer lastRow,
            Integer[] successor);

    private void SuccessorOrder(Integer[] successor)
    {
        // if not all colors in the pattern were in the last guess
        if (!knowledge.HowManyInPattern(successor).equals(NHOLES)) {

            /* progressive approach
             *  more information could be retrieved from this attempt
             *  however testing all NHOLES good colors could begin later
             */
            Relieve1BadBy1Good(successor);


            /*
             * 
             */
            // TODO: RelieveAllBadByGood(successor);

        // if all colors in the were in the last guess
        } else {

            Swap(successor);

        }
    }
    
    protected void Swap(Integer[] successor)
    {

        Integer[] oldSuccessor = new Integer[NHOLES];
        System.arraycopy(successor, 0, oldSuccessor, 0, successor.length);

        SwapOne(successor);

        // if could not swap one
        if (Arrays.equals(successor, oldSuccessor)) {
            SwapAll(successor);
        }

    }

    /**
     * Swap all into places where it is possible
     * @pre All pegs in successor are in the pattern.
     * @param successor
     */
    protected abstract void SwapAll(Integer[] successor);

    /**
     * Swap a couple of colors already in the successor
     * 
     * @param successor
     */
    protected void SwapOne(Integer[] successor)
    {
        // list of swappable positions
        ArrayList swappablePoss = getSwappablePoss();

        /* System.out.println("Swappable positions: " +
                IntegerArrayToString(ArrayListToIntegerArray(swappablePoss)));
        */

        // list of potential swaps
            /* cartesian product filtering
             * - reflexive swaps reflexive
             * - duplicate swaps
             * - already discarded swaps
             */
        ArrayList<Integer> origins = new ArrayList();
        ArrayList<Integer> destinations = new ArrayList();
        getPotentialSwaps(swappablePoss, origins, destinations);



        /*
         * build successor
         *
         * eg:
         *  with data:
         *      last row = 1425, origins(0) = 1, destinations(0) = 3
         * result:
         *      2415
         */

        if (origins.size() == 0 || destinations.size() == 0) {
            if (DEBUG) System.out.println("swap(): there are no origins or destinations.");
            if (DEBUG) System.out.println("Successor: " + IntegerArrayToString(successor));

            return;
        }

        int i = 0;

        // successor Attempt
        Integer[] Attempt = new Integer[successor.length]; 

        do {
            System.arraycopy(successor, 0, Attempt, 0, successor.length);

            Integer aux;
            Integer pos1 = (Integer)origins.get(i);
            Integer pos2 = (Integer)destinations.get(i);

            aux = Attempt[pos1];
            Attempt[pos1] = Attempt[pos2];
            Attempt[pos2] = aux;
            if (DEBUG) System.out.println("successorAttempt: " + IntegerArrayToString(Attempt));
            
            i++;
            
        // discard proposing a previous guess
        } while (IsRowInMatrix(Attempt, codePegs) && i < origins.size());

        System.arraycopy(Attempt, 0, successor, 0, successor.length);
        if (DEBUG) System.out.println("final successor: " + IntegerArrayToString(Attempt));
    }

    /**
     * Relieve 1 color in the successor but not in the pattern
     * by a color not in the the successor but in the pattern
     * @pre At least 1 color in the successor is not in the pattern, and at
     *      least 1 color in the pattern is not in the successor.
     * @param successor has NHOLES positions
     */
    protected void Relieve1BadBy1Good(Integer[] successor)
    {
        Integer posOfBad = -1;
        Integer colorIn = -1;
        Integer i = 0;

        Integer[] colorsIn = knowledge.WhichAreInPattern();
        Integer[] colorsNotIn = knowledge.WhichAreNotInPattern();

        // position of 1 color in the successor but not in the pattern
        i = 0;
        do {
            // if color not in pattern is in the successor
            if (PegPosInRow(successor, colorsNotIn[i]).intValue() > -1) {
                posOfBad = PegPosInRow(successor, colorsNotIn[i]);
            }
            i++;
        } while (posOfBad.equals(-1) && i < colorsNotIn.length);
        if (posOfBad.equals(-1)) return;

        // color not in the successor but in the pattern
        i = 0;
        do {
            // if color in pattern is not in the successor
            if (PegPosInRow(successor, colorsIn[i]).equals(-1)) colorIn = colorsIn[i];
            i++;
        } while (colorIn.equals(-1) && i < NCOLORS);
        if (colorIn.equals(-1)) return;

        successor[posOfBad] = new Integer(colorIn);
    }

    /**
     * 
     * @return Which positions of the 
     */
    private ArrayList getSwappablePoss()
    {
        Integer row = CurrentRow() - 1;

        // swappable pegs as ArrayList
        ArrayList swappablePegs = getSwappablePegs(row);

        // swappablePegs as Integer array
        Integer[] spAsInt = ArrayListToIntegerArray(swappablePegs);
   
        // result as Integer array
        Integer[] Ires = PegsPossInRow(row, spAsInt);

        // result as ArrayList
        ArrayList res = IntegerArrayToArrayList(Ires);

        return res;
    }

    private ArrayList getSwappablePegs(int row)
    {
        ArrayList<Integer> swappable = new ArrayList();

        for (int i = 0; i < NCOLORS.intValue(); i++) {
            if (knowledge.getState(i).equals(PegKnowledge.ESTA_PERO_NO_EN) ||
                    (
                        knowledge.getState(i).equals(PegKnowledge.ESTA_EN) &&
                        !knowledge.WhereIs(i).equals(PegPosInRow(row, i))
                     )
            ) {
                swappable.add(i);
            }
        }

        return swappable;
    }
    
    /**
     *
     * @param poss Swappable positions
     * @param origins Origins of the compted swaps
     * @param destinations Destinations of the computed swaps
     */
    private void getPotentialSwaps(ArrayList<Integer> poss,
            ArrayList origins, ArrayList destinations)
    {
        Integer origin;
        Integer destination;

        /* cartesian product of swappable pegs filtering
         * - reflexive swaps reflexive
         * - duplicate swaps
         */
        for (int i = 0; i < poss.size(); i++) {
            origin = new Integer(poss.get(i));

            for (int j = 0; j < poss.size(); j++) {
                destination = new Integer(poss.get(j));

                // filter reflexives
                if (!origin.equals(destination)) {

                    // filter duplicates
                    if (FindSwap(origin, destination, origins, destinations).equals(-1)) {

                        origins.add(origin);
                        destinations.add(destination);

                    } // fi duplicates

                } // fi reflexives
            }
        }

        /*
         * discard swaps that imply positioning pegs where we already know
         * they are not.
         */
        // ArrayList<Integer> originsCopy = new ArrayList(origins);
        // ArrayList<Integer> destinationsCopy = new ArrayList(destinations);

        // for each unfiltered swap
        for (int i = 0; i < origins.size(); i++) {

            // positions
            Integer pos1 = (Integer) origins.get(i);
            Integer pos2 = (Integer) destinations.get(i);

            // pegs affected
            Integer peg1 = codePegs[CurrentRow() - 1][pos1];
            Integer peg2 = codePegs[CurrentRow() - 1][pos2];

            // if the swap is not possible for someone of the pegs
            if (
                    !knowledge.CanPegBeInPos(peg1, pos2) ||
                    !knowledge.CanPegBeInPos(peg2, pos1)
            ) {
                // we forget the swap
                origins.remove(i);
                destinations.remove(i);
                i--;
            }

        }

    }

}
