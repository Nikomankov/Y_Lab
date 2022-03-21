package Recorder;

import Game.Game;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RecorderJSON implements Recorder {
    private JSONObject root;
    private JSONArray gameplayArr;
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
        this.root = new JSONObject();
        this.gameplayArr = new JSONArray();
        this.playersArr = new JSONArray();
        playersArr.add(playerConvert(1));
        playersArr.add(playerConvert(2));
        this.stepsArr = new JSONArray();
        this.result = new JSONObject();
    }

    @Override
    public void stepRecord(int counter, int playerId, int line, int column) {
        JSONObject step = new JSONObject();
        step.put("num", counter);
        step.put("playerId", playerId);
        step.put("line", line);
        step.put("column", column);
        stepsArr.add(step);
    }

    @Override
    public void resultRecord(Game.WinStatus winStatus) {

        switch (winStatus){
            case DRAW ->{
                resultObj.put("GameResult", "Draw");
            }
            case FIRST -> {
                result.put("Player", playerConvert(1));
                resultObj.put("GameResult", result);
            }
            case SECOND -> {
                result.put("Player", playerConvert(2));
                resultObj.put("GameResult", result);
            }
        }
    }
    @Override
    public void closeRecord(){
        playersArr.
        gameplay.put()
        root.put("Gameplay", gameplayArr);
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
