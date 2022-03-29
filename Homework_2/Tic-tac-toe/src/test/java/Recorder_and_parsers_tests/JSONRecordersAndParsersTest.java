package Recorder_and_parsers_tests;

import Game.Game;
import Game.GameResult;
import Game.Player;
import Game.Step;
import Parser.ParserJSON;
import Recorder.RecorderJSON;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JSONRecordersAndParsersTest extends TestCase {
    Player player1;
    Player player2;
    File file;
    String filePath = ".\\src\\test\\java\\Recorder_and_parsers_tests\\testJSON.json";
    RecorderJSON recorderJSON;
    String expectedRecord;
    ParserJSON parserJSON;

    @Override
    public void setUp(){
        player1 = new Player(1,"player1","X");
        player2 = new Player(2,"player2","0");
        file = new File(filePath);
        recorderJSON = new RecorderJSON(player1,player2,file);
        Step step = new Step(1,1,2,3);
        recorderJSON.stepRecord(step);
        GameResult gameResult = new GameResult(Game.WinStatus.FIRST,player1);
        recorderJSON.resultRecord(gameResult);
        recorderJSON.closeRecord();
        expectedRecord = "{\"Gameplay\":{" +
                "\"Player\":[{\"_id\":1,\"_name\":\"player1\",\"_symbol\":\"X\"},{\"_id\":2,\"_name\":\"player2\",\"_symbol\":\"0\"}]," +
                "\"Game\":{\"Step\":[{\"_num\":1,\"_id\":1,\"_line\":2,\"_column\":3}]}," +
                "\"GameResult\":{\"Player\":{\"_id\":1,\"_name\":\"player1\",\"_symbol\":\"X\"}}" +
                "}}";

        parserJSON = new ParserJSON(file);

    }
    @Test
    @Before
    public void testRecord(){
        String actual ="";
        try(FileReader fileReader = new FileReader(file)) {
            int c;
            while((c = fileReader.read()) != -1){
                actual += (char)c;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        actual = actual.replaceAll("\\s","");
        assertEquals(expectedRecord,actual);
    }

    @Test
    public void testGetSteps(){
        Step[] stepsTest = new Step[1];
        stepsTest[0] = new Step(1,1,2,3);
        Step[] steps = parserJSON.getSteps();
        System.out.println(stepsTest.length);
        int counter = 0;
        for (int i = 0; i < stepsTest.length; i++){
            if (stepsTest[i].getNum() == steps[i].getNum() &
                    stepsTest[i].getId() == steps[i].getId() &
                    stepsTest[i].getLine() == steps[i].getLine() &
                    stepsTest[i].getColumn() == steps[i].getColumn()){
                counter++;
            }
        }
        System.out.println(counter == stepsTest.length);
        assertTrue(counter == stepsTest.length);
    }

    @Test
    public void testGetResult(){
        String expected = "Player 1 -> player1 winner as 'X'!";
        String actual = parserJSON.getResults();
        assertEquals(expected, actual);
    }

}
