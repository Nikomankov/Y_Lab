package Game;

public class GameResult {
    private final String draw = "Draw!";
    private Player winner;
    private String result;
    private Game.WinStatus winStatus;

    public GameResult(Game.WinStatus winStatus){
        if (winStatus.equals(Game.WinStatus.DRAW)){
            result = draw;
            this.winStatus = winStatus;
        }
    }

    public GameResult(Game.WinStatus winStatus, Player player){
        winner = player;
        if(winStatus.equals(Game.WinStatus.FIRST) || winStatus.equals(Game.WinStatus.SECOND)){
            result = winner.getName() + " winner!";
            this.winStatus = winStatus;
        }
    }

    public Player getWinner(){
        return winner;
    }

    public String getResult(){
        return result;
    }

    public Game.WinStatus getWinStatus(){
        return this.winStatus;
    }
}
