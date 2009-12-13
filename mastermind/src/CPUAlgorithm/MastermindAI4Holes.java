
package CPUAlgorithm;

/**
 * Since it extends from CommonMastermindAI, it only must implement the elements
 * dependant on the number of holes and colors.
 *
 * @author Samuel Gomez
 */
public class MastermindAI4Holes extends CommonMastermindAI
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

    private static final Integer NHOLES = 4;
    private static final Integer NCOLORS = 6;





    /*
     *
     * Constructor
     *
     */

    /**
     * @see CommonMastermindAI
     */
    public MastermindAI4Holes(Integer[][] _codePegs, Integer[][] _keyPegs)
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

        switch (lastRow) {
            case -1:
                // without: 4,5
                successor[0] = 0;
                successor[1] = 1;
                successor[2] = 2;
                successor[3] = 3;
                break;
            case 0:
                // without: 5,0
                successor[0] = 4;
                successor[1] = 1;
                successor[2] = 2;
                successor[3] = 3;
                break;
            case 1:
                // without: 0,1
                successor[0] = 4;
                successor[1] = 5;
                successor[2] = 2;
                successor[3] = 3;
                break;
            case 2:
                // without: 1,2
                successor[0] = 4;
                successor[1] = 5;
                successor[2] = 0;
                successor[3] = 3;

                /*
                * given 6 colors and 4 holes, in the worst case,
                 * by this time we will know at least 3
                 * of the colors in the pattern, and we will know that
                 * 1 of the color does not exist in the pattern. so we will
                 * lack of information only about 2 pegs.
                 *
                 * what we can do is to make a guess composed by
                 * the 3 colors we know that are in, plus, 1 of the colors
                 * we don't know about.
                 *
                 * the feedback from this row will tell us:
                 * if we got 3 keypegs
                 *  the 4th color is not in the pattern, so the
                 *  remaining unknown color does.
                 * if we got 4 keypegs
                 *  the 4th color is in the pattern, so the
                 *  remaingin unknown color does not.
                 *

                ArrayList successorsList = new ArrayList();

                successorsList = IntegerArrayToArrayList(
                        knowledge.WhichAreInPattern());

                successorsList.add(IntegerArrayToArrayList(
                        knowledge.WhichMayBeInPattern()).get(0));

                Integer[] aux = ArrayListToIntegerArray(successorsList);
                for (int i = 0; i < successor.length; i++) {
                    successor[i] = aux[i];
                }
                */
                break;
            case 3:
                // without: 2,3
                successor[0] = 4;
                successor[1] = 5;
                successor[2] = 0;
                successor[3] = 1;
        }

    }

}
