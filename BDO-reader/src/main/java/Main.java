import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Main {

    public static void main (String args []){

        ArrayList<String> processXMLs;

        processXMLs = xml_3P01();
        firstExcel(processXMLs, 3, "BDOresults.xls","3P01");

        processXMLs = xml_3P02();
        otherXMLs(processXMLs, 3,"BDOresults.xls", "3P02");

        processXMLs = xml_3P03o();
        otherXMLs(processXMLs, 3,"BDOresults.xls", "3P03o");

        processXMLs = xml_3P03p();
        otherXMLs(processXMLs, 3,"BDOresults.xls", "3P03p");
    }

    private static TreeMap<String, String> XMLreader(File xml) {

        TreeMap<String, String> textBlocks = null;

        try {
            textBlocks = new TreeMap<>();

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

            Document doc = docBuilder.parse(new File(String.valueOf(xml)));

            doc.getDocumentElement().normalize();

            NodeList listOfTextBlocks = doc.getElementsByTagName("textBlock");

            int totalTextBlocks = listOfTextBlocks.getLength();
            System.out.println("Number of text blocks: " + totalTextBlocks);

            for (int textBlock = 0; textBlock < totalTextBlocks; textBlock++) {

                Node firstTextBlock = listOfTextBlocks.item(textBlock);

                if (firstTextBlock.getNodeType() == Node.ELEMENT_NODE) {
                    Element textBlockElement = (Element) firstTextBlock;
                    String number = textBlockElement.getAttribute("id");
                    String a = firstTextBlock.getTextContent();
                    textBlocks.put(number, a);
                }
            }

        } catch (SAXParseException err) {
            System.out.println ("** Parsing error" + ", line "
                    + err.getLineNumber () + ", uri " + err.getSystemId ());
            System.out.println(" " + err.getMessage ());
        } catch (SAXException e) {
            Exception x = e.getException ();
            ((x == null) ? e : x).printStackTrace ();

        } catch (Throwable t) {
            t.printStackTrace ();
        }
        return textBlocks;
    }

    private static void firstExcel(ArrayList<String> xmls, int columnOffset,
                                   String excelFile, String tabName) {

        TreeMap<String, String> xmlResults;
        int columnOffsetIncrement = 5;

        String firstXML = "xmls/3P01/TC 1 - 3P01.xml";

        String BDOexpected = "BDOexpected.xls";
        String BDOresults = "BDOresults.xls";

        xmlResults = XMLreader(new File(firstXML));

        ExcelWriter(xmlResults, columnOffset, BDOexpected, BDOresults, tabName);
//        columnOffset += columnOffsetIncrement;

        otherXMLs(xml_3P01(), 3, excelFile, tabName);

    }

    private static void otherXMLs(ArrayList<String> xmls, int columnOffset,
                                  String excelFile, String tabName) {

        TreeMap<String, String> xmlResults;

        int columnOffsetIncrement = 5;

        for (String xmlFile: xmls) {
            xmlResults = XMLreader(new File(xmlFile));
            ExcelWriter(xmlResults, columnOffset, excelFile, excelFile, tabName);
            columnOffset += columnOffsetIncrement;
        }
    }

//    Figure out how to enter a folder path for xmls and then loop over xml files
//    to get ride of 4 below methods

    private static ArrayList<String> xml_3P01 () {
        ArrayList<String> xmls3P01 = new ArrayList<>();

        xmls3P01.add("xmls/3P01/TC 2 - 3P01.xml");
        xmls3P01.add("xmls/3P01/TC 3 - 3P01.xml");
        xmls3P01.add("xmls/3P01/TC 4 - 3P01.xml");
        xmls3P01.add("xmls/3P01/TC 5 - 3P01.xml");
        xmls3P01.add("xmls/3P01/TC 6 - 3P01.xml");

        return xmls3P01;
    }

    private static ArrayList<String> xml_3P02 () {
        ArrayList<String> xmls3P02 = new ArrayList<>();

        xmls3P02.add("xmls/3P02/TC 1 - 3P02.xml");
        xmls3P02.add("xmls/3P02/TC 2 - 3P02.xml");
        xmls3P02.add("xmls/3P02/TC 3 - 3P02.xml");
        xmls3P02.add("xmls/3P02/TC 4 - 3P02.xml");
        xmls3P02.add("xmls/3P02/TC 5 - 3P02.xml");
        xmls3P02.add("xmls/3P02/TC 6 - 3P02.xml");

        return xmls3P02;
    }

    private static ArrayList<String> xml_3P03o () {
        ArrayList<String> xmls3P03o = new ArrayList<>();

        xmls3P03o.add("xmls/3P03o/TC 1 - 3P03o.xml");
        xmls3P03o.add("xmls/3P03o/TC 2 - 3P03o.xml");
        xmls3P03o.add("xmls/3P03o/TC 3 - 3P03o.xml");
        xmls3P03o.add("xmls/3P03o/TC 4 - 3P03o.xml");
        xmls3P03o.add("xmls/3P03o/TC 5 - 3P03o.xml");
        xmls3P03o.add("xmls/3P03o/TC 6 - 3P03o.xml");

        return xmls3P03o;
    }

    private static ArrayList<String> xml_3P03p () {
        ArrayList<String> xmls3P03p = new ArrayList<>();

        xmls3P03p.add("xmls/3P03p/TC 1 - 3P03p.xml");
        xmls3P03p.add("xmls/3P03p/TC 2 - 3P03p.xml");
        xmls3P03p.add("xmls/3P03p/TC 3 - 3P03p.xml");
        xmls3P03p.add("xmls/3P03p/TC 4 - 3P03p.xml");
        xmls3P03p.add("xmls/3P03p/TC 5 - 3P03p.xml");
        xmls3P03p.add("xmls/3P03p/TC 6 - 3P03p.xml");

        return xmls3P03p;
    }

    private static void ExcelWriter(TreeMap<String, String> blocks,
                                    int columnOffset,
                                    String originalFile,
                                    String copyFile,
                                    String tabName) {
        try {
            Workbook workbook
                    = Workbook.getWorkbook(new File(originalFile));

            WritableWorkbook newCopy
                    = Workbook.createWorkbook(new File(copyFile), workbook);

            WritableSheet sheet = newCopy.getSheet(tabName);

            int cOffset = columnOffset;
            int rowOffset = 5;

            for (Map.Entry<String, String> entry : blocks.entrySet()) {
                String block = entry.getKey();
                String yesOrNo = entry.getValue();

                Label blockLabel = new Label(cOffset, rowOffset, block);
                sheet.addCell(blockLabel);
                cOffset++;

                Label yesOrNoLabel = new Label(cOffset, rowOffset, yesOrNo);
                sheet.addCell(yesOrNoLabel);

                cOffset--;
                rowOffset++;
            }

            newCopy.write();
            newCopy.close();
            workbook.close();

        } catch (WriteException | IOException | BiffException e) {
            e.printStackTrace();
        }
    }
}
