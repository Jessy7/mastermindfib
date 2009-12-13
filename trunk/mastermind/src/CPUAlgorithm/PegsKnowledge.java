
package CPUAlgorithm;

import java.util.ArrayList;

/**
 * This class groups the knowledge about pegs and provides methods
 * to access (write and read) to this knowledge.
 *
 * Write methods are named addPeg*, and they allow the user to abstract from
 * whether the added information was already known or didn't; adding less
 * precise information than the already kept by the knowledge,
 * does not makes the knowledge less precise than before.
 *
 * However, it does not manage writtings that contradict the current knowledge;
 * this must be controlled by the user.
 *
 * @author Samuel Gómez
 */
public class PegsKnowledge
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

    protected static ArrayList IntegerArrayToArrayList(Integer[] integerArray)
    {
        ArrayList arrayList = new ArrayList();

        for (int i = 0; i < integerArray.length; i++) {
            arrayList.add(integerArray[i]);
        }

        return arrayList;
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





    /**
     * New instance
     * 
     * @param _nColors
     * @param _nHoles
     */
    public PegsKnowledge(int _nColors, int _nHoles)
    {
        nHoles = new Integer(_nHoles);

        nColors = new Integer(_nColors);

        knowledge = new PegKnowledge[nColors];
        for (int i = 0; i < _nColors; i++) {
            knowledge[i] = new PegKnowledge();
        }
    }

    /**
     * Copy instance
     *
     * @param original
     */
    public PegsKnowledge(PegsKnowledge original)
    {
        nHoles = new Integer(original.nHoles);

        nColors = new Integer(original.nColors);

        knowledge = new PegKnowledge[nColors];
        for (int i = 0; i < nColors; i++) {
            knowledge[i] = new PegKnowledge(original.knowledge[i]);
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
                if (knowledge[i].getState().equals(PegKnowledge.PUEDE_ESTAR)) {
                    addPegEstaPeroNoEn(i);
                }
            }
        }
        
    }

    public void addPegNoEstaEn(int peg, Integer DondeNoEsta)
    {
        knowledge[peg].addPegNoEstaEn(DondeNoEsta);
        
        Integer[] where = WhereMayBe(peg);
        if (where.length == 1) {

            /* if a peg P in the pattern can only be in one possible hole H, P is in H */
            if (knowledge[peg].getState().equals(PegKnowledge.ESTA_PERO_NO_EN)) {
                addPegEstaEn(peg, where[0]);
            }

        } else if (where.length == 0) {

            /* if a peg P in the pattern can not be in any hole, P is not in the pattern */
            addPegNoEsta(peg);

        }

    }

    public void addPegEstaPeroNoEn(int peg)
    {
        knowledge[peg].addPegEstaPeroNoEn();

        // if we know all which are in, we know than the rest are not
        if (HowManyInPattern().equals(nHoles)) {
            for (int i = 0; i < nColors; i++) {
                if (!knowledge[i].isInPattern()) {
                    addPegNoEsta(i);
                }
            }
        }
    }

    public void addPegEstaPeroNoEn(int peg, Integer DondeNoEsta)
    {
        knowledge[peg].addPegEstaPeroNoEn(DondeNoEsta);

        /* if a peg P in the pattern can only be in one possible hole H, P is in H */
        Integer[] where = WhereMayBe(peg);
        if (where.length == 1) {
            addPegEstaEn(peg, where[0]);
        }

        // if we know all which are in, we know than the rest are not
        if (HowManyInPattern().equals(nHoles)) {
            for (int i = 0; i < nColors; i++) {
                if (!knowledge[i].isInPattern()) {
                    addPegNoEsta(i);
                }
            }
        }
    }

    public void addPegEstaEn(int peg, Integer DondeEsta)
    {
        knowledge[peg].addPegEstaEn(DondeEsta);

        /* peg P is in hole H implies all the other pegs are not in hole H */
        for (int i = 0; i < knowledge.length; i++) {
            if (i != peg) {
                addPegNoEstaEn(i, DondeEsta);
            }
        }

        // if we know all which are in, we know than the rest are not
        if (HowManyInPattern().equals(nHoles)) {
            for (int i = 0; i < nColors; i++) {
                if (!knowledge[i].isInPattern()) {
                    addPegNoEsta(i);
                }
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

    public Integer[] WhereMayBe(int peg)
    {
        ArrayList res = new ArrayList();

        for (int hole = 0; hole < nHoles; hole++) {
            if (CanPegBeInPos(peg, hole)) {
                res.add(hole);
            }
        }

        return ArrayListToIntegerArray(res);
    }

    private Integer HowManyInState(Integer state)
    {
        Integer res = new Integer(0);

        for (int i = 0; i < knowledge.length; i++) {
            if (knowledge[i].getState().equals(state)) {
                res++;
            }
        }

        return res;
    }

    private Integer HowManyInState(Integer state, Integer[] Colors)
    {
        Integer res = new Integer(0);

        for (int j = 0; j < Colors.length; j++) {
            if (knowledge[Colors[j]].getState().equals(state)) {
                res++;
            }
        }

        return res;
    }

    /**
     *
     * @param Colors
     * @return How many of the colors in Colors it is known that there are
     * in the pattern and right in the position that they have in Colors
     */
    public Integer HowManyInRightHole(Integer[] Colors)
    {
        Integer res = new Integer(0);

        for (int j = 0; j < Colors.length; j++) {
            if (knowledge[Colors[j]].WhereIs().equals(j)) {
                res++;
            }
        }

        return res;
    }

    /**
     *
     * @return How many of the colors (all colors) is known where are
     * in the pattern
     */
    public Integer HowManyInRightHole()
    {
        return HowManyInState(PegKnowledge.ESTA_EN);
    }

    /**
     *
     * @param array
     * @return how many of the colors in the array is known that are
     * in the pattern (state == ESTA_EN || state == ESTA_PERO_NO_EN)
     */
    public Integer HowManyInPattern(Integer[] Colors)
    {
        return HowManyInState(PegKnowledge.ESTA_EN, Colors) +
                HowManyInState(PegKnowledge.ESTA_PERO_NO_EN, Colors);
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

    public Integer[] WhichMayBeInPos(int pos)
    {
        ArrayList CanBeInHole = new ArrayList();

        for (int c = 0; c < nColors.intValue(); c++) {

            if (CanPegBeInPos(c, pos)) {

                CanBeInHole.add(c);

            }
        }

        return ArrayListToIntegerArray(CanBeInHole);
    }

    public Integer[] WhichAreInRightHole()
    {
        ArrayList which = new ArrayList();

        for (int i = 0; i < nColors.intValue(); i++) {
            if (!knowledge[i].WhereIs().equals(-1)) which.add(i);
        }

        return ArrayListToIntegerArray(which);
    }

    public Integer[] WhichAreInPattern()
    {
        ArrayList which = new ArrayList();

        for (int i = 0; i < nColors.intValue(); i++) {
            if (knowledge[i].isInPattern()) which.add(i);
        }

        return ArrayListToIntegerArray(which);
    }

    public Integer[] WhichAreNotInPattern()
    {
        ArrayList which = new ArrayList();

        for (int i = 0; i < nColors.intValue(); i++) {
            if (knowledge[i].isNotInPattern()) which.add(i);
        }

        return ArrayListToIntegerArray(which);
    }

    public Integer[] WhichMayBeInPattern()
    {
        ArrayList which = new ArrayList();

        for (int i = 0; i < nColors.intValue(); i++) {
            if (knowledge[i].mayBeInPatter()) which.add(i);
        }

        return ArrayListToIntegerArray(which);
    }

    public void print()
    {
        String str = new String("");
        Integer[] details;

        for (int i = 0; i < nColors.intValue(); i++) {

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

    }

    public Boolean equals(PegsKnowledge B)
    {
        for (int i = 0; i < nColors; i++) {
            if (!knowledge[i].equals(B.knowledge[i])) {
                return false;
            }
        }

        return true;
    }
    
}
