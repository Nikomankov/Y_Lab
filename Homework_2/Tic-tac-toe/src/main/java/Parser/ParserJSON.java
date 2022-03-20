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
    private JSONArray players;
    private JSONArray steps;
    private JSONObject resultObject;

    //JSON Simple
    public ParserJSON(File file){
        this.parser = new JSONParser();
        try(Reader reader = new FileReader(file)){
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray gameplay = (JSONArray) jsonObject.get("Gameplay");
            JSONObject playersObject = (JSONObject) gameplay.get(0);
            this.players = (JSONArray) playersObject.get("Players");
            JSONObject stepsObject = (JSONObject) gameplay.get(1);
            this.steps = (JSONArray) stepsObject.get("Steps");
            resultObject = (JSONObject) gameplay.get(2);
            System.out.println(resultObject);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int[][] getSteps() {
        int[][] stepsArray = new int[2][steps.size()];
        for(int i=0; i < steps.size(); i++){
            JSONObject step = (JSONObject) steps.get(i);
            stepsArray[0][i] = Integer.parseInt(step.get("line").toString()) - 1;
            stepsArray[1][i] = Integer.parseInt(step.get("column").toString()) - 1;
        }
        return stepsArray;
    }

    @Override
    public String getResults() {
        if(resultObject.get("GameResult").toString().equals("Draw!")){
            return "Draw!";
        }
        JSONArray array = (JSONArray) resultObject.get("GameResult");
        JSONObject object = (JSONObject) array.get(0);
        return ("Player " + object.get("id") + " -> " +
                object.get("name") + " winner as '" +
                object.get("symbol") + "'!");
    }
    /*
    One winner
    "GameResult": [
        {
          "id": "1",
          "name": "Momo",
          "symbol": "x"
        }
      ]

      Draw
      "GameResult":"Draw!"
     */
}
