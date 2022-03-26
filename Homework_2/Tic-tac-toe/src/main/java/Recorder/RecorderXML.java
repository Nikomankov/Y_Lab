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
    private Player player1;
    private Player player2;
    private Element gameplay;
    private Element game;
    private File file;

    public RecorderXML(Player player1, Player player2, File file){
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
        gameplay = document.createElement("Gameplay");
        document.appendChild(gameplay);
        gameplay.appendChild(playerConvert(player1));
        gameplay.appendChild(playerConvert(player2));
        game = document.createElement("Game");
        gameplay.appendChild(game);
    }

    @Override
    public void stepRecord(Step step){
        Element stepXML = document.createElement("step");
        stepXML.setAttribute("num", String.valueOf(step.getNum()));
        stepXML.setAttribute("playerId", String.valueOf(step.getId()));
        stepXML.setTextContent("line = " + step.getLine() + ", column = " + step.getColumn());
        game.appendChild(stepXML);
    }
    @Override
    public void resultRecord(GameResult gameResult){
        Element result = document.createElement("GameResult");
        if(gameResult.getWinStatus().equals(Game.WinStatus.DRAW)){
            result.setTextContent("Draw!");
        } else {
            result.appendChild(playerConvert(gameResult.getWinner()));
        }
        gameplay.appendChild(result);
    }
    @Override
    public void closeRecord(){
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult streamRes = new StreamResult(file);
            transformer.transform(source, streamRes);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    private Element playerConvert(Player player){
        Element playerXML = document.createElement("Player");
        playerXML.setAttribute("id", String.valueOf(player.getId()));
        playerXML.setAttribute("name", player.getName());
        playerXML.setAttribute("symbol", player.getSymbol());
        return playerXML;
    }
}
