package Parser;

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
    public int[][] getSteps() {
        int[][] stepsArray = new int[2][steps.size()];
        for(int i = 0; i < steps.size(); i++){
            JSONObject step = (JSONObject) steps.get(i);
            stepsArray[0][i] = Integer.parseInt(step.get("_line").toString()) - 1;
            stepsArray[1][i] = Integer.parseInt(step.get("_column").toString()) - 1;
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
        return ("Player " + winner.get("id") + " -> " +
                winner.get("name") + " winner as '" +
                winner.get("symbol") + "'!");
    }
}
