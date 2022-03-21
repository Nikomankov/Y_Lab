package Recorder;

import Game.Game;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RecorderJSON implements Recorder {
    private JSONObject root;
    private JSONObject gameplay;
    private JSONArray playersArr;
    private JSONArray stepsArr;
    private JSONObject result;
    private String player1;
    private String player2;
    private String filename;

    public RecorderJSON(String player1, String player2, String filename){
        this.player1 = player1;
        this.player2 = player2;
        this.filename = filename;
        root = new JSONObject();
        gameplay = new JSONObject();
        playersArr = new JSONArray();
        playersArr.add(playerConvert(1));
        playersArr.add(playerConvert(2));
        stepsArr = new JSONArray();
    }

    @Override
    public void stepRecord(int counter, int playerId, int line, int column) {
        JSONObject step = new JSONObject();
        step.put("_num", counter);
        step.put("_playerId", playerId);
        step.put("_line", line);
        step.put("_column", column);
        stepsArr.add(step);
    }

    @Override
    public void resultRecord(Game.WinStatus winStatus) {
        result = new JSONObject();
        switch (winStatus){
            case DRAW ->{
                JSONParser parser = new JSONParser();
                try {
                    result = (JSONObject) parser.parse("Draw!");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            case FIRST -> result.put("Player",playerConvert(1));
            case SECOND -> result.put("Player",playerConvert(2));
        }
    }
    @Override
    public void closeRecord(){
        JSONObject steps = new JSONObject();
        steps.put("Step",stepsArr);
        gameplay.put("Player",playersArr);
        gameplay.put("Game",steps);
        gameplay.put("GameResult",result);
        root.put("Gameplay", gameplay);
        try {
            Files.write(Paths.get(filename), root.toJSONString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONObject playerConvert(int playerId){
        JSONObject player = new JSONObject();
        player.put("id", 1);
        player.put("name", (playerId == 1 ? player1 : player2));
        player.put("symbol", (playerId == 1 ? "X" : "0"));
        return player;
    }
}
