/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package UseCaseController;

import DomainController.GameDomainController;
import Enum.DifficultyLevel;


public class SettingsUseCaseController extends GenericSettingsUCC {

    public SettingsUseCaseController() {}

    /**
     * Sets if the player2 is a human or the cpu
     * @param CPU true if player2 is the cpu, false if player2 is a human
     */
    public void setPlayer2(boolean CPU) {
        gdc = GameDomainController.getInstance();
        ((GameDomainController)gdc).setPlayer2(CPU);
    }

    /**
     * Configures de settings of the created game
     * @param dl DifficultyLevel of the game
     * @param vsCpu true if player2 is the cpu, false if player2 is a human 
     */
    public void setSettings(DifficultyLevel dl, Boolean vsCpu) {
        gdc = GameDomainController.getInstance();
        ((GameDomainController)gdc).setSettings(dl, vsCpu);
    }

    @Override
    public void saveSettings(DifficultyLevel dif) {
        throw new UnsupportedOperationException("Not necessary");
    }
}
