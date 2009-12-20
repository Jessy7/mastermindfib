package UseCaseController;

import DomainController.GameDomainController;

/**
 * This class implements NewGame use case controller. It's responsable to create
 * the instance of the game.
 * @author Oriol Bellet
 */
public class NewGameUseCaseController extends GenericNewGameUCC {

    /**
     * NewGameUseCaseController creator
     */
    public NewGameUseCaseController () {}

    /**
     * This method get the instance of GameDomainController and creates a new Game
     * @return 0 if ok, -1 if error
     */
    @Override
    public int createGame() {
        gGDC = GameDomainController.getInstance();
        return ((GameDomainController)gGDC).newGame();
    }
}