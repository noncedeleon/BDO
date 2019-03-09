import jxl.write.WritableWorkbook;

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
        String excelMasterFilepath     = "/users/jensen/Desktop/BDOs/BDOexpected.xls";
        String excelResultsFilepath    = "/users/jensen/Desktop/BDOs/BDOresults.xls";
        String rootBDOdirectory        = "/users/jensen/Desktop/BDOs/xmls";
        String element                 = "textBlock";
        String attribute               = "id";
        int columnInitial              = 3;


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

        /*Xml Comparator

        String rootTestDirectoryOne        = "/users/jensen/Desktop/BDOs/xmlTestOne";
        String rootTestDirectoryTwo        = "/users/jensen/Desktop/BDOs/xmlTestTwo";

        Xml xmlOne = new Xml(rootTestDirectoryOne, element, attribute);
        String rootBDOTestOneDirectory        = "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne";

        Xml xmlTwo = new Xml(rootTestDirectoryTwo, element, attribute);
        String rootBDOTestTwoDirectory        = "/users/jensen/Desktop/BDOs/xmlTestTwo/xmlTwo";

        xmlOne.setSubDirectories();
        xmlOne.setAllFilePaths();
        xmlOne.mapAllXmlTextBlocks(rootBDOTestOneDirectory);

//      xmlTwo.setSubDirectories();
//        xmlTwo.setAllFilePaths();
        xmlTwo.mapAllXmlTextBlocks(rootBDOTestTwoDirectory);

        XMLComparer xmlComparer = new XMLComparer(
                xmlOne.getMappedTextBlocks(),
                xmlTwo.getMappedTextBlocks());

        System.out.println(xmlComparer.directoriesAreEqual());
        xmlComparer.setXmlDirectories();
//        System.out.println(xmlComparer.getXmlDirectories());
        xmlComparer.compareXMLs();
        */


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
