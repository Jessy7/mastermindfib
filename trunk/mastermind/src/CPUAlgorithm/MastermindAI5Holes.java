
package CPUAlgorithm;

import Enum.KeyPeg;

/**
 * Since it extends from CommonMastermindAI, it only must implement the elements
 * dependant on the number of holes and colors.
 *
 * @author Samuel Gomez
 */
public class MastermindAI5Holes extends CommonMastermindAI
{
    /*
     * Sections:
     * - Constants
     * - Constructor
     * - Knowledge synthesis
     */




    
    /*
     *
     * Constants
     *
     */

    private static final Integer NHOLES = 5;
    private static final Integer NCOLORS = 6;





    /*
     *
     * Constructor
     *
     */

    public MastermindAI5Holes(Integer[][] _codePegs, KeyPeg[][] _keyPegs)
    {
        super(NHOLES, NCOLORS, _codePegs, _keyPegs);
    }





    /*
     *
     * Knowledge synthesis
     *
     */

    /**
     * @return 
     *  if 4 of the pegs are in the pattern
     *      if 4 pegs in the last guess are in the pattern
     *      do swaps among the ones in ESTA_PERO_NO_EN
     *  else
     *      test: {0, 1, 2, 3}, {1, 2, 3, 4}, {2, 3, 4, 5}, {1, 2, 3, 5}
     */
    protected void SuccessorSelection(Integer lastRow, Integer[] successor)
    {

        if (lastRow >= -1 && lastRow <= 3) {

            switch (lastRow) {
                case -1:
                    // without: 5
                    successor[0] = 0;
                    successor[1] = 1;
                    successor[2] = 2;
                    successor[3] = 3;
                    successor[4] = 4;
                    break;
                case 0:
                    // without: 0
                    successor[0] = 5;
                    successor[1] = 1;
                    successor[2] = 2;
                    successor[3] = 3;
                    successor[4] = 4;
                    break;
                case 1:
                    // without: 1
                    successor[0] = 5;
                    successor[1] = 0;
                    successor[2] = 2;
                    successor[3] = 3;
                    successor[4] = 4;
                    break;
                case 2:
                    // without: 2
                    successor[0] = 5;
                    successor[1] = 0;
                    successor[2] = 1;
                    successor[3] = 3;
                    successor[4] = 4;
                    break;
                case 3:
                    // without: 3
                    successor[0] = 5;
                    successor[1] = 0;
                    successor[2] = 1;
                    successor[3] = 2;
                    successor[4] = 4;
                    break;
                /*case 4:
                    * in the worst case, by this time we will know at least 4
                     * of the colors in the pattern, and we will know that
                     * 1 of the color does not exist in the pattern. so we will
                     * lack of information only about 2 pegs.
                     *
                     * what we can do is to make a guess composed by
                     * the 4 colors we know that are in, plus, 1 of the colors
                     * we don't know about.
                     *
                     * the feedback from this row will tell us:
                     * if we got 4 keypegs
                     *  the 4th color is not in the pattern, so the
                     *  remaining unknown color does.
                     * if we got 5 keypegs
                     *  the 4th color is in the pattern, so the
                     *  remaingin unknown color does not.
                     *
                    ArrayList successorsList = new ArrayList();
                    
                    successorsList = IntegerArrayToArrayList(
                            knowledge.WhichAreInPattern());

                    successorsList.add(IntegerArrayToArrayList(
                            knowledge.WhichMayBeInPattern()).get(0));

                    Integer[] odiojava = ArrayListToIntegerArray(successorsList);
                    for (int i = 0; i < successor.length; i++) {
                        successor[i] = odiojava[i];
                    }
                    break;*/
            }
            
        }

    }

    protected void SwapAll(Integer[] successor)
    {
        /*
         * brute force constraint satisfaction
         */
        Integer[] Attempt = new Integer[successor.length];
        for (Integer w0 : knowledge.WhichMayBeInPos(0)) {
            Attempt[0] = new Integer(w0);
            for (Integer w1 : knowledge.WhichMayBeInPos(1)) {
                Attempt[1] = new Integer(w1);
                for (Integer w2 : knowledge.WhichMayBeInPos(2)) {
                    Attempt[2] = new Integer(w2);
                    for (Integer w3 : knowledge.WhichMayBeInPos(3)) {
                        Attempt[3] = new Integer(w3);
                        for (Integer w4 : knowledge.WhichMayBeInPos(4)) {
                            Attempt[4] = new Integer(w4);
                            
                            if (!IntegerArrayFindDuplicate(Attempt)) {
                                if (!IsRowInMatrix(Attempt, codePegs)) {
                                    System.arraycopy(Attempt, 0, successor, 0, successor.length);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}
