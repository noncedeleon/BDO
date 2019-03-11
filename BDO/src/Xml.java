import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeMap;

public class Xml {

    private String filePath;
    private String parentFileName;
    private String element;
    private String attribute;
    private TreeMap<String, String> xmlMappedTextBlocks;

    // Default -> element = "textBlock", attribute = "id"
    Xml(String filePath) {
        this.filePath = filePath;
        this.xmlMappedTextBlocks = new TreeMap<>();
        this.element = "textBlock";
        this.attribute = "id";
    }

    Xml(String filePath, String element, String attribute) {
        this(filePath);
        this.element = element;
        this.attribute = attribute;
    }

    public String getFileName() {
        return new File(filePath).getName();
    }

    public String getFilePath() {
        return filePath;
    }

    public String getParentFileName() {
        Path path = Paths.get(filePath);
        return path.getName(path.getNameCount() - 2).toString();
    }

    public String getElement() {
        return element;
    }

    public String getAttribute() {
        return attribute;
    }

    private Document convertXmlToDocument(String xmlFileName) {

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        Document xmlAsDocument = null;

        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
            xmlAsDocument = docBuilder.parse(new File(xmlFileName));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return xmlAsDocument;
    }

    public void setXmlMappedTextBlocks() {

        // Should this be called here?
        Document xmlAsDocument = convertXmlToDocument(filePath);
        NodeList textBlocks = xmlAsDocument.getElementsByTagName(element);

        for (int textBlock = 0; textBlock < textBlocks.getLength(); textBlock++) {
            Node firstTextBlock = textBlocks.item(textBlock);

            if (firstTextBlock.getNodeType() == Node.ELEMENT_NODE) {
                Element textBlockElement = (Element) firstTextBlock;
                String number = textBlockElement.getAttribute(attribute);
                String yesOrNo = firstTextBlock.getTextContent();
                xmlMappedTextBlocks.put(number, yesOrNo);
            }
        }
    }

    public TreeMap<String, String> getXmlMappedTextBlocks() {
        return xmlMappedTextBlocks;
    }
}
