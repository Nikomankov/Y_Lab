package Parser;

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

public class XMLParser  implements Parser{
    private Document document;

    public XMLParser(File file){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            this.document = builder.parse(file);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        document.getDocumentElement().normalize();
    }

    @Override
    public int[][] getSteps(){
        NodeList stepNodes = document.getElementsByTagName("step");
        int[][] stepsArray = new int[2][stepNodes.getLength()];
        for(int i = 0; i < stepNodes.getLength(); i++){
            String step = stepNodes.item(i).getTextContent();
            stepsArray[0][i] = Character.getNumericValue(step.charAt(7)) - 1;
            stepsArray[1][i] = Character.getNumericValue(step.charAt(19)) - 1;
        }
        return stepsArray;
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
