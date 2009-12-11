
package CPUAlgorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


/**
 * This represents the knowledge about a peg during the computation of a guess
 * for a game with no duplicate allowed
 * @author Samuel Gomez
 */
public class PegKnowledge
{

    public static final Integer NO_ESTA = -2;
    public static final Integer PUEDE_ESTAR = 0;
    public static final Integer ESTA_PERO_NO_EN = 1;
    public static final Integer ESTA_EN = 2;

    /*
     * One row per color (from 1 to 6).
     * Columns:
     *  - State: {NO_ESTA, ..., ESTA_EN}
     *  - Details about the state. Depending on the value of the state:
     *      - If PUEDE_ESTAR: Stores where it is known that the peg is not.
     *      - If ESTA_PERO_NO_EN: Stores where it is known that the peg is not.
     *      - If ESTA_EN: Stores where the peg is.
     *
     */
    private Integer state;
    private HashSet state_details;

    private static Integer[] ArrayListToIntegerArray(ArrayList arrayList)
    {
        Integer[] integerArray = new Integer[arrayList.size()];

        for (int i = 0; i < arrayList.size(); i++) {
            integerArray[i] = (Integer)arrayList.get(i);
        }

        return integerArray;
    }

    public PegKnowledge()
    {
        state = PUEDE_ESTAR;
        state_details = new HashSet();
    }

    public PegKnowledge(PegKnowledge original)
    {
        state = new Integer(original.state);
        state_details = new HashSet(original.state_details);
    }

    public Integer getState()
    {
        return state;
    }

    public Integer[] getStateDetails()
    {
        ArrayList resList = new ArrayList();
        Iterator iterator = state_details.iterator();

        while (iterator.hasNext()) {
            resList.add(iterator.next());
        }

        return ArrayListToIntegerArray(resList);
    }

    public void addPegNoEsta()
    {
        state = NO_ESTA;
        state_details.clear();
    }

    public void addPegNoEstaEn(Integer DondeNoEsta)
    {
        if (state.intValue() == PUEDE_ESTAR.intValue() ||
                state.intValue() == ESTA_PERO_NO_EN.intValue()) {
            state_details.add(new Integer(DondeNoEsta));
        }

        if (DondeNoEsta < 0) {
            System.err.println("Warning: Invalid DondeNoEsta value (" + DondeNoEsta + ")");
        }
    }

    /**
     * keeps knowledge about where the peg is not (state_details)
     */
    public void addPegEstaPeroNoEn()
    {
        if (ESTA_PERO_NO_EN.intValue() > state) {
            state = ESTA_PERO_NO_EN;
        }
    }

    public void addPegEstaPeroNoEn(Integer DondeNoEsta)
    {
        if (ESTA_PERO_NO_EN.intValue() >= state.intValue()) {
            state = ESTA_PERO_NO_EN;
            state_details.add(DondeNoEsta);
        }

        if (DondeNoEsta < 0) {
            System.err.println("Warning: Invalid DondeNoEsta value (" + DondeNoEsta + ")");
        }
    }

    public void addPegEstaEn(Integer DondeEsta)
    {
        if (ESTA_EN.intValue() > state.intValue()) {
            state = ESTA_EN;
            state_details.clear();
            state_details.add(DondeEsta);
        }
    }

    public Boolean CanPegBeInPos(Integer pos)
    {
        
        if (state.equals(NO_ESTA)) return false;

        if (state.equals(PUEDE_ESTAR)) {
            return !state_details.contains(pos);
        }

        if (state.equals(ESTA_PERO_NO_EN)) {
            return !state_details.contains(pos);
        }

        if (state.equals(ESTA_EN)) {
            return state_details.contains(pos);
        }

        return false;
    }

    public Integer WhereIs()
    {
        Iterator itr;

        if (state.equals(ESTA_EN)) {
            itr = state_details.iterator();
            return (Integer)itr.next();
        } else {
            return -1;
        }
    }
    
    public Boolean isInPattern()
    {
        return state.equals(ESTA_EN) || state.equals(ESTA_PERO_NO_EN);
    }

    public Boolean isNotInPattern()
    {
        return state.equals(NO_ESTA);
    }

    public Boolean mayBeInPatter()
    {
        return state.equals(PUEDE_ESTAR);
    }

    public Boolean equals (PegKnowledge B)
    {
        return state.equals(B.state) &&
                state_details.equals(B.state_details);
    }

    private Integer Compare (Integer stateA, Integer stateB)
    {
        if (Math.abs(stateA) > Math.abs(stateB)) {
            return 1;
        } else if (Math.abs(stateA) < Math.abs(stateB)) {
            return -1;
        } else {
            return 0;
        }
    }
}
