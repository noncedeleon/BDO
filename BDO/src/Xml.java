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
import java.util.TreeMap;

public class Xml {

    private String fileName;
    private String elementTagName;
    private String attributeName;
    private TreeMap<String, String> xmlMappedTextBlocks;

    Xml(String fileName, String elementTagName, String attributeName) {
        this.fileName = fileName;
        this.elementTagName = elementTagName;
        this.attributeName = attributeName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getElementTagName() {
        return elementTagName;
    }

    public String getAttributeName() {
        return attributeName;
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

    void mapXmlTextBlocks() {

        // Initialize this at run time when needed instead of in constructor?
        xmlMappedTextBlocks = new TreeMap<>();

        // Should this be called here?
        Document xmlAsDocument = convertXmlToDocument(fileName);
        NodeList textBlocks = xmlAsDocument.getElementsByTagName(elementTagName);

        for (int textBlock = 0; textBlock < textBlocks.getLength(); textBlock++) {
            Node firstTextBlock = textBlocks.item(textBlock);

            if (firstTextBlock.getNodeType() == Node.ELEMENT_NODE) {
                Element textBlockElement = (Element) firstTextBlock;
                String number = textBlockElement.getAttribute(attributeName);
                String a = firstTextBlock.getTextContent();
                xmlMappedTextBlocks.put(number, a);
            }
        }
    }

    TreeMap<String, String> getXmlMappedTextBlocks() {
        return xmlMappedTextBlocks;
    }
}
