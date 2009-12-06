/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package UseCaseController;

import DomainController.GameDomainController;
import Enum.DifficultyLevel;


public class SettingsUseCaseController extends GenericSettingsUCC {

    public SettingsUseCaseController() {}

    public void setPlayer2(boolean CPU)
    {
        gdc = GameDomainController.getInstance();
        ((GameDomainController)gdc).setPlayer2(CPU);
    }

    public void setSettings(DifficultyLevel dl, Boolean vsCpu)
    {
        gdc = GameDomainController.getInstance();
        ((GameDomainController)gdc).setSettings(dl, vsCpu);
    }

    @Override
    public void saveSettings(DifficultyLevel dif) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
