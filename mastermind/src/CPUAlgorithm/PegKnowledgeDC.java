
package CPUAlgorithm;

import Domain.PegKnowledge;
import java.util.ArrayList;

/**
 * This class groups the knowledge about pegs and provides methods
 * to access to this knowledge
 *
 * @author Samuel Gomez
 */
public class PegKnowledgeDC
{
    private Integer nHoles;
    private Integer nColors;
    private PegKnowledge[] knowledge;





    /*
     *
     * static utils
     *
     */
    private static Integer[] ArrayListToIntegerArray(ArrayList arrayList)
    {
        Integer[] integerArray = new Integer[arrayList.size()];

        for (int i = 0; i < arrayList.size(); i++) {
            integerArray[i] = (Integer)arrayList.get(i);
        }

        return integerArray;
    }

    private static Boolean Contains(final Integer[] hayStack, final Integer needle)
    {
        for (int i = 0; i < hayStack.length; i++) {
            if (hayStack[i].equals(needle)) {
                return true;
            }
        }
        return false;
    }





    public PegKnowledgeDC(int ncolors, int nholes)
    {
        nHoles = new Integer(nholes);

        nColors = new Integer(ncolors);

        knowledge = new PegKnowledge[ncolors];
        for (int i = 0; i < ncolors; i++) {
            knowledge[i] = new PegKnowledge();
        }
    }

    public Integer getState(int peg)
    {
        return knowledge[peg].getState();
    }

    public void addPegNoEsta(int peg)
    {
        knowledge[peg].addPegNoEsta();

        /*
         * when we know all the ones which are out,
         * we know that the rest ones are in
         */
        if (HowManyInState(PegKnowledge.NO_ESTA).equals(nColors - nHoles)) {
            for (int i = 0; i < nColors; i++) {
                addPegEstaPeroNoEn(i);
            }
        }
        
    }

    public void addPegEstaPeroNoEn(int peg)
    {
        knowledge[peg].addPegEstaPeroNoEn();
    }

    public void addPegEstaPeroNoEn(int peg, Integer DondeNoEsta)
    {
        knowledge[peg].addPegEstaPeroNoEn(DondeNoEsta);

        // if it is known that there it is but not in any possible holes minus 1
        // we can infer that it is in the remaining hole, so
        // we can upgrade its status to ESTA_EN
        Integer[] details = knowledge[peg].getStateDetails();
        Integer DondeEsta = -1;
        Integer j = 0;

        if (details.length == nHoles - 1) {
            do {
                if (!Contains(details, j)) {
                    // we got him
                    DondeEsta = new Integer(j);
                }
                j++;
            } while (DondeEsta.equals(-1));

            // upgrade state
            addPegEstaEn(peg, DondeEsta);
        }
    }

    public void addPegEstaEn(int peg, Integer DondeEsta)
    {
        knowledge[peg].addPegEstaEn(DondeEsta);

        for (int i = 0; i < knowledge.length; i++) {
            if ((i != peg) && (getState(peg).equals(PegKnowledge.ESTA_PERO_NO_EN))) {
                addPegEstaPeroNoEn(i, DondeEsta);
            }
        }
    }

    public Boolean CanPegBeInPos(int peg, Integer pos)
    {
        Boolean CanBePerSe = knowledge[peg].CanPegBeInPos(pos);
        Boolean CouldBeOnSet = true;

        if (CanBePerSe) {

            for (int i = 0; i < knowledge.length; i++) {

                // if there is already a peg in that pos
                if ((i != peg) &&
                        (knowledge[i].getState().equals(PegKnowledge.ESTA_EN)) &&
                        (knowledge[i].WhereIs().equals(pos))) {
                    CouldBeOnSet = false;
                    
                }
            }
        }

        return CanBePerSe && CouldBeOnSet;
    }

    public Integer WhereIs(int peg)
    {
        return knowledge[peg].WhereIs();
    }

    public Integer HowManyInState(Integer state)
    {
        Integer res = 0;

        for (int i = 0; i < knowledge.length; i++) {
            if (knowledge[i].getState().equals(state)) {
                res++;
            }
        }

        return res;
    }

    /**
     *
     * @param array
     * @return how many of the colors in the array is known that are
     * in the pattern (state == ESTA_EN || state == ESTA_PERO_NO_EN)
     */
    public Integer HowManyInPattern(Integer[] Colors)
    {
        Integer res = new Integer(0);

        for (int i = 0; i < Colors.length; i++) {
            if (knowledge[Colors[i]].getState().equals(PegKnowledge.ESTA_EN) ||
                    knowledge[Colors[i]].getState().equals(PegKnowledge.ESTA_PERO_NO_EN)) {
                res++;
            }
        }

        return res;
    }

    /**
     * 
     * @return how many of the colors (all colors) is known that are in the pattern
     */
    public Integer HowManyInPattern()
    {
        return HowManyInState(PegKnowledge.ESTA_EN) +
                HowManyInState(PegKnowledge.ESTA_PERO_NO_EN);
    }

    public Integer HowManyMayBeInPattern()
    {
        return HowManyInState(PegKnowledge.PUEDE_ESTAR);
    }

    public Integer[] WhichAreInPattern()
    {
        ArrayList which = new ArrayList();

        for (int i = 0; i < nColors; i++) {
            if (knowledge[i].isInPattern()) which.add(i);
        }

        return ArrayListToIntegerArray(which);
    }

    public Integer[] WhichAreNotInPattern()
    {
        ArrayList which = new ArrayList();

        for (int i = 0; i < nColors; i++) {
            if (knowledge[i].isNotInPattern()) which.add(i);
        }

        return ArrayListToIntegerArray(which);
    }

    public Integer[] WhichMayBeInPattern()
    {
        ArrayList which = new ArrayList();

        for (int i = 0; i < nColors; i++) {
            if (knowledge[i].mayBeInPatter()) which.add(i);
        }

        return ArrayListToIntegerArray(which);
    }

    public void print()
    {
        String str = new String("");
        Integer[] details;

        for (int i = 0; i < nColors; i++) {

            // state
            str = i + " " + knowledge[i].getState().toString();

            // state details
            str += " (";
            details = knowledge[i].getStateDetails();
            for (int j = 0; j < details.length; j++) {
                str += ", "  + details[j];
            }
            str += ") ";

            System.out.println(str);
        }

        System.out.println();
    }
    
}
