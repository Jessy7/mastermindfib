/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Domain;

import Enum.DifficultyLevel;

/**
 *
 * @author Samuel Gomez
 */
public class Settings extends GenericSettings {

    private Boolean vsCPU;

    public Settings()
    {
        
    }

    public Settings(DifficultyLevel _level)
    {
        level = _level;
    }

    public Settings(DifficultyLevel _level, Boolean _vsCPU)
    {
        level = _level;
        vsCPU = _vsCPU;
    }
    
    public Settings(Settings set)
    {
        level = set.getLevel();
        vsCPU = set.getVsCPU();
    }

    public Boolean getVsCPU()
    {
        return vsCPU;
    }

    public void setVsCPU(Boolean _vsCPU)
    {
        vsCPU = _vsCPU;
    }
    
}
