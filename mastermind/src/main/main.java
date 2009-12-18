/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import Enum.DifficultyLevel;
import Enum.KeyPeg;
import UseCaseController.GenericPlayGameUCC;
import UseCaseController.LoadGameUseCaseController;
import UseCaseController.NewGameUseCaseController;
import UseCaseController.PlayGameUseCaseController;
import UseCaseController.SaveGameUseCaseController;
import UseCaseController.SettingsUseCaseController;
import UseCaseController.ShowRankingUseCaseController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Oriol Bellet
 */
public class main
{

    private static GenericPlayGameUCC pg;
    private static BufferedReader br;
    private static Integer[][] board;
    private static Integer[] patternColor;
    private static Integer[] patternVisibility;
    private static KeyPeg[][] keyPegs;

    public static void main (String args[]) throws IOException
    {
        
        br = new BufferedReader(new InputStreamReader(System.in));

        while(true)
        {
            String mainOption = "";
            while ((mainOption.compareTo("1") != 0)  && (mainOption.compareTo("2")!= 0) && (mainOption.compareTo("3")!= 0) && (mainOption.compareTo("4")!= 0))
            {
                System.out.println("Choose option:");
                System.out.println("1.New game");
                System.out.println("2.Load Game");
                System.out.println("3.Show Ranking");
                System.out.println("4.Exit Game");

                mainOption = br.readLine();
            }

            switch (Integer.valueOf(mainOption))
            {
                case 1 : newGame();
                            break;
                case 2 : loadGame();
                            break;
                case 3 : showRanking();
                            break;
                case 4 : exitGame();;
                            break;
            }
        }
    }

    private static void newGame() throws IOException
    {
        NewGameUseCaseController ng = new NewGameUseCaseController();
        ng.createGame();
        settings();
    }

    private static void settings() throws IOException
    {
        SettingsUseCaseController s;
        String difficulty = "";
        String vsCpu = "";
        Boolean bVsCpu;

        while (difficulty.compareTo("0")!= 0 && difficulty.compareTo("1")!= 0 && difficulty.compareTo("2")!= 0)
        {
            System.out.println("Choose level:");
            System.out.println("0.Easy");
            System.out.println("1.Normal");
            System.out.println("2.Hard");

            difficulty = br.readLine().trim();
        }

        s = new SettingsUseCaseController();

        while (vsCpu.compareTo("0")!= 0 && vsCpu.compareTo("1")!= 0)
        {
            System.out.println("Do you want to play vs CPU:");
            System.out.println("0.No");
            System.out.println("1.Yes");

            vsCpu = br.readLine();
        }

        if (vsCpu.compareTo("0")== 0)
            bVsCpu = false;
        else
            bVsCpu = true;

        s.setSettings(DifficultyLevel.getFromString(difficulty), bVsCpu);

        playGame();
    }
    
    private static void insertPattern() throws IOException 
    {
        Boolean validPatern = false;
        String pattern = "";
        int patternLenght;
        
        pg = new PlayGameUseCaseController();
        DifficultyLevel dl = ((PlayGameUseCaseController)pg).getLevel();
        patternLenght = ((PlayGameUseCaseController)pg).getPatternLength();
        
        while(!validPatern)
        {
            System.out.println("Please, insert the pattern");
            pattern = br.readLine();
            if (dl ==  DifficultyLevel.Easy && pattern.length() == patternLenght)
            {
                ((PlayGameUseCaseController)pg).setPattern(pattern);
                validPatern = true;
            }
            else if (dl ==  DifficultyLevel.Normal && pattern.length() == patternLenght)
            {
                ((PlayGameUseCaseController)pg).setPattern(pattern);
                validPatern = true;
            }
            else if (dl ==  DifficultyLevel.Hard && pattern.length() == patternLenght)
            {
                ((PlayGameUseCaseController)pg).setPattern(pattern);
                validPatern = true;
            }
            else
            {
                System.out.println("Invalid pattern");
            }
        }
    }

