package UseCaseController;

/**
 * Class that is used to show the rules of the game in the interface.
 *
 * @author Oriol Bellet
 */
public class ShowRulesUseCaseController extends GenericShowRulesUCC {

    public ShowRulesUseCaseController () {
        Rules = "";
    }


    /**
     * Method that reads the rules stored in the class and returns the string.
     *
     * @return A string containing the rules of the game
     */
    @Override
    public String getRules(){
        return Rules;
    }

    @Override
    public void setRules(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

