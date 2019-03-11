import jxl.write.WritableWorkbook;

import java.util.ArrayList;
import java.util.Map;

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


//        bdoWriter();
        xmlComparator();


    }

    private static void xmlComparator() {

        String rootDirTestOne = "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne";
        String rootDirTestTwo = "/users/jensen/Desktop/BDOs/xmlTestTwo/xmlTwo";

        DirectoryNavigator directoryNavigatorOne = new DirectoryNavigator(rootDirTestOne);
        DirectoryNavigator directoryNavigatorTwo = new DirectoryNavigator(rootDirTestTwo);

//        directoryNavigatorOne.setSubDirectories(directoryNavigatorOne.getRootDirectory());


        String rootBDOTestOneDirectory
                = "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P01/TC 1 - 3P01.xml";
        String rootBDOTestTwoDirectory
                = "/users/jensen/Desktop/BDOs/xmlTestTwo/xmlTwo/3P01/TC 1 - 3P01.xml";

        Xml one = new Xml(rootBDOTestOneDirectory);
        Xml two = new Xml(rootBDOTestTwoDirectory);

        one.setXmlMappedTextBlocks();
        two.setXmlMappedTextBlocks();

        XmlComparer xmlComparer = new XmlComparer(one, two);

        xmlComparer.setUnequalElements();

        BdoXmlComp bdoXmlComp = new BdoXmlComp(directoryNavigatorOne, directoryNavigatorTwo);

        directoryNavigatorOne.setSubDirectories(directoryNavigatorOne.getRootDirectory());
        directoryNavigatorOne.setSubDirectoryNames();
        directoryNavigatorOne.setAllFilePaths(directoryNavigatorOne.getRootDirectory());
        directoryNavigatorOne.setAllFileNames();

        directoryNavigatorTwo.setSubDirectories(directoryNavigatorTwo.getRootDirectory());
        directoryNavigatorTwo.setSubDirectoryNames();
        directoryNavigatorTwo.setAllFilePaths(directoryNavigatorTwo.getRootDirectory());
        directoryNavigatorTwo.setAllFileNames();

        boolean templateFileNamesEqualResultsFileNames
                = bdoXmlComp.templateFileNamesEqualResultsFileNames();

        bdoXmlComp.setTemplateXmlFilepathsMappedToResultsXmlFilepaths();

//        System.out.println(bdoXmlComp.getTemplateXmlFilepathsMappedToResultsXmlFilepaths());
        bdoXmlComp.as();
    }

    private static void bdoWriter() {
        String excelMasterFilepath = "/users/jensen/Desktop/BDOs/BDOexpected.xls";
        String excelResultsFilepath = "/users/jensen/Desktop/BDOs/BDOresults.xls";
        String rootBDOdirectory = "/users/jensen/Desktop/BDOs/xmls";
        int columnInitial = 3;


        // Make a directoryNavigator outside of Excel --> to pass to spreadsheet
        DirectoryNavigator directoryNavigator = new DirectoryNavigator(rootBDOdirectory);
//        directoryNavigator.setRootDirectory(rootBDOdirectory);

        ExcelSheet resultsWorkbook
                = new ExcelSheet(directoryNavigator, excelMasterFilepath, excelResultsFilepath);

        WritableWorkbook excelCopy
                = resultsWorkbook.createCopy(excelMasterFilepath, excelResultsFilepath);

//        Which is better??
        String rootDirectory = directoryNavigator.getRootDirectory();                           // Through file finder object
//        String rootDirectory = resultsWorkbook.getDirectoryNavigator().getRootDirectory();    // Through workbook
        directoryNavigator.setSubDirectories(rootDirectory);                                    // Through file finder object
//        resultsWorkbook.getDirectoryNavigator().setSubDirectories(rootDirectory);             // Through workbook

//        resultsWorkbook.setSubDirectoryNames();
        resultsWorkbook.writeWorkbook(excelCopy);
    }
}
