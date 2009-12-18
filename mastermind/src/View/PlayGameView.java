/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import Enum.DifficultyLevel;
import UseCaseController.PlayGameUseCaseController;
import javax.swing.JFrame;

/**
 *
 * @author Administrador
 */
public abstract class PlayGameView extends JFrame {
    public PlayGameView()
    {
        PlayGameUseCaseController nh= new PlayGameUseCaseController();

        DifficultyLevel level = nh.getLevel();
        boolean vsCPU = nh.getVsCpu();

        if (level.equals(DifficultyLevel.Easy)) {
            new PlayGameViewE(vsCPU);
        }
        else if(level.equals(DifficultyLevel.Normal)){
            new PlayGameViewM(vsCPU);
        }
        else if (level.equals(DifficultyLevel.Hard)){
            new PlayGameViewS(vsCPU);

    }
}
}