    private static void playGame() throws IOException
    {

        pg = new PlayGameUseCaseController();

        int currentRound;

        while (!pg.isGameFinished())
        {
            currentRound = ((PlayGameUseCaseController)pg).getCurrentRound();
            System.out.println("Current round: " + currentRound);
            initializeValues();
            startRound();
            playRound();
        }

        System.out.println("GAME FINISHED!!");
        int p1points = ((PlayGameUseCaseController)pg).getScore(1);
        System.out.println("Player1 points: " + p1points);
        int p2points = ((PlayGameUseCaseController)pg).getScore(2);
        System.out.println("Player2 points: " + p2points);

        definePlayer();

    }

    private static void startRound() throws IOException {
        if (((PlayGameUseCaseController)pg).isCodemakerHuman())
            insertPattern();
        else
            ((PlayGameUseCaseController)pg).generatePattern();
        
        patternColor = ((PlayGameUseCaseController)pg).getPatternColor();
    }

    private static void playRound() throws IOException {

        Boolean b = false;
        int guessLength = ((PlayGameUseCaseController)pg).getColumns();
        int isRoundFinished = ((PlayGameUseCaseController)pg).isRoundFinished();

        while (isRoundFinished == 0)
        {

            showElements();

            askForSave();

            if (((PlayGameUseCaseController)pg).isCodebreakerHuman()) {
                askForAHint();
            }
            
            String guess = "";

            int attempt = ((PlayGameUseCaseController)pg).getCurrentRow();
            System.out.println("Attempt: " + (attempt+1));

            if (((PlayGameUseCaseController)pg).isCodebreakerHuman()) {
                b = false;
                while (!b) {
                    System.out.println("Please, insert the guess");
                    guess = br.readLine();

                    Integer[] iGuess = new Integer[guess.length()];
                    for(int i = 0; i < guess.length(); i++)
                        iGuess[i] = Integer.valueOf(guess.substring(i,i+1));

                    if (guess.length() == guessLength)
                    {
                        for (int i = 0; i < guessLength; i++)
                            ((PlayGameUseCaseController)pg).setCell(attempt, i, iGuess[i]);
                        b = true;
                    }
                    else {
                        System.out.println("Invalid guess");
                    }
                }
            }
            else if(!((PlayGameUseCaseController)pg).isCodebreakerHuman()) {
                ((PlayGameUseCaseController)pg).cpuAttempt();
            }

            ((PlayGameUseCaseController)pg).generateKeyPegs();
            updateElements();
            isRoundFinished = ((PlayGameUseCaseController)pg).isRoundFinished();
        }

        Integer[] pattern = ((PlayGameUseCaseController)pg).getPatternColor();
        int patternLength = ((PlayGameUseCaseController)pg).getPatternLength();

        switch (isRoundFinished) {
            case 1: System.out.println ("CODEBREAKER WINS THIS ROUND!");
                    System.out.print("Pattern: ");
                    for (int i = 0; i < patternLength; i++) {
                        System.out.print(pattern[i]);
                    }
                    System.out.println();
                break;
            case 2: System.out.println ("CODEMAKER WINS THIS ROUND!");
                    System.out.print("Pattern: ");
                    for (int i = 0; i < patternLength; i++) {
                        System.out.print(pattern[i]);
                    }
                    System.out.println();
                break;
        }
        ((PlayGameUseCaseController)pg).closeRound();
    }

    private static void definePlayer() throws IOException
    {
        String name = "";

        int score = ((PlayGameUseCaseController)pg).getScore(1);

        if (pg.entersRanking(score))
        {
            while (name.isEmpty())
            {
                System.out.println("Insert player1 name:");
                name = br.readLine();
            }
            pg.updateRanking(name, score);
        }

        score = ((PlayGameUseCaseController)pg).getScore(2);

        if (pg.entersRanking(score))
        {
            name = "";

            while (name.isEmpty())  {
                System.out.println("Insert player2 name:");
                name = br.readLine();
            }

            pg.updateRanking(name, score);
        }
        
        showRanking();

    }

    private static void showRanking()
    {
        ShowRankingUseCaseController sr = new ShowRankingUseCaseController();
        List<String> names = new ArrayList();
        List<Integer> scores = new ArrayList();

        sr.getRanking(names, scores);

        System.out.println("Ranking");
        for (int i = 0; i < names.size() && i < scores.size(); i++)
        {
            System.out.println((i+1) + ". " + names.get(i) + " " + scores.get(i));
        }
    }

