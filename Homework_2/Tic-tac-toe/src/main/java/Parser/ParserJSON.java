package Parser;

import Game.Step;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class ParserJSON implements Parser{
    private JSONParser parser;
    private JSONObject gameplay;
    private JSONArray steps;

    //JSON Simple
    public ParserJSON(File file){
        parser = new JSONParser();
        try(Reader reader = new FileReader(file)){
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            gameplay = (JSONObject) jsonObject.get("Gameplay");
            JSONObject game = (JSONObject) gameplay.get("Game");
            steps = (JSONArray) game.get("Step");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Step[] getSteps() {
        Step[] stepsArray = new Step[steps.size()];
        for(int i = 0; i < steps.size(); i++){
            JSONObject step = (JSONObject) steps.get(i);
            stepsArray[i] = new Step(i+1,
                    (i % 2 == 1 ? 2 : 1),
                    Integer.parseInt(step.get("_line").toString()),
                    Integer.parseInt(step.get("_column").toString()));
        }
        return stepsArray;
    }

    @Override
    public String getResults() {

        if(gameplay.get("GameResult").toString().equals("Draw!")){
            return "Draw!";
        }
        JSONObject result = (JSONObject) gameplay.get("GameResult");
        JSONObject winner = (JSONObject) result.get("Player");
        return ("Player " + winner.get("_id") + " -> " +
                winner.get("_name") + " winner as '" +
                winner.get("_symbol") + "'!");
    }
}
