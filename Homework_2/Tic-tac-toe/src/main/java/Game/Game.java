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
    private Player player1 = new Player(1,"Player1","X");
    private Player player2 = new Player(2,"Player2","0");
    private GameResult gameResult;
    private String[][] gameField;
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
        this.gameField = new String[3][3];
        createField();
        //Rating
        this.rating = new File(txtPath);
        //XML DOM record
        this.xml = new File(xmlPath);
        this.recorderXML = new RecorderXML(player1, player2,xml);
        //JSON Jackson record
        this.json = new File(jsonPath);
        this.recorderJSON = new RecorderJSON(player1,player2,json);
    }

    public Game(String player1, String player2){
        System.out.println(GAME_RULES);
        this.player1 = new Player(1,player1,"X");
        this.player2 = new Player(2,player2,"0");
        this.gameField = new String[3][3];
        createField();
        //Rating
        this.rating = new File(txtPath);
        //XML DOM record
        this.xml = new File(xmlPath);
        this.recorderXML = new RecorderXML(this.player1, this.player2,xml);
        //JSON Jackson record
        this.json = new File(jsonPath);
        this.recorderJSON = new RecorderJSON(this.player1, this.player2,json);
    }

    private void createField(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                this.gameField[i][j] = "-";
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

    private boolean checkEnterMove(Step step){
        int line = step.getLine();
        int column = step.getColumn();
        try{
            if(line > -1 & line < 3 & column > -1 & column < 3){
                try {
                    if(gameField[line][column].equals("-")){
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

    public void step(Step step){
        gameField[step.getLine()][step.getColumn()] =
                step.getId() == 1 ? player1.getSymbol() : player2.getSymbol();
    }

    private boolean lineСheck(Step step){
        String c = step.getId() == 1 ? player1.getSymbol() : player2.getSymbol();
        int diagCounter1 = 0;
        int diagCounter2 = 0;
        int horizontCounter = 0;
        int verticalCounter = 0;

        for(int i = 0; i < 3; i++){
            diagCounter1 += gameField[i][i].equals(c) ? 1 : 0;
            diagCounter2 += gameField[i][2-i].equals(c) ? 1 : 0;
            horizontCounter += gameField[step.getLine()][i].equals(c) ? 1 : 0;
            verticalCounter += gameField[i][step.getColumn()].equals(c) ? 1 : 0;
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
            System.out.print("player " + (playerIndex == 1 ? player1.getName() : player2.getName()) +
                    "\nEnter your move: line = ");
            line = scanner.nextInt() - 1;
            System.out.print("column = ");
            column = scanner.nextInt() - 1;

            Step step = new Step(counter,playerIndex,line,column);
            if (!checkEnterMove(step)) {
                continue;
            }
            step(step);
            recorderXML.stepRecord(step);
            recorderJSON.stepRecord(step);

            //winner check
            win = lineСheck(step);
            if (win) {
                switch (playerIndex) {
                    case 1 -> this.gameResult = new GameResult(WinStatus.FIRST, player1);
                    case 2 -> this.gameResult = new GameResult(WinStatus.SECOND, player2);
                }
            }
            counter++;
            if(counter == 10 & !win){
                this.gameResult = new GameResult(WinStatus.DRAW);
            }
        }
        System.out.println(gameResult.getResult());
        ratingEntry();
        recorderXML.resultRecord(gameResult);
        recorderJSON.resultRecord(gameResult);

        recorderXML.closeRecord();
        recorderJSON.closeRecord();
    }

    //----------------------------------------------
    //Rating to txt
    private void ratingEntry(){
        try {
            rating.createNewFile();
            FileWriter fileWriter = new FileWriter(rating,true);
            if(rating.length() == 0){
                fileWriter.append("Rating list:\n");
            }
            fileWriter.append(gameResult.getResult());
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
                Step[] stepsArray = parser.getSteps();
                for(Step step : stepsArray){
                    step(step);
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
                Step[] stepsArray = parser.getSteps();
                for(Step step : stepsArray){
                    step(step);
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



