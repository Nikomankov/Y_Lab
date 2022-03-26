package Parser;

import Game.Step;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class ParserXML implements Parser{
    private Document document;

    public ParserXML(File file){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(file);
            document.getDocumentElement().normalize();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Step[] getSteps(){
        NodeList stepNodes = document.getElementsByTagName("step");
        Step[] steps = new Step[stepNodes.getLength()];
        for(int i = 0; i < stepNodes.getLength(); i++){
            String step = stepNodes.item(i).getTextContent();
            steps[i] = new Step(i+1,i % 2 == 1 ? 2 : 1,Character.getNumericValue(step.charAt(7)),Character.getNumericValue(step.charAt(19)));
        }
        return steps;
    }

    @Override
    public String getResults(){
        Node gameResult = document.getElementsByTagName("GameResult").item(0);
        boolean draw = gameResult.getTextContent().trim().equals("Draw!");
        if (draw) {
            return "Draw!";
        }
        NamedNodeMap winner = document.getElementsByTagName("Player").item(0).getAttributes();
        return ("Player " + winner.getNamedItem("id").getNodeValue() + " -> " +
                winner.getNamedItem("name").getNodeValue() + " winner as '" +
                winner.getNamedItem("symbol").getNodeValue() + "'!");
    }
}
