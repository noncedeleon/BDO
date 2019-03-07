import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.IOException;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) throws IOException, WriteException {

        // Both
        String rootBDOdirectory        = "/users/jensen/Desktop/BDOs/xmls";

        // Excel
        String excelMasterFilepath     = "/users/jensen/Desktop/BDOs/BDOexpected.xls";
        String excelCopyFilepath       = "/users/jensen/Desktop/BDOs/BDOresults.xls";



        // Xml
        String element          = "textBlock";
        String attribute        = "id";

        // Create xmlReader for a sheet with specific element and attribute to pull out
        XmlReader xmlReader = new XmlReader(rootBDOdirectory, element, attribute);
//      [/users/jensen/Desktop/BDOs/xmls/3P01,
//       /users/jensen/Desktop/BDOs/xmls/3P02,
//       /users/jensen/Desktop/BDOs/xmls/3P03o,
//       /users/jensen/Desktop/BDOs/xmls/3P03p]

        xmlReader.setBdoDirectoryFilePaths();
//        for (String sa : l) {
//            System.out.println(sa);
//        }

        xmlReader.setXmlsFromAllDirectories();
//        ArrayList<String> la = xmlReader.getXmlFilePaths();
//        for (String sa : la) {
//            System.out.println(sa);
//        }

        xmlReader.mapAllXmlTextBlocks(xmlReader.getRootBDOdirectory());
//        System.out.println(xmlReader.getMappedTextBlocks());

        // EXCEL
        // createCopy() makes original copy of excel book
        ExcelSheet sheet = new ExcelSheet(excelMasterFilepath, excelCopyFilepath, rootBDOdirectory);

        WritableWorkbook excelCopy = sheet.createCopy(excelMasterFilepath, excelCopyFilepath);

//        sheet.getTabNameFromXmlTreeMap(xmlReader.getMappedTextBlocks());
//        System.out.println(sheet.xmlsInBdo(xmlReader.getMappedTextBlocks()));
        // gets directories for each BDO (wherein all XMLs are)
//        ArrayList<String> bdoDirectories = xmlReader.getBdoDirectoryFilePaths();
//        System.out.println(bdoDirectories);

//        System.out.println(xmlReader.getMappedTextBlocks());
          // fill copied Excel with all XmlReader data from root directory
        TreeMap<String, TreeMap<String, String>> xmlBlocks = xmlReader.getMappedTextBlocks();

        sheet.fillOutputSpreadsheet(excelCopy, xmlReader.getMappedTextBlocks());
//
        excelCopy.write();
        excelCopy.close();
//
//
//
//        // Reading out the original excel, not the results file.
//        TreeMap<String, TreeMap> errors
//                = sheet.errorsInWorkbook(excelCopy, bdoDirectories);
//
//        sheet.printErrors(errors);

    }
}
