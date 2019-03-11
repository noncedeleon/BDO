import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BdoXmlCompTest {

    String rootDirTestOne = "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne";
    String rootDirTestTwo = "/users/jensen/Desktop/BDOs/xmlTestTwo/xmlTwo";

    DirectoryNavigator directoryNavigatorOne = new DirectoryNavigator(rootDirTestOne);
    DirectoryNavigator directoryNavigatorTwo = new DirectoryNavigator(rootDirTestTwo);

    BdoXmlComp bdoXmlComp = new BdoXmlComp(directoryNavigatorOne, directoryNavigatorTwo);


    // Need to set sub directories -->
    // then set all file names
    @Test
    public void templateFileNamesEqualResultsFileNames() throws Exception {
        directoryNavigatorOne.setSubDirectories(directoryNavigatorOne.getRootDirectory());
        directoryNavigatorTwo.setSubDirectories(directoryNavigatorTwo.getRootDirectory());

        directoryNavigatorOne.setAllFileNames();
        directoryNavigatorTwo.setAllFileNames();
        assertTrue(bdoXmlComp.templateFileNamesEqualResultsFileNames());
    }
}
