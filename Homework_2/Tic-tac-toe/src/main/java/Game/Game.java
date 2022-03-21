package Game;

import Exceptions.IllegalPositionException;
import Exceptions.IllegalValuesException;
import Exceptions.JSONMissingException;
import Exceptions.XMLMissingException;
import Parser.ParserJSON;
import Parser.ParserXML;
import Recorder.RecorderJSON;
import Recorder.RecorderXML;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Game{
    private final String GAME_RULES = "Game.Game rules:" +
            "\n1. The player with \"X\" always goes first" +
            "\n2. To go, you must enter the coordinates where you want to go in the " +
            "\n   format [line number, column number]." +
            "\n3. It is forbidden to put your symbol on top of the opponent's symbol." +
            "\n4. The winner is the one who first builds a horizontal, vertical or " +
            "\n   diagonal line of 3 of his symbols.";
    private String player1 = "Player 1";
    private String player2 = "Player 2";
    private char[][] gameField;
    public enum WinStatus {FIRST, SECOND, DRAW}
    private File rating;
    private File xml;
    private File json;
    private String txtPath = "Homework_2\\Tic-tac-toe\\Rating.txt";
    private String xmlPath = "Homework_2\\Tic-tac-toe\\gameXML.xml";
    private String jsonPath = "Homework_2\\Tic-tac-toe\\gameJSON.json";
    private RecorderXML recorderXML;
    private RecorderJSON recorderJSON;

    public Game(){
        System.out.println(GAME_RULES);
        this.gameField = new char[3][3];
        createField();
        this.xml = new File(xmlPath);
        this.json = new File(jsonPath);
        System.out.println("XML = " + xml.getAbsolutePath());
        System.out.println("XML = " + xml.getName());

        //Rating
        this.rating = new File(txtPath);
        try {
            rating.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //XML DOM record
        this.recorderXML = new RecorderXML(player1, player2,xml);
        //JSON.simple record
        this.recorderJSON = new RecorderJSON(player1,player2,jsonPath);
    }

    public Game(String player1, String player2){
        System.out.println(GAME_RULES);
        this.player1 = player1;
        this.player2 = player2;
        this.gameField = new char[3][3];
        createField();
        this.xml = new File(xmlPath);
        this.json = new File(jsonPath);

        //Rating
        this.rating = new File(txtPath);
        try {
            rating.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //XML DOM record
        this.recorderXML = new RecorderXML(player1, player2,xml);
        //JSON.simple record
        this.recorderJSON = new RecorderJSON(player1,player2,jsonPath);
    }

    private void createField(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                this.gameField[i][j] = '-';
            }
        }
    }
    public void printField(){
        System.out.println("    1   2   3");
        for(int i = 0; i < 3; i++){
            System.out.print(i+1 + " ");
            for(int j = 0; j < 3; j++){
                if(j == 0) System.out.print("|");
                System.out.print(" " + gameField[i][j] + " |");
            }
            System.out.println();
        }
    }

    private boolean checkEnterMove(int line, int column){
        try{
            if(line > -1 & line < 3 & column > -1 & column < 3){
                try {
                    if(gameField[line][column] == '-'){
                        return true;
                    } else {
                        throw new IllegalPositionException();
                    }
                } catch (IllegalPositionException e1){
                    e1.printStackTrace();
                }
            } else {
                throw new IllegalValuesException();
            }
        } catch (IllegalValuesException e2){
            e2.printStackTrace();
        }
        return false;
    }

    public void step(int player, int line, int column){
        switch (player) {
            case 1 -> gameField[line][column] = 'X';
            case 2 -> gameField[line][column] = '0';
        }
    }

    private boolean lineСheck(int player, int line, int column){
        char c = player == 1 ? 'X' : '0';
        int diagCounter1 = 0;
        int diagCounter2 = 0;
        int horizontCounter = 0;
        int verticalCounter = 0;

        for(int i = 0; i < 3; i++){
            diagCounter1 += gameField[i][i] == c ? 1 : 0;
            diagCounter2 += gameField[i][2-i] == c ? 1 : 0;
            horizontCounter += gameField[line][i] == c ? 1 : 0;
            verticalCounter += gameField[i][column] == c ? 1 : 0;
        }
        return diagCounter1 == 3 | diagCounter2 == 3 | horizontCounter == 3 | verticalCounter == 3;
    }

    public void playGame(){
        Scanner scanner = new Scanner(System.in);
        int counter = 1;
        int line;
        int column;
        boolean win = false;
        while(counter < 10 & !win) {
            int playerIndex = counter % 2 == 1 ? 1 : 2;
            printField();

            //player moves
            System.out.print("player " + (playerIndex == 1 ? player1 : player2) +
                    "\nEnter your move: line = ");
            line = scanner.nextInt() - 1;
            System.out.print("column = ");
            column = scanner.nextInt() - 1;

            if (!checkEnterMove(line, column)) {
                continue;
            }

            switch (playerIndex) {
                case 1 -> {
                    step(1, line, column);
                    recorderXML.stepRecord(counter,1,line+1,column+1);
                    recorderJSON.stepRecord(counter,1,line+1,column+1);
                }
                case 2 -> {
                    step(2, line, column);
                    recorderXML.stepRecord(counter,2,line+1,column+1);
                    recorderJSON.stepRecord(counter,2,line+1,column+1);
                }
            }

            //winner check
            win = lineСheck(playerIndex, line, column);
            if (win) {
                switch (playerIndex) {
                    case 1 -> {
                        System.out.println("Congratulation! " + player1 + " WIN!");
                        ratingEntry(WinStatus.FIRST);
                        recorderXML.resultRecord(WinStatus.FIRST);
                        recorderJSON.resultRecord(WinStatus.FIRST);
                    }
                    case 2 -> {
                        System.out.println("Congratulation! " + player2 + " WIN!");
                        ratingEntry(WinStatus.SECOND);
                        recorderXML.resultRecord(WinStatus.SECOND);
                        recorderJSON.resultRecord(WinStatus.SECOND);
                    }
                }
            }
            counter++;
            if(counter == 10 & !win){
                System.out.println("Draw!");
                ratingEntry(WinStatus.DRAW);
                recorderXML.resultRecord(WinStatus.DRAW);
                recorderJSON.resultRecord(WinStatus.DRAW);
            }
        }
        recorderXML.closeRecord();
        recorderJSON.closeRecord();
    }

    //----------------------------------------------
    //Rating to txt
    private void ratingEntry(WinStatus winStatus){
        try {
            FileWriter fileWriter = new FileWriter(rating,true);
            if(rating.length() == 0){
                fileWriter.append("Rating list:\n");
            }
            switch (winStatus){
                case DRAW -> fileWriter.append(player1 + " & " + player2 + " draw!\n");
                case FIRST -> fileWriter.append(player1 + " winner!\n");
                case SECOND -> fileWriter.append(player2 + " winner!\n");
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------
    //JSON simple readers
    public void readFromJSON(){
        try {
            if(!json.exists()){
                throw new JSONMissingException();
            } else {
                ParserJSON parser = new ParserJSON(json);
                int[][] stepsArray = parser.getSteps();
                for(int i = 0; i < stepsArray[0].length; i++){
                    step(i%2 == 1 ? 1 : 2, stepsArray[0][i], stepsArray[1][i]);
                    printField();
                    System.out.println();
                }
                System.out.println(parser.getResults());
            }
        } catch (JSONMissingException e){
            e.printStackTrace();
        }
    }

    //XML DOM reader
     public void readFromXML(){
        try {
            if(!xml.exists()){
                throw new XMLMissingException();
            } else {
                ParserXML parser = new ParserXML(xml);
                int[][] stepsArray = parser.getSteps();
                for(int i = 0; i < stepsArray[0].length; i++){
                    step(i%2 == 1 ? 1 : 2, stepsArray[0][i], stepsArray[1][i]);
                    printField();
                    System.out.println();
                }
                System.out.println(parser.getResults());
            }
        } catch (XMLMissingException e){
            e.printStackTrace();
        }
     }
}


