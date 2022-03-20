package Parser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class ParserJSON implements Parser{

    //JSON Simple
    public ParserJSON(File file){
        JSONParser parser = new JSONParser();
        try(Reader reader = new FileReader(file.getName())){
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray steps = (JSONArray) jsonObject.get("Step");
            JSONObject step = (JSONObject) parser.parse(String.valueOf(steps.get(1)));
            int num = Integer.parseInt(step.get("_num").toString());
            int id = Integer.parseInt(step.get("_id").toString());
            int line = Integer.parseInt(step.get("_line").toString());
            int column = Integer.parseInt(step.get("_column").toString());
            System.out.println("num = " + num +
                    "id = " + id +
                    "line = " + line +
                    "column = " + column);
//            System.out.println(steps.get(1));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int[][] getSteps() {
        return new int[0][];
    }

    @Override
    public String getResults() {
        return null;
    }
}
