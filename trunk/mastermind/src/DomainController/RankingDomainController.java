/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DomainController;

import Data.RankingData;
import Domain.Ranking;
import Enum.DifficultyLevel;

/**
 *
 * @author Oriol Bellet
 */
public class RankingDomainController extends GenericRankingDC {

    private static RankingDomainController INSTANCE = null;
    private RankingDomainController()
    {
        int[] values = new int[10];
        String[] names = new String[10];
        ranking = new Ranking[1];
        RankingData.loadRanking(DifficultyLevel.Easy, values, names);
        ranking[0] = new Ranking(values,names);
    }


    @Override
    protected int checkDifficulty() {
        return 0;
    }

   public static RankingDomainController getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new RankingDomainController();
        }
        return INSTANCE;
    }
}