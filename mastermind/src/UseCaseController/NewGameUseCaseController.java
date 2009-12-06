/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package UseCaseController;

import DomainController.GameDomainController;

/**
 *
 * @author Ori
 */
public class NewGameUseCaseController extends GenericNewGameUCC {

    public NewGameUseCaseController () {}
    
    @Override
    public int createGame() {
        gGDC = GameDomainController.getInstance();
        return ((GameDomainController)gGDC).newGame();
    }
}
