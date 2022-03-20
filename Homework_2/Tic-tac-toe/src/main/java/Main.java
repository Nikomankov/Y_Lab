import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final String GREETING = "This is a game of tic-tac-toe. Please enter players names:";

//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println(GREETING);
//        System.out.print("Player 1 (X): ");
//        String player1 = scanner.nextLine();
//        System.out.print("Player 2 (0): ");
//        String player2 = scanner.nextLine();
//
//        Game game1 = new Game(player1,player2);
//        game1.playGame();

        Game game = new Game();
//        game.playGame();
        game.readFromXML();
    }
}

//my exceptions
class IllegalPositionException extends Exception{
    public IllegalPositionException(){
        super("This position is taken, please select another");
    }
}
class IllegalValuesException extends Exception{
    public IllegalValuesException(){
        super("The set values go beyond the boundaries of the playing field. 0 < values < 4");
    }
}
class XMLMissingException extends Exception{
    public XMLMissingException() {
        super("XML file is missing");
    }
}