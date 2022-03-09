import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Game {
    private final String player1;
    private final String player2;
    private final String GAME_RULES = "Game rules:" +
            "\n1. The player with \"X\" always goes first" +
            "\n2. To go, you must enter the coordinates where you want to go in the " +
            "\n   format [line number, column number]." +
            "\n3. It is forbidden to put your symbol on top of the opponent's symbol." +
            "\n4. The winner is the one who first builds a horizontal, vertical or " +
            "\n   diagonal line of 3 of his symbols.";
    private char[][] gameField;

    public Game(String player1, String player2){
        this.player1 = player1;
        this.player2 = player2;
        this.gameField = new char[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                this.gameField[i][j] = '-';
            }
        }
    }

    public void printRules(){
        System.out.println(GAME_RULES);
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

    public boolean checkEnterMove(int line, int column){
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

    private boolean step(int player, int line, int column){
        switch (player) {
            case 1 -> gameField[line][column] = 'X';
            case 0 -> gameField[line][column] = '0';
        }
        return true;
    }

    public void startGame(){
        try {
            FileWriter fileWriter = new FileWriter("Raiting.txt",true);

            Scanner scanner = new Scanner(System.in);
            int counter = 1;
            boolean win = false;
            String move;
            int line;
            int column;
            while(counter < 10 & !win) {
                printField();

                System.out.print("player " + (counter % 2 == 1 ? player1 : player2) +
                        "\nEnter your move: line = ");
                line = scanner.nextInt() - 1;
                System.out.print("column = ");
                column = scanner.nextInt() - 1;
                if (!checkEnterMove(line, column)) {
                    continue;
                }
                switch (counter % 2) {
                    case 1 -> step(1, line, column);
                    case 0 -> step(0, line, column);
                }
                win = lineСheck(counter % 2, line, column);
                if (win) {
                    System.out.println("Congratulation! " + (counter % 2 == 1 ? player1 : player2) + " WIN!");
                    fileWriter.append((counter % 2 == 1 ? player1 : player2) + "\n");
                    fileWriter.close();
                }
                counter++;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean lineСheck(int player, int line, int column){
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


}
