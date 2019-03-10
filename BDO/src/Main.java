import jxl.write.WritableWorkbook;

import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {

       /* Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the file path of the BDO template: ");
        String excelMasterFilepath = scanner.nextLine();

        System.out.print("Enter the file path where to copy the BDO results: ");
        String excelResultsFilepath = scanner.nextLine();

        System.out.print("Enter the root directory for the BDO xmls: ");
        String rootBDOdirectory = scanner.nextLine();

        System.out.print("Enter the xml element to check: ");
        String element = scanner.nextLine();

        System.out.print("Enter the xml attribute to check: ");
        String attribute = scanner.nextLine();

        scanner.close();*/

        // Excel


//        bdoWriter();

//        Xml Comparator


        String element = "textBlock";
        String attribute = "id";

        String rootBDOTestOneDirectory
                = "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P01/TC 1 - 3P01.xml";
        Xml one = new Xml(rootBDOTestOneDirectory, element, attribute);
        one.mapXmlTextBlocks();
        TreeMap<String, String> xmlOne = one.getXmlMappedTextBlocks();
//        System.out.println("one = " + xmlOne);

        String rootBDOTestTwoDirectory
                = "/users/jensen/Desktop/BDOs/xmlTestTwo/xmlTwo/3P01/TC 1 - 3P01.xml";
        Xml two = new Xml(rootBDOTestTwoDirectory, element, attribute);

        two.mapXmlTextBlocks();
        TreeMap<String, String> xmlTwo = two.getXmlMappedTextBlocks();
//        System.out.println("two = " + xmlTwo);

        XmlComparer xmlComparer = new XmlComparer(xmlOne, xmlTwo);


        System.out.println("Elements are equal: " + xmlComparer.xmlElementsAreEqual());
        System.out.println("Attribute are equal: " + xmlComparer.xmlAttributesAreEqual());
//        xmlComparer.setXmlDirectories();
//        System.out.println(xmlComparer.getXmlDirectories());


       /* XML Test

        Xml xml = new Xml("/users/jensen/Desktop/BDOs/xmls/3P01/TC 1 - 3P01.xml",
                "textBlock",
                "id");

        xml.mapXmlTextBlocks();
        // For 1 xml
//        System.out.println(xml.getXmlMappedTextBlocks());
        // XML works

        FileFinder directoryParser = new FileFinder(rootBDOdirectory);

        // Get root directory
        String rootDirectory = directoryParser.getRootDirectory();
//        System.out.println(rootDirectory);

        // Set sub directories
        directoryParser.setSubDirectories(rootDirectory);

        // Get sub directories
        ArrayList<String> subDirectories = directoryParser.getSubDirectories();
//        System.out.println(subDirectories);

        // Set all xml files
        directoryParser.setAllFilePaths();

        // Get all xml files
        ArrayList<String> allFilePaths = directoryParser.getAllFilePaths();
//        System.out.println(allFilePaths);

*/
    }

    private static void bdoWriter() {
        String excelMasterFilepath = "/users/jensen/Desktop/BDOs/BDOexpected.xls";
        String excelResultsFilepath = "/users/jensen/Desktop/BDOs/BDOresults.xls";
        String rootBDOdirectory = "/users/jensen/Desktop/BDOs/xmls";
        int columnInitial = 3;


        // Make a fileFinder outside of Excel --> to pass to spreadsheet
        FileFinder fileFinder = new FileFinder(rootBDOdirectory);
        fileFinder.setRootDirectory(rootBDOdirectory);

        ExcelSheet resultsWorkbook
                = new ExcelSheet(fileFinder, excelMasterFilepath, excelResultsFilepath);

        WritableWorkbook excelCopy
                = resultsWorkbook.createCopy(excelMasterFilepath, excelResultsFilepath);

//        Which is better??
        String rootDirectory = fileFinder.getRootDirectory();                           // Through file finder object
//        String rootDirectory = resultsWorkbook.getFileFinder().getRootDirectory();    // Through workbook
        fileFinder.setSubDirectories(rootDirectory);                                    // Through file finder object
//        resultsWorkbook.getFileFinder().setSubDirectories(rootDirectory);             // Through workbook

        resultsWorkbook.setTabNames();
        resultsWorkbook.writeWorkbook(excelCopy);
    }
}
