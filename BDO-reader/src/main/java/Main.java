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
import java.util.*;

public class Main {

    public static void main (String args []) throws IOException, BiffException, WriteException {

//        need to build in if system is windows or mac
        String copyName = "BDOresults.xls";
        String tab = "3P01";

        WritableWorkbook copy = copyOriginal("BDOexpected.xls", copyName);

        ArrayList<String> processXMLs;
        processXMLs = xml_3P01();
//
//
        firstExcel(processXMLs, 3, copyName,tab);
        otherXMLs(processXMLs, 3, copy,tab);

//        processXMLs = xml_3P02();
//        otherXMLs(processXMLs, 3,"BDOresults.xls", "3P02");

//        processXMLs = xml_3P03o();
//        otherXMLs(processXMLs, 3,"BDOresults.xls", "3P03o");
//
//        processXMLs = xml_3P03p();
//        otherXMLs(processXMLs, 3,"BDOresults.xls", "3P03p");

//        closeResultExcel(copy);
    }


    public static ArrayList<String> xmls(String directoryPath) {

        File dir = new File(directoryPath);
        ArrayList<String> files = new ArrayList<>();

        if (dir.isDirectory()) {
            File[] listFiles = dir.listFiles();

            if (listFiles != null) {
                for (File file : listFiles) {
                    if (file.isFile()) {
                        files.add(file.getName());
                    }
                }
            }
        }
        return files;
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
                                   String excelFile, String tabName) throws IOException {

        TreeMap<String, String> xmlResults;

        String firstXML = "xmls/3P01/TC 1 - 3P01.xml";

        String original = "BDOexpected.xls";
        String copy = "BDOresults.xls";

        WritableWorkbook BDOresults = copyOriginal(original, copy);

        xmlResults = XMLreader(new File(firstXML));

        ExcelWriter(xmlResults, columnOffset, BDOresults, tabName);

        otherXMLs(xml_3P01(), 3, BDOresults, tabName);

    }

    private static void otherXMLs(ArrayList<String> xmls,
                                  int columnOffset,
                                  WritableWorkbook excelFile,
                                  String tabName) {

        TreeMap<String, String> xmlResults;

        int columnOffsetIncrement = 5;

        for (String xmlFile: xmls) {
            xmlResults = XMLreader(new File(xmlFile));
            ExcelWriter(xmlResults, columnOffset, excelFile, tabName);
            columnOffset += columnOffsetIncrement;
        }
    }

//    Figure out how to enter a folder path for xmls and then loop over xml files
//    to get ride of 4 below methods

    private static void ExcelWriter(TreeMap<String, String> blocks,
                                    int columnOffset,
                                    WritableWorkbook writableWorkbook,
                                    String tabName) {
        try {
//            Workbook workbook
//                    = Workbook.getWorkbook(new File(originalFile));
//
//            WritableWorkbook newCopy
//                    = Workbook.createWorkbook(new File(copyFile), workbook);

            WritableSheet sheet = writableWorkbook.getSheet(tabName);

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
            writableWorkbook.write();
            writableWorkbook.close();

        } catch (WriteException | IOException e) {
            e.printStackTrace();
        }
    }

    public static WritableWorkbook copyOriginal(String originalExcel,
                                          String copyExcel) {
        Workbook original = null;
        WritableWorkbook copy = null;
        try {
            original
                    = Workbook.getWorkbook(new File(originalExcel));
            copy
                    = Workbook.createWorkbook(new File(copyExcel), original);
            copy.write();
            copy.close();
        } catch (IOException | BiffException | WriteException e) {
            e.printStackTrace();
        }
        return copy;
    }



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
}
