package Recorder_and_parsers_tests;

import Game.Game;
import Game.GameResult;
import Game.Player;
import Game.Step;
import Parser.ParserXML;
import Recorder.RecorderXML;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class XMLRecordersAndParsersTest extends TestCase {
    Player player1;
    Player player2;
    File file;
    String filePath = ".\\src\\test\\java\\Recorder_and_parsers_tests\\testXML.xml";
    RecorderXML recorderXML;
    String expectedRecord;
    ParserXML parserXML;

    @Override
    public void setUp(){
        player1 = new Player(1,"player1","X");
        player2 = new Player(2,"player2","0");
        file = new File(filePath);
        recorderXML = new RecorderXML(player1,player2,file);
        Step step = new Step(1,1,2,3);
        recorderXML.stepRecord(step);
        GameResult gameResult = new GameResult(Game.WinStatus.FIRST,player1);
        recorderXML.resultRecord(gameResult);
        recorderXML.closeRecord();
        expectedRecord = "<?xmlversion=\"1.0\"encoding=\"UTF-8\"standalone=\"no\"?>" +
                "<Gameplay>" +
                "<Playerid=\"1\"name=\"player1\"symbol=\"X\"/>" +
                "<Playerid=\"2\"name=\"player2\"symbol=\"0\"/>" +
                "<Game>" +
                "<stepnum=\"1\"playerId=\"1\">line=2,column=3</step>" +
                "</Game>" +
                "<GameResult>" +
                "<Playerid=\"1\"name=\"player1\"symbol=\"X\"/>" +
                "</GameResult>" +
                "</Gameplay>";
        parserXML = new ParserXML(file);
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
        System.out.println(actual);
        assertEquals(expectedRecord,actual);
    }

    @Test
    public void testGetSteps(){
        Step[] stepsTest = new Step[1];
        stepsTest[0] = new Step(1,1,2,3);
        Step[] steps = parserXML.getSteps();
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
        String actual = parserXML.getResults();
        assertEquals(expected, actual);
    }

}
