import org.w3c.dom.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Game {
    private final String GAME_RULES = "Game rules:" +
            "\n1. The player with \"X\" always goes first" +
            "\n2. To go, you must enter the coordinates where you want to go in the " +
            "\n   format [line number, column number]." +
            "\n3. It is forbidden to put your symbol on top of the opponent's symbol." +
            "\n4. The winner is the one who first builds a horizontal, vertical or " +
            "\n   diagonal line of 3 of his symbols.";
    private String player1 = "Player 1";
    private String player2 = "Player 2";
    private char[][] gameField;

    private enum WinStatus {FIRST, SECOND, DRAW}
    private File rating;

    private File xml;
    private Document docToWrite;
//    private Document docToRead;

    public Game(){
        System.out.println(GAME_RULES);
        this.gameField = new char[3][3];
        createField();
        this.xml = new File("Homework_2\\Tic-tac-toe\\game.xml");

        //Rating
        this.rating = new File("Homework_2\\Tic-tac-toe\\Rating.txt");
        try {
            rating.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //XML DOM record
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            this.docToWrite = builder.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

    }

    public Game(String player1, String player2){
        this.player1 = player1;
        this.player2 = player2;
        System.out.println(GAME_RULES);
        this.gameField = new char[3][3];
        createField();
        this.xml = new File("Homework_2\\Tic-tac-toe\\game.xml");

        //Rating
        this.rating = new File("Homework_2\\Tic-tac-toe\\Rating.txt");
        try {
            rating.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //XML DOM record
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            this.docToWrite = builder.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    //DOM reader
    /*
    public Game(){
        this.gameField = new char[3][3];
        createField();
        this.xml = new File("Homework_2\\Tic-tac-toe\\game.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try{
            if(!xml.exists()){
                throw new XMLMissingException();
            } else {
                try {
                    builder = factory.newDocumentBuilder();
                    this.docToRead = builder.parse(xml);
                } catch (ParserConfigurationException | IOException | SAXException e) {
                    e.printStackTrace();
                }
            }
        } catch (XMLMissingException e){
            e.printStackTrace();
        }
    }
    */

    private void createField(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                this.gameField[i][j] = '-';
            }
        }
    }
    public void printField(){
        System.out.println("    1   2   3");
        for(int i = 0; i < 3; i++){
            System.out.print(i+1 + " ");
            for(int j = 0; j < 3; j++){
                if(j == 0) System.out.print("|");
                System.out.print(" " + gameField[i][j] + " |");
            }
            System.out.println();
        }
    }

    private boolean checkEnterMove(int line, int column){
        try{
            if(line > -1 & line < 3 & column > -1 & column < 3){
                try {
                    if(gameField[line][column] == '-'){
                        return true;
                    } else {
                        throw new IllegalPositionException();
                    }
                } catch (IllegalPositionException e1){
                    e1.printStackTrace();
                }
            } else {
                throw new IllegalValuesException();
            }
        } catch (IllegalValuesException e2){
            e2.printStackTrace();
        }
        return false;
    }

    private void step(int player, int line, int column){
        switch (player) {
            case 1 -> gameField[line][column] = 'X';
            case 2 -> gameField[line][column] = '0';
        }
    }

    private boolean lineСheck(int player, int line, int column){
        char c = player == 1 ? 'X' : '0';
        int diagCounter1 = 0;
        int diagCounter2 = 0;
        int horizontCounter = 0;
        int verticalCounter = 0;

        for(int i = 0; i < 3; i++){
            diagCounter1 += gameField[i][i] == c ? 1 : 0;
            diagCounter2 += gameField[i][2-i] == c ? 1 : 0;
            horizontCounter += gameField[line][i] == c ? 1 : 0;
            verticalCounter += gameField[i][column] == c ? 1 : 0;
        }
        return diagCounter1 == 3 | diagCounter2 == 3 | horizontCounter == 3 | verticalCounter == 3;
    }

    public void playGame(){
        Scanner scanner = new Scanner(System.in);
        int counter = 1;
        int line;
        int column;
        boolean win = false;
        Element gameplay = docToWrite.createElement("Gameplay");
        docToWrite.appendChild(gameplay);
        gameplay.appendChild(playerToXml(1));
        gameplay.appendChild(playerToXml(2));
        Element game = docToWrite.createElement("Game");
        gameplay.appendChild(game);
        while(counter < 10 & !win) {
            int playerIndex = counter % 2 == 1 ? 1 : 2;
            printField();

            //player moves
            System.out.print("player " + (playerIndex == 1 ? player1 : player2) +
                    "\nEnter your move: line = ");
            line = scanner.nextInt() - 1;
            System.out.print("column = ");
            column = scanner.nextInt() - 1;

            if (!checkEnterMove(line, column)) {
                continue;
            }

            switch (playerIndex) {
                case 1 -> {
                    step(1, line, column);
                    game.appendChild(stepToXML(counter, 1, line + 1, column + 1));
                }
                case 2 -> {
                    step(2, line, column);
                    game.appendChild(stepToXML(counter, 2, line + 1, column + 1));
                }
            }

            //winner check
            win = lineСheck(playerIndex, line, column);
            if (win) {
                switch (playerIndex) {
                    case 1 -> {
                        System.out.println("Congratulation! " + player1 + " WIN!");
                        ratingEntry(WinStatus.FIRST);
                        gameplay.appendChild(winStatusToXML(WinStatus.FIRST));
                    }
                    case 2 -> {
                        System.out.println("Congratulation! " + player2 + " WIN!");
                        ratingEntry(WinStatus.SECOND);
                        gameplay.appendChild(winStatusToXML(WinStatus.SECOND));
                    }
                }
            }
            counter++;
            if(counter == 10 & !win){
                System.out.println("Draw!");
                ratingEntry(WinStatus.DRAW);
                gameplay.appendChild(winStatusToXML(WinStatus.DRAW));
            }
        }

        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(docToWrite);
            StreamResult file = new StreamResult(xml);
            transformer.transform(source, file);
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }

    //----------------------------------------------
    //Rating to txt
    private void ratingEntry(WinStatus winStatus){
        try {
            FileWriter fileWriter = new FileWriter(rating,true);
            if(rating.length() == 0){
                fileWriter.append("Rating list:\n");
            }
            switch (winStatus){
                case DRAW -> fileWriter.append(player1 + ", " + player2 + " draw!\n");
                case FIRST -> fileWriter.append(player1 + " winner!\n");
                case SECOND -> fileWriter.append(player2 + " winner!\n");
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //----------------------------------------------
    //Gameplay to XML methods
    //DOM recorder
    private Element playerToXml(int playerIndex){
        Element player = docToWrite.createElement("Player");
        player.setAttribute("id",String.valueOf(playerIndex));
        player.setAttribute("name", playerIndex == 1 ? player1 : player2);
        player.setAttribute("symbol", playerIndex == 1 ? "X" : "0");
        return player;
    }
    private Element stepToXML(int counter, int player, int line, int column){
        Element step = docToWrite.createElement("step");
        step.setAttribute("num", String.valueOf(counter));
        step.setAttribute("playerId", String.valueOf(player));
        step.setTextContent("line = " + line + ", column = " + column);
        return step;
    }
    private Element winStatusToXML(WinStatus winStatus){
        Element gameResult = docToWrite.createElement("GameResult");
        switch (winStatus){
            case DRAW -> {
                gameResult.setTextContent("Draw!");
                gameResult.appendChild(playerToXml(1));
                gameResult.appendChild(playerToXml(2));
            }
            case FIRST -> gameResult.appendChild(playerToXml(1));
            case SECOND -> gameResult.appendChild(playerToXml(2));
        }
        return gameResult;
    }

    //Gameplay from XML

    //DOM reader
    /*
    public void readFromXML(){
        try{
            if(!xml.exists()){
                throw new XMLMissingException();
            } else {
                int line;
                int column;
                docToRead.getDocumentElement().normalize();
                NodeList stepNodes = docToRead.getElementsByTagName("step");
                for(int i = 0; i < stepNodes.getLength(); i++){
                    String step = stepNodes.item(i).getTextContent();
                    line = Character.getNumericValue(step.charAt(7)) - 1;
                    column = Character.getNumericValue(step.charAt(19)) - 1;
                    step(i%2 == 1 ? 1 : 2, line, column);
                    printField();
                    System.out.println();
                }
                Node gameResult = docToRead.getElementsByTagName("GameResult").item(0);
                boolean draw = gameResult.getTextContent().trim().equals("Draw!");
                if(draw) {
                    System.out.println("Draw!");
                }
                NodeList winners = gameResult.getChildNodes();
                for(int i = 0; i < winners.getLength(); i++){
                    NamedNodeMap player;
                    if(winners.item(i).getNodeName().equals("Player")){
                        player = winners.item(i).getAttributes();
                        System.out.println("Player " + player.getNamedItem("id").getNodeValue() + " -> " +
                                player.getNamedItem("name").getNodeValue() + (draw ? "" : " winner") +" as '" +
                                player.getNamedItem("symbol").getNodeValue() + "'!");
                    }
                }
            }
        } catch (XMLMissingException e){
            e.printStackTrace();
        }
    }
     */

    //SAX reader
    public void playFromXML(){
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            XMLHandler handler = new XMLHandler();
            parser.parse(xml, handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public void playFromXML(String path){
        this.xml = new File(path);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            XMLHandler handler = new XMLHandler();
            parser.parse(xml, handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private class XMLHandler extends DefaultHandler {
        private int playerID, line, column = -1;
        private String lastElementName, name1 , name2;
        private boolean result, draw  = false;
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes){
            lastElementName = qName;
            switch (qName){
                case "step" ->
                    playerID = Integer.parseInt(attributes.getValue("playerId"));
                case "GameResult" ->
                    result = true;
                case "Player" ->{
                    if(result){
                        System.out.println("Player " + attributes.getValue("id") + " -> " +
                                attributes.getValue("name") + (draw ? "" : " winner") + " as '" + attributes.getValue("symbol") + "'!");
                    } else {
                        if(name1 == null){
                            name1 = attributes.getValue("name");
                        } else name2 = attributes.getValue("name");
                    }
                }
            }
        }
        @Override
        public void characters(char[] ch, int start, int lenght){
            String information = new String(ch, start, lenght);
            information = information.replaceAll("\n", "").trim();
            if(!information.isEmpty()){
                switch (lastElementName){
                    case "step" -> {
                        line = Character.getNumericValue(information.charAt(7)) - 1;
                        column = Character.getNumericValue(information.charAt(19)) - 1;
                    }
                    case "GameResult" -> {
                        System.out.println(information); //draw
                        draw = information.equals("Draw!");
                    }
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            if((line != -1) & (column != -1)){
                step(playerID,line,column);
                printField();
                System.out.println();
                line = -1;
                column = -1;
            }
        }
    }

}

//my exceptions
class IllegalPositionException extends Exception{
    public IllegalPositionException(){
        super("This position is taken, please select another");
    }
}
class IllegalValuesException extends Exception{
    public IllegalValuesException(){
        super("The set values go beyond the boundaries of the playing field. 0 < values < 4");
    }
}
class XMLMissingException extends Exception{
    public XMLMissingException() {
        super("XML file is missing");
    }
}

