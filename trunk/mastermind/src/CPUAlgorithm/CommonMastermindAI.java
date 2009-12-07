
package CPUAlgorithm;

import java.util.ArrayList;

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
    protected static final Integer KEYPEG_NCOLS = 2;
    protected static final Integer COLORED_COL = 0;
    protected static final Integer WHITE_COL = 1;

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
     */
    protected Integer[][] codePegs;

    /*
     * X rows, KEYPEG_NCOLS columns.
     * - Column COLORED_COL: For colored key pegs.
            Indicate some color and position match.
     * - Column WHITE_COL: For white key pegs.
            Indicate some color but not position match.
     */
    protected Integer[][] keyPegs;

    protected PegKnowledgeDC knowledge;





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
        for (int j = 0; j < NHOLES; j++) {

            // the peg on the source has changed its position
            if (!fromRow[j].equals(toRow[j])) {

                // actually is a new swap
                if (!destinations.contains(j)) {
                    sources.add(j);

                    // find the new position of th epeg
                    for (int k = 0; k < NHOLES; k++) {
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

        for (int j = 0; j < NCOLORS; j++) {
            WA_bool[j] = new Boolean(false);
        }

        /*
         * WA_bool = [false][false][false][false][false][false]
         */

        for (int j = 0; j < NHOLES; j++) {
            WA_bool[codePegsRow[j]] = new Boolean(true);
        }

        /*
         * WA_bool = [true][true][false][true][false][true]
         */

        for (int j = 0; j < NCOLORS; j++) {
            if (WA_bool[j] == false) {
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

     Boolean IsRelieve(final Integer[] fromRow, final Integer[] toRow)
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
        for (int j = 0; j < NHOLES; j++) {
            if (fromRow[j].equals(removed)) {
                // don't check this hole
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
        for (int j = 0; j < NHOLES; j++) {
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
        for (int j = 0; j < NHOLES; j++) {
            if (row[j].equals(Color)) return j;
        }

        return -1;
    }


    


    /*
     *
     * Constructor
     *
     */

    public CommonMastermindAI(Integer _nHoles, Integer _nColors,
            Integer[][] _codePegs, Integer[][] _keyPegs)
    {
        NHOLES = _nHoles;
        NCOLORS = _nColors;

        codePegs = new Integer[_codePegs.length][NHOLES];
        for (int i = 0; i < _codePegs.length; i++) {
            for (int j = 0; j < NHOLES; j++) {
                codePegs[i][j] = new Integer(_codePegs[i][j]);
            }
        }
        
        keyPegs = new Integer[_keyPegs.length][KEYPEG_NCOLS];
        for (int i = 0; i < _keyPegs.length; i++) {
            for (int j = 0; j < KEYPEG_NCOLS; j++) {
                keyPegs[i][j] = new Integer(_keyPegs[i][j]);
            }
        }

        knowledge = new PegKnowledgeDC(NCOLORS, NHOLES);
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

        Learn();
        return getSuccessor();
    }
    

    

    
    /*
     * 
     * Data analysis
     * 
     */

    private void Learn()
    {
        int currRow;

        currRow = CurrentRow();

        // learn from rows
        for (int i = 0; i < currRow; i++) {
            LearnFromRow(i);
        }

        // knowledge.print();

        // learn from transitions
        for (int i = 0; i < currRow; i++) {
            for (int j = 0; j < currRow; j++) {
                if (i < j) {
                    LearnFromTransition(i, j);
                }
            }
        }

        // knowledge.print();

        // learn from double transitions
        for (int i = 0; i < currRow - 2; i++) {
            LearnFromDoubleTransition(i, i + 1, i + 2);
        }

        knowledge.print();

    }
    
    protected void LearnFromRow(Integer row)
    {
        // switch value
        Integer nKeyPegs = keyPegs[row][COLORED_COL] + keyPegs[row][WHITE_COL];

        /* the minimum -> all outside the guess are inside the pattern
         * eg: 3 keypegs, 7 holes, 11 colors --> the 4 colors that are not there
         * in the guess, are in the pattern.
         */
        if (NHOLES - nKeyPegs == NCOLORS - NHOLES) {

            for (Integer IsNot : WhichAreNot(codePegs[row])) {
                knowledge.addPegEstaPeroNoEn(IsNot);
            }

        /* the maximum -> all inside the guess are inside the pattern
         * eg: 5 keypegs, 5 holes, 8 colors --> the 5 colors that are there
         * in the guess are in the pattern and the rest don't.
         */
        } else if (nKeyPegs.equals(NHOLES)) {

            // if no one is on the right position
            if (keyPegs[row][WHITE_COL].equals(NHOLES)) {

                // for each one we know that it is not on that position
                for (Integer j = 0; j < NHOLES; j++) {
                    knowledge.addPegEstaPeroNoEn(codePegs[row][j], j);
                }

            // if some one is on the right position
            } else {

                // for each one we know that, at least, is in the pattern
                for (Integer j = 0; j < NHOLES; j++) {
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

                for (int j = 0; j < NHOLES; j++) {
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

    private void LearnFromRelieve(final Integer fromRow, final Integer toRow,
            Integer added, Integer removed)
    {
        knowledge.print();

        if (IsReliveRemainingEqual(codePegs[fromRow], codePegs[toRow],
                added, removed)) {

            LearnFromRelieveRemainingEqual(fromRow, toRow, added, removed);

        } else if (IsReliveRemainingReorder(codePegs[fromRow], codePegs[toRow],
                added, removed)) {

            /*LearnFromRelieveRemainingReorder(codePegs[fromRow], codePegs[toRow],
                    added, removed);

        } else if (IsRelieveReorder(codePegs[fromRow], codePegs[toRow],
                added, removed)) {

            LearnFromRelieveReorder(codePegs[fromRow], codePegs[toRow],
                    added, removed);
*/
        }

    }

    private void LearnFromRelieveRemainingEqual(final Integer fromRow, final Integer toRow,
            Integer added, Integer removed)
    {
        /*
         * in any positions, differences in the sum of white + colored key pegs
         * give information
         */
        switch(KeyDiff(fromRow, toRow)) {

            // added peg is in the pattern. the old don't.
            case 1:
                knowledge.addPegEstaPeroNoEn(added, PegPosInRow(toRow, added));
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
                    knowledge.addPegEstaPeroNoEn(added, PegPosInRow(toRow, added));

                // added -> removed
                } else if (knowledge.getState(added).equals(PegKnowledge.ESTA_PERO_NO_EN)) {
                    knowledge.addPegEstaPeroNoEn(removed, PegPosInRow(fromRow, removed));

                /*
                 * ESTA_EN state is transmited (not its details)
                 */

                // removed -> added
                } else if (knowledge.getState(removed).equals(PegKnowledge.ESTA_EN)) {
                    knowledge.addPegEstaEn(added, PegPosInRow(toRow, added));

                // added -> removed
                } else if (knowledge.getState(added).equals(PegKnowledge.ESTA_EN)) {
                    knowledge.addPegEstaEn(removed, PegPosInRow(fromRow, removed));

                }

                break;

            // removed peg is in the pattern. the new don't.
            case -1:
                knowledge.addPegEstaPeroNoEn(removed, PegPosInRow(fromRow, added));
                knowledge.addPegNoEsta(added);
                break;

        } // fi any position

        /*
         * in the same position, color key pegs differences give information
         */
        if (PegPosInRow(toRow, added).equals(PegPosInRow(fromRow, removed))) {

            Integer pos = PegPosInRow(toRow, added); // == PegPosInRow(fromRow, removed)
            switch (KeyColoredDiff(fromRow, toRow)) {

                // added peg is exactly in the right hole
                case 1:
                    knowledge.addPegEstaEn(added, pos);

                    // the old is not in the pattern
                    if (KeyWhiteDiff(fromRow, toRow).equals(0)) {
                        knowledge.addPegNoEsta(removed);
                    }
                    break;

                // removed peg was exactly in the right hole
                case -1:
                    knowledge.addPegEstaEn(removed, pos);

                    // the new is not in the pattern
                    if (KeyWhiteDiff(fromRow, toRow).equals(0)) {
                        knowledge.addPegNoEsta(added);
                    }
                    break;

            }
        } // fi same position

    }

    private void LearnFromRelieveRemainingReorder(final Integer fromRow, final Integer toRow,
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
        ArrayList originsList = new ArrayList();
        ArrayList destinationsList = new ArrayList();

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

                // if first transition wins a colored peg
                if (KeyColoredDiff(firstRow, secondRow).equals(1)) {

                    SwapDiff(codePegs[firstRow], codePegs[secondRow],
                            originsList, destinationsList);

                    Integer posX = (Integer)originsList.get(0);
                    Integer X = codePegs[firstRow][posX];

                    Integer posY = (Integer)destinationsList.get(0);
                    Integer Y = codePegs[firstRow][posY];

                    switch(KeyColoredDiff(secondRow, thirdRow)) {

                        // if second transition looses a colored peg
                        case -1:
                            knowledge.addPegEstaEn(Y, posY);
                            break;

                        // if second transition does not modify the colored pegs
                        case 0:
                            knowledge.addPegEstaEn(X, posX);
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
        return keyPegs[toRow][COLORED_COL] - keyPegs[fromRow][COLORED_COL];
    }

    private Integer KeyWhiteDiff(final Integer fromRow, final Integer toRow)
    {
        return keyPegs[toRow][WHITE_COL] - keyPegs[fromRow][WHITE_COL];
    }

    protected Integer CurrentRow()
    {
        Integer row;

        for (row = 0;
                row < codePegs.length && codePegs[row][0] != null;
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

        Integer[] successor = {0, 0, 0, 0};

        Integer lastRow = new Integer(CurrentRow() - 1);

        // initializes the successor with the last guess
        if (lastRow >= 0) {
            for (int j = 0; j < NHOLES; j++) {
                successor[j] = new Integer(codePegs[lastRow][j]);
            }
        }

        // if we don't know yet which colors are in the pattern
        if (knowledge.HowManyInPattern() < NHOLES) {

            SuccessorSelection(lastRow, successor);

        // if we already know which colors are in the pattern
        } else {

            SuccessorOrder(successor);

        }

        return successor;
    }

    protected abstract void SuccessorSelection(Integer lastRow,
            Integer[] successor);

    private void SuccessorOrder(Integer[] successor)
    {
        // if not all colors in the pattern were in the last guess
        if (knowledge.HowManyInPattern(successor) != NHOLES) {

            Relieve1BadBy1Good(successor);

        // if all colors in the were in the last guess
        } else {

            Swap(successor);

        }
    }
    
    /**
     * Swap a couple of colors already in the successor
     * 
     * @param successor
     */
    protected void Swap(Integer[] successor)
    {
        // list of swappable positions
        ArrayList swappablePoss = getSwappablePoss();

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

        int i = 0;

        do {
            Integer aux;
            Integer pos1 = (Integer)origins.get(i);
            Integer pos2 = (Integer)destinations.get(i);

            aux = successor[pos1];
            successor[pos1] = successor[pos2];
            successor[pos2] = aux;
            i++;

        // discard proposing a previous guess
        } while (IsRowInMatrix(successor, codePegs) && i < origins.size());

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
            if (PegPosInRow(successor, colorsNotIn[i]) > -1) {
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
        ArrayList swappablePegs = getSwappablePegs();

        // swappablePegs as Integer array
        Integer[] spAsInt = ArrayListToIntegerArray(swappablePegs);
   
        // result as Integer array
        Integer[] Ires = PegsPossInRow(row, spAsInt);

        // result as ArrayList
        ArrayList res = IntegerArrayToArrayList(Ires);

        return res;
    }

    private ArrayList getSwappablePegs()
    {
        ArrayList<Integer> swappable = new ArrayList();

        for (int i = 0; i < NCOLORS; i++) {
            if (knowledge.getState(i).equals(PegKnowledge.ESTA_PERO_NO_EN)) {
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
