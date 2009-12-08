
package CPUAlgorithm;

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





    public PegKnowledgeDC(int _nColors, int _nHoles)
    {
        nHoles = new Integer(_nHoles);

        nColors = new Integer(_nColors);

        knowledge = new PegKnowledge[_nColors];
        for (int i = 0; i < _nColors; i++) {
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

    public void addPegNoEstaEn(int peg, Integer DondeNoEsta)
    {
        knowledge[peg].addPegNoEstaEn(DondeNoEsta);

        Integer[] where = WhereMayBe(peg);
        if (where.length == 1) {
            addPegEstaEn(peg, where[0]);
        }
    }

    public void addPegEstaPeroNoEn(int peg)
    {
        knowledge[peg].addPegEstaPeroNoEn();
    }

    public void addPegEstaPeroNoEn(int peg, Integer DondeNoEsta)
    {
        knowledge[peg].addPegEstaPeroNoEn(DondeNoEsta);

        Integer[] where = WhereMayBe(peg);
        if (where.length == 1) {
            addPegEstaEn(peg, where[0]);
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
    
}
