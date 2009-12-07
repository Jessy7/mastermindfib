
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
     *      - If ESTA_PERO_NO_EN: Stores where it is known that the peg is not.
     *              Comma-separated numbers of column.
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
        if (greaterThan(NO_ESTA, state)) {
            state = NO_ESTA;
            state_details.clear();
        }
    }

    public void addPegEstaPeroNoEn()
    {
        if (greaterThan(ESTA_PERO_NO_EN, state)) {
            state = ESTA_PERO_NO_EN;
        }
    }

    public void addPegEstaPeroNoEn(Integer DondeNoEsta)
    {
        if (equals(ESTA_PERO_NO_EN, state) ||
                greaterThan(ESTA_PERO_NO_EN, state)) {
            state = ESTA_PERO_NO_EN;
            state_details.add(DondeNoEsta);
        }
    }

    public void addPegEstaEn(Integer DondeEsta)
    {
        if (greaterThan(ESTA_EN, state)) {
            state = ESTA_EN;
            state_details.clear();
            state_details.add(DondeEsta);
        }
    }

    public Boolean CanPegBeInPos(Integer pos)
    {
        
        if (state == NO_ESTA) return false;

        if (state == PUEDE_ESTAR) return true;

        if (state == ESTA_PERO_NO_EN) {
            return !state_details.contains(pos);
        }

        if (state == ESTA_EN) {
            return state_details.contains(pos);
        }

        return false;
    }

    public Integer WhereIs()
    {
        Iterator itr;

        if (state == ESTA_EN) {
            itr = state_details.iterator();
            return (Integer)itr.next();
        } else {
            return -1;
        }
    }
    
    public Boolean isInPattern()
    {
        return state == ESTA_EN || state == ESTA_PERO_NO_EN;
    }

    public Boolean isNotInPattern()
    {
        return state == NO_ESTA;
    }

    public Boolean mayBeInPatter()
    {
        return state == PUEDE_ESTAR;
    }

    private Boolean greaterThan (Integer stateA, Integer stateB)
    {
        return Compare(stateA, stateB) > 0;
    }

    private Boolean lessThan (Integer stateA, Integer stateB)
    {
        return Compare(stateA, stateB) < 0;
    }

    private Boolean equals (Integer stateA, Integer stateB)
    {
        return Compare(stateA, stateB) == 0;
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