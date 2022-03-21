package Recorder;
import Game.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class RecorderXML implements Recorder {
    private Document document;
    private String player1;
    private String player2;
    private Element gameplay;
    private Element game;
    private File file;

    public RecorderXML(String player1, String player2, File file){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            this.document = builder.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        this.file = file;
        this.player1 = player1;
        this.player2 = player2;
        this.gameplay = document.createElement("Gameplay");
        document.appendChild(gameplay);
        gameplay.appendChild(playerConvert(1));
        gameplay.appendChild(playerConvert(2));
        this.game = document.createElement("Game");
        gameplay.appendChild(game);
    }

    @Override
    public void stepRecord(int counter, int playerId, int line, int column){
        Element step = document.createElement("step");
        step.setAttribute("num", String.valueOf(counter));
        step.setAttribute("playerId", String.valueOf(playerId));
        step.setTextContent("line = " + line + ", column = " + column);
        game.appendChild(step);
    }
    @Override
    public void resultRecord(Game.WinStatus winStatus){
        Element result = document.createElement("GameResult");
        switch (winStatus){
            case DRAW -> result.setTextContent("Draw!");
            case FIRST -> result.appendChild(playerConvert(1));
            case SECOND -> result.appendChild(playerConvert(2));
        }
        gameplay.appendChild(result);
    }
    @Override
    public void closeRecord(){
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource();
            StreamResult streamRes = new StreamResult(file);
            transformer.transform(source, streamRes);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private Element playerConvert(int playerId){
        Element player = document.createElement("Player");
        player.setAttribute("id",String.valueOf(playerId));
        player.setAttribute("name", playerId == 1 ? player1 : player2);
        player.setAttribute("symbol", playerId == 1 ? "X" : "0");
        return player;
    }
}
