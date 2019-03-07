import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ExcelSheet {

    String excelMaster;
    String excelCopy;
    String rootBDOdirectory;

    int numberOfXmlsPerBDO;

    public ExcelSheet(String excelMaster, String excelCopy, String rootBDOdirectory) {
        this.excelMaster = excelMaster;
        this.excelCopy = excelCopy;
        this.rootBDOdirectory = rootBDOdirectory;
    }

    public WritableWorkbook createCopy(String excelMaster, String excelCopy) {
        Workbook original;
        WritableWorkbook copy = null;
        try {
            original = Workbook.getWorkbook(new File(excelMaster));
            copy = Workbook.createWorkbook(new File(excelCopy), original);
        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }
        return copy;
    }

    // xmlFileName == full path name
    // Gets BDO directory filepaths
    public ArrayList<String> getBdoDirectoryFilePaths(String directory) {
        File filePath = new File(directory);
        ArrayList<String> directoryList = new ArrayList<>();

        if (filePath.isDirectory()) {
            File[] directoryFileList = filePath.listFiles();
            for (File file : directoryFileList) {
                if (!file.isHidden()) {
                    directoryList.add(file.getAbsolutePath());
                }
            }
        }
        Collections.sort(directoryList);
        return directoryList;
    }


    public TreeMap<String, TreeMap<String, String>>
        xmlsInBdo(TreeMap<String, TreeMap<String, String>> xmlDirectory, String bdo) {

        TreeMap<String, TreeMap<String, String>> bdoDirectory = new TreeMap<>();

        for (String s : xmlDirectory.keySet()) {
            TreeMap<String, String> xmls;
            Path xml = Paths.get(s);
            String bdoString = xml.getName(xml.getNameCount() - 2).toString();

            // 3P01 == ../../..3P01/...xml
            if (bdo.equals(bdoString)) {
                xmls = xmlDirectory.get(s);
                bdoDirectory.put(s, xmls);
            }
        }
        return bdoDirectory;
    }

    public Set<String> getTabNamesFromXmlTreeMap(TreeMap<String, TreeMap<String, String>> xmlDirectory) {
        Set<String> files = xmlDirectory.keySet();
        Set<String> bdoDirectories = new TreeSet<>();

        for (String s : files) {
            Path p = Paths.get(s);
            bdoDirectories.add(p.getName(p.getNameCount() - 2).toString());
        }
        return bdoDirectories;
    }

    public void writeSheet(WritableWorkbook results,
                           TreeMap<String, TreeMap<String, String>> xmlsInDirectory,
                           String bdoDirectory) {

        TreeMap<String, TreeMap<String, String>> xmlsPerBdo
                = xmlsInBdo(xmlsInDirectory, bdoDirectory);

        int columnInitial = 3;
        int columnIterator = 5;
        int rowOffset = 5;

        try {
            // this loop does all the columns
            WritableSheet sheet = results.getSheet(bdoDirectory);

            for(String s : xmlsPerBdo.keySet()) {
                TreeMap<String, String> blocks;
                blocks = xmlsPerBdo.get(s);
                for (String textBlock : blocks.keySet()) {
                    String yesOrNo = blocks.get(textBlock);

                    Label textBlockNumber = new Label(columnInitial, rowOffset, textBlock);
                    sheet.addCell(textBlockNumber);
                    columnInitial++;

                    Label yesOrNoLabel = new Label(columnInitial, rowOffset, yesOrNo);
                    sheet.addCell(yesOrNoLabel);
                    columnInitial--;

                    rowOffset++;
                }
                columnInitial += columnIterator;
                rowOffset -= blocks.size();
            }

        } catch (WriteException e) {
            e.printStackTrace();
        }
    }


    public void fillOutputSpreadsheet(WritableWorkbook outputSpreadsheet,
                                      TreeMap<String, TreeMap<String, String>> xmlTextBlocks) {

        Set<String> bdoDirectories = getTabNamesFromXmlTreeMap(xmlTextBlocks);
        // returns 4 - one for each tab
        for (String bdo : bdoDirectories) {

            TreeMap<String, TreeMap<String, String>> xmlsInBdo = xmlsInBdo(xmlTextBlocks, bdo);

            writeSheet(outputSpreadsheet, xmlTextBlocks, bdo);
        }
    }


    // ERRORS

    public String getTabNameFromFilePathForErrorReporting(String xmlDirectory) {
        Path p = Paths.get(xmlDirectory);
        return p.getName(p.getNameCount() - 1).toString();
    }
}

//    public TreeMap<String, TreeMap> errorsInWorkbook(WritableWorkbook results,
//                                                     ArrayList<String> xmlsInDirectory) {
//
//        TreeMap<String, TreeMap> errorsPerBDO = new TreeMap<>();
//
////        xmlsInBdo(xmlsInDirectory.get(0));
//
//
//        for (String bdoDirectories : xmlsInDirectory) {
//
//            String filePath = bdoDirectories;
//            String tabName = getTabNameFromFilePathForErrorReporting(filePath);
//            System.out.println(tabName);
//            WritableSheet sheet = results.getSheet(tabName);
//
//            TreeMap<String, TreeMap> errorsPerSheet = new TreeMap<>();
//
//            int column = 5;
//            int row = 1;
//            int columnIncrementor = 5;
//            int rowIncrementor = 1;
//
//            TreeMap<String, String> errorsPerTestCase= new TreeMap<>();
//
//            for (int i = 0; i < numberOfXmlsPerBDO; i++) {
//
//                String testCase = sheet.getCell(column, row).getContents();
//                row += rowIncrementor;
//
//                String errors = sheet.getCell(column, row).getContents();
//                errorsPerTestCase.put(testCase, errors);
//
//                row -= rowIncrementor;
//                column += columnIncrementor;
//                errorsPerSheet.put(tabName, errorsPerTestCase);
//            }
//            errorsPerBDO.put(bdoDirectories, errorsPerTestCase);
//        }
//        return errorsPerBDO;
//    }
//
//    public void printErrors(TreeMap<String, TreeMap> errorsInWorkbook) {
//        for (Map.Entry<String, TreeMap> string : errorsInWorkbook.entrySet()) {
//            System.out.println(string);
//        }
//    }