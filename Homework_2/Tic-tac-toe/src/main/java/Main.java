import Game.Game;

public class Main {
    public static void main(String[] args) {
//        final String GREETING = "This is a game of tic-tac-toe. Please enter players names:";
//        Scanner scanner = new Scanner(System.in);
//        System.out.println(GREETING);
//        System.out.print("Player 1 (X): ");
//        String player1 = scanner.nextLine();
//        System.out.print("Player 2 (0): ");
//        String player2 = scanner.nextLine();
//        Game.Game game1 = new Game.Game(player1,player2);
//        game1.playGame();

//----------------------------------------------------------------

        Game game = new Game();
        game.playGame();
        game.readFromJSON();
        game.readFromXML();
    }
}


