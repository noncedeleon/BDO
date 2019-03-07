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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeMap;

public class XmlReader {

    private String rootBDOdirectory;
    String elementTagName;
    String attributeName;
    ArrayList<String> bdoDirectoryFilePaths;
    ArrayList<String> xmlFilePaths;
    TreeMap<String, TreeMap<String, String>> mappedTextBlocks;

    public XmlReader(String rootBDOdirectory, String elementTagName, String attributeName) {
        this.rootBDOdirectory = rootBDOdirectory;
        this.elementTagName = elementTagName;
        this.attributeName = attributeName;
        mappedTextBlocks = new TreeMap<>();
    }

    public String getRootBDOdirectory() {
        return rootBDOdirectory;
    }

    public ArrayList<String> getBdoDirectoryFilePaths() {
        return bdoDirectoryFilePaths;
    }

    public ArrayList<String> getXmlFilePaths() {
        return xmlFilePaths;
    }

    public void setBdoDirectoryFilePaths() {
        ArrayList<String> directoryList = new ArrayList<>();
        File filePath = new File(rootBDOdirectory);

        if (filePath.isDirectory()) {
            File[] directoryFileList = filePath.listFiles();
            for (File file : directoryFileList) {
                if (!file.isHidden()) {
                    directoryList.add(file.getAbsolutePath());
                }
            }
        }
        Collections.sort(directoryList);
        bdoDirectoryFilePaths = directoryList;
    }

    public void setXmlsFromAllDirectories() {
        ArrayList<String> allXmls = new ArrayList<>();

        for (String bdoDirectory : bdoDirectoryFilePaths) {
            File directory = new File(bdoDirectory);
            File[] xmls = directory.listFiles();

            for (File file : xmls) {
                if (file.isFile() && !file.isHidden()) {
                    allXmls.add(file.getAbsolutePath());
                }
            }
        }
        Collections.sort(allXmls);
        xmlFilePaths = allXmls;
    }


    public TreeMap<String, String> getXmlTextBlocks(String xmlFileName) {

        TreeMap<String, String> xmlTextBlocks = new TreeMap<>();

        Document xml = convertStringXmlToDocument(xmlFileName);
        NodeList textBlocks = xml.getElementsByTagName(elementTagName);

        for (int textBlock = 0; textBlock < textBlocks.getLength(); textBlock++) {
            Node firstTextBlock = textBlocks.item(textBlock);

            if (firstTextBlock.getNodeType() == Node.ELEMENT_NODE) {
                Element textBlockElement = (Element) firstTextBlock;
                String number = textBlockElement.getAttribute(attributeName);
                String a = firstTextBlock.getTextContent();
                xmlTextBlocks.put(number, a);
            }
        }
        return xmlTextBlocks;
    }

    private Document convertStringXmlToDocument(String xmlFileName) {

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        Document xml = null;

        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
            xml = docBuilder.parse(new File(xmlFileName));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return xml;
    }

    public void mapAllXmlTextBlocks(String rootBDOdirectory) {

        TreeMap<String, String> xmlBlocks = new TreeMap<>();

        File dir = new File(rootBDOdirectory);
        File[] xmls = dir.listFiles();
        Arrays.sort(xmls);

        for (File file : xmls) {
            if (!file.isHidden()) {
                if (file.isDirectory()) {
                    mapAllXmlTextBlocks(file.getAbsolutePath());
                } else {
                    String fileName = file.getAbsolutePath();
                    xmlBlocks = getXmlTextBlocks(fileName);
                    mappedTextBlocks.put(fileName, xmlBlocks);
                }
            }
        }
    }

    public TreeMap<String, TreeMap<String, String>> getMappedTextBlocks() {
        return mappedTextBlocks;
    }
}
