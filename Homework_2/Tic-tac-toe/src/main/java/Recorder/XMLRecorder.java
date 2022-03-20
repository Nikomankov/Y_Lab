package Recorder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLRecorder implements Recorder{
    private Document document;
    private String player1;
    private String player2;
    private Element gameplay;
    private Element game;

    public XMLRecorder(String player1, String player2){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            this.document = builder.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        this.player1 = player1;
        this.player2 = player2;
        this.gameplay = document.createElement("Gameplay");
        document.appendChild(gameplay);
        gameplay.appendChild(playerToXml(1));
        gameplay.appendChild(playerToXml(2));
        this.game = document.createElement("Game");
        gameplay.appendChild(game);
    }

    @Override
    public void stepRecord(int counter, int id, int line, int column){
        game.appendChild(stepToXML(counter, 1, line + 1, column + 1));
    }

    @Override
    public void resultRecord(Game.WinStatus winStatus){
        gameplay.appendChild(winStatusToXML(winStatus));
    }

    private Element playerToXml(int playerIndex){
        Element player = document.createElement("Player");
        player.setAttribute("id",String.valueOf(playerIndex));
        player.setAttribute("name", playerIndex == 1 ? player1 : player2);
        player.setAttribute("symbol", playerIndex == 1 ? "X" : "0");
        return player;
    }
    private Element stepToXML(int counter, int player, int line, int column){
        Element step = document.createElement("step");
        step.setAttribute("num", String.valueOf(counter));
        step.setAttribute("playerId", String.valueOf(player));
        step.setTextContent("line = " + line + ", column = " + column);
        return step;
    }
    private Element winStatusToXML(Game.WinStatus winStatus){
        Element gameResult = document.createElement("GameResult");
        switch (winStatus){
            case DRAW -> gameResult.setTextContent("Draw!");
            case FIRST -> gameResult.appendChild(playerToXml(1));
            case SECOND -> gameResult.appendChild(playerToXml(2));
        }
        return gameResult;
    }


}