    private static void loadGame() throws IOException
    {
        String name = "";
        br = new BufferedReader(new InputStreamReader(System.in));
        LoadGameUseCaseController lg = new LoadGameUseCaseController();

        System.out.println("Insert de name of the game to load");
        name = br.readLine();

        int control = lg.loadGame("C:\\Documents and Settings\\Ori\\Escritorio\\games\\" + name + ".sav");
        if (control == -1)
            System.out.println("load game error");
        updateElements();
        playRound();
        playGame();

    }

    private static void saveGame() throws IOException
    {

        SaveGameUseCaseController sg = new SaveGameUseCaseController();
        br = new BufferedReader(new InputStreamReader(System.in));

        String name = "";
        while (name.compareTo("") == 0)
        {
            System.out.println("Insert the name");
            name = br.readLine();
        }

        sg.saveGame("C:\\Documents and Settings\\Ori\\Escritorio\\games", name);

    }

    private static void giveHint()
    {
        int patternToShow;
        pg = new PlayGameUseCaseController();
        patternToShow = pg.giveHint();

        if (patternToShow == -1) {
           System.out.println("A hint is already given");
        }
        else {
            patternVisibility[patternToShow] = patternColor[patternToShow];
        }

        showElements();

    }

    private static void exitGame()
    {
        System.exit(1);
    }

    private static void showElements()
    {
        int columns = ((PlayGameUseCaseController)pg).getColumns();

        System.out.print("Pattern: ");
        for (int i = 0; i < columns; i++)
            System.out.print(patternVisibility[i] + " ");

        System.out.println();
     
        System.out.println("CodePegs      keyPegs");
        for (int i = 9; i > -1; i--) {
            for (int j = 0; j < columns; j++)
                System.out.print(board[i][j]);
            System.out.print("          ");
            for (int j = 0; j < columns; j++)
                System.out.print(keyPegs[i][j] + " ");
            System.out.println();
        }    
    }

    private static void initializeValues()
    {

        int columns = ((PlayGameUseCaseController)pg).getColumns();
        board = new Integer[10][columns];
        patternColor = new Integer[columns];
        patternVisibility = new Integer[columns];
        keyPegs = new KeyPeg[10][columns];


        for (int i = 0; i < 10; i++)
            for (int j = 0; j < columns; j++)
                board[i][j] = new Integer(0);

        for (int i = 0; i < columns; i++)
            patternVisibility[i] = new Integer(0);

        for (int i = 0; i < 10; i++)
            for (int j = 0; j < columns; j++)
                keyPegs[i][j] = KeyPeg.VOID;
    }

    private static void updateElements() {

        pg = new PlayGameUseCaseController();
        int columns = ((PlayGameUseCaseController)pg).getColumns();

        board = ((PlayGameUseCaseController)pg).getBoard();
        keyPegs = ((PlayGameUseCaseController)pg).getKeyPegs();
        Boolean[] patternGuessB = ((PlayGameUseCaseController)pg).getPatternVisibility();
        patternColor = ((PlayGameUseCaseController)pg).getPatternColor();
        patternVisibility = new Integer[columns];

        for (int i = 0; i < patternGuessB.length; i++)
        {
            if (patternGuessB[i] == Boolean.TRUE)
                patternVisibility[i] = patternColor[i];
            else
                patternVisibility[i] = 0;
        }
    }

    private static void askForSave() throws IOException {

            String answer = "";

            while ((answer.compareTo("y") != 0) && (answer.compareTo("n") != 0))
            {
                System.out.println("Do you want to save the game? (y/n)");
                answer = br.readLine();
            }

            if (answer.compareTo("y") == 0)
            {
                saveGame();
            }
    }

    private static void askForAHint() throws IOException {

            String answer = "";

            while ((answer.compareTo("y") != 0) && (answer.compareTo("n") != 0))
            {
                System.out.println("If you ask for a hint, you'll not be able to appear on the ranking");
                System.out.println("Do you really want a hint? (y/n)");
                answer = br.readLine();
            }

            if (answer.compareTo("y") == 0)
            {
                giveHint();
            }
    }
}