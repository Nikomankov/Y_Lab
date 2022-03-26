package Recorder;

import Game.Game;
import Game.GameResult;
import Game.Player;
import Game.Step;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecorderJSON implements Recorder{
    private Player player1;
    private Player player2;
    private File file;
    private List<Player> players;
    private List<Step> steps;
    private Object result;

    public RecorderJSON(Player player1, Player player2, File file){
        this.player1 = player1;
        this.player2 = player2;
        this.file = file;
        players = new ArrayList<>();
        players.add(this.player1);
        players.add(this.player1);
        steps = new ArrayList<>();
    }

    @Override
    public void stepRecord(Step step) {
        steps.add(step);
    }

    @Override
    public void resultRecord(GameResult gameResult) {
        result = new HashMap<>();
        HashMap<String,Player> player = new HashMap<>();
        if(gameResult.getWinStatus().equals(Game.WinStatus.DRAW)){
            result = gameResult.getResult();
        } else {
            player.put("Player", gameResult.getWinner());
            result = player;
        }
    }

    @Override
    public void closeRecord() {
        HashMap<String,Map> root = new HashMap<>();
        HashMap<String,Object> gameplay = new HashMap<>();
        HashMap<String,List> game = new HashMap<>();
        game.put("Step",steps);
        gameplay.put("Player",players);
        gameplay.put("Game",game);
        gameplay.put("GameResult", result);
        root.put("Gameplay",gameplay);

        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file,root);
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(json);

    }
}
