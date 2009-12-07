/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import Enum.DifficultyLevel;
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
 * @author Ori
 */
public class main
{

    private static BufferedReader br;
    private static Integer[][] board = new Integer[10][4];
    private static Integer[] patternGuess = new Integer[4];
    private static Integer[][] keyPegs = new Integer[10][4];

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

        System.out.println("vs CPU? " + bVsCpu);

        s.setSettings(DifficultyLevel.getFromString(difficulty), bVsCpu);

        playGame();
    }
    
    private static void insertPattern() throws IOException 
    {
        GenericPlayGameUCC pg;
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

        PlayGameUseCaseController pg = new PlayGameUseCaseController();

        int currentRound;

        while (!pg.isGameFinished())
        {
            currentRound = pg.getCurrentRound();
            System.out.println("Current round: " + currentRound);
            initializeValues();
            playRound();
        }

        System.out.println("GAME FINISHED!!");
        int p1points = ((PlayGameUseCaseController)pg).getScore(1);
        System.out.println("player1 points: " + p1points);
        int p2points = ((PlayGameUseCaseController)pg).getScore(2);
        System.out.println("player2 points: " + p2points);

        definePlayer();

    }

    private static void playRound() throws IOException
    {

        GenericPlayGameUCC pg = new PlayGameUseCaseController();
        DifficultyLevel dl = pg.getLevel();
        Boolean b = false;

        System.out.println("Is codemaker human?: " + ((PlayGameUseCaseController)pg).isCodemakerHuman());
        System.out.println("Is codebreaker human?: " + ((PlayGameUseCaseController)pg).isCodebreakerHuman());

        if (((PlayGameUseCaseController)pg).isCodemakerHuman())
        {
            insertPattern();
        }
        else
        {
            ((PlayGameUseCaseController)pg).generatePattern();
        }

        while (!((PlayGameUseCaseController)pg).isRoundFinished())
        {
            /*
            String answer = "";

            while ((answer.compareTo("y") != 0) && (answer.compareTo("n") != 0))
            {
                System.out.println("Do you want to save a game? (y/n)");
                answer = br.readLine();
            }

            if (answer.compareTo("y") == 0)
            {
                saveGame();
            }

            answer = "";

            while ((answer.compareTo("y") != 0) && (answer.compareTo("n") != 0))
            {
                System.out.println("Do you want a hint? (y/n)");
                answer = br.readLine();
            }

            if (answer.compareTo("y") == 0)
            {
                giveHint();
            }
            */
            String guess = "";
            int attempt = ((PlayGameUseCaseController)pg).getCurrentRow();

            System.out.println("Attempt: " + (attempt+1));

            if (((PlayGameUseCaseController)pg).isCodebreakerHuman())
            {
                b = false;
                while (!b)
                {
                    System.out.println("Please, insert the guess");
                    guess = br.readLine();

                    Integer[] iGuess = new Integer[guess.length()];
                    for(int i = 0; i < guess.length(); i++)
                        iGuess[i] = Integer.valueOf(guess.substring(i,i+1));

                    if (dl ==  DifficultyLevel.Easy && guess.length() == 4)
                    {
                        for (int i = 0; i < iGuess.length; i++)
                            ((PlayGameUseCaseController)pg).setCell(attempt, i, iGuess[i]);
                        b = true;
                    }
                    else if (dl ==  DifficultyLevel.Normal && iGuess.length == 5)
                    {
                        for (int i = 0; i < iGuess.length; i++)
                            ((PlayGameUseCaseController)pg).setCell(attempt, i, iGuess[i]);
                        b = true;
                    }
                    else if (dl ==  DifficultyLevel.Hard && iGuess.length == 6)
                    {
                        for (int i = 0; i < iGuess.length; i++)
                            ((PlayGameUseCaseController)pg).setCell(attempt, i, iGuess[i]);
                        b = true;
                    }
                    else
                    {
                        System.out.println("Invalid guess");
                    }
                }

            }
            else if(!((PlayGameUseCaseController)pg).isCodebreakerHuman())
            {
                ((PlayGameUseCaseController)pg).cpuAttempt();
                /*Integer[] codePegRow = ((PlayGameUseCaseController)pg).getCodePegRow(((PlayGameUseCaseController)pg).getCodePegsLastRowNumber());
                System.out.print("CodePegs cpu: ");
                for (int i = 0; i < codePegRow.length; i++)
                {
                    System.out.print(codePegRow[i]);
                }
                System.out.println();*/
            }

            board[((PlayGameUseCaseController)pg).getCodePegsLastRowNumber()] = ((PlayGameUseCaseController)pg).getCodePegRow(((PlayGameUseCaseController)pg).getCodePegsLastRowNumber());

            ((PlayGameUseCaseController)pg).generateKeyPegs();
            Integer[] keyPegsRow = ((PlayGameUseCaseController)pg).getKeyPegsRow(((PlayGameUseCaseController)pg).getCodePegsLastRowNumber());
            int lastRow = ((PlayGameUseCaseController)pg).getCodePegsLastRowNumber();

            for (int i = 0; i < 4; i++)
                keyPegs[lastRow][i] = keyPegsRow[i];
            showElements();
        }
        ((PlayGameUseCaseController)pg).closeRound();
    }

    private static void definePlayer() throws IOException
    {
        String name = "";
        PlayGameUseCaseController pg = new PlayGameUseCaseController();

        int score = pg.getScore(1);

        if (pg.entersRanking(score))
        {
            while (name.isEmpty())
            {
                System.out.println("Insert player1 name:");
                name = br.readLine();
            }
            pg.updateRanking(name, score);
        }

        if (!pg.getVsCpu())
        {
            score = pg.getScore(2);

            if (pg.entersRanking(score))
            {
                name = "";

                while (name.isEmpty())
                {
                    System.out.println("Insert player2 name:");
                    name = br.readLine();
                }

                pg.updateRanking(name, score); 
            }
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
        PlayGameUseCaseController pg = new PlayGameUseCaseController();
        pg.giveHint();
        patternGuess = ((PlayGameUseCaseController)pg).getPatternColor();
    }

    private static void exitGame()
    {
        System.exit(1);
    }

    private static void showElements()
    {
        System.out.print("Pattern: ");
        for (int i = 0; i < 4; i++)
            System.out.print(patternGuess[i] + " ");

        System.out.println();
     
        System.out.println("CodePegs      keyPegs");
        for (int i = 9; i > -1; i--) {
            for (int j = 0; j < 4; j++)
                System.out.print(board[i][j]);
            System.out.print("          ");
            for (int j = 0; j < 4; j++)
                System.out.print(keyPegs[i][j]);
            System.out.println();
        }    
    }

    private static void initializeValues()
    {
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 4; j++)
                board[i][j] = new Integer(0);

        for (int i = 0; i < 4; i++)
            patternGuess[i] = new Integer(0);

        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 4; j++)
                keyPegs[i][j] = new Integer(0);
    }

    private static void updateElements() {

        PlayGameUseCaseController pg = new PlayGameUseCaseController();

        board = ((PlayGameUseCaseController)pg).getBoard();
        keyPegs = ((PlayGameUseCaseController)pg).getKeyPegs();
        Boolean[] patternGuessB = ((PlayGameUseCaseController)pg).getPatternVisibility();
        Integer[] patternColor = ((PlayGameUseCaseController)pg).getPatternColor();

        for (int i = 0; i < patternGuessB.length; i++)
        {
            if (patternGuessB[i] == Boolean.TRUE)
                patternGuess[i] = patternColor[i];
            else
                patternGuess[i] = 0;
        }
    }
}