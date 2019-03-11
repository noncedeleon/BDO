import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public class DirectoryNavigatorTest {

    String rootDirTestOne = "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne";
//    String rootDirTestTwo = "/users/jensen/Desktop/BDOs/xmlTestTwo/xmlTwo";

    DirectoryNavigator one = new DirectoryNavigator(rootDirTestOne);
//    DirectoryNavigator two = new DirectoryNavigator(rootDirTestTwo);

    ArrayList<String> subDirectories
            = new ArrayList<>(
                    Arrays.asList("/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P01",
                            "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P02",
                            "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P03o",
                            "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P03p"));

    ArrayList<String> allFilePaths
            = new ArrayList<>(
            Arrays.asList(
                    "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P01/TC 1 - 3P01.xml",
                    "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P01/TC 2 - 3P01.xml",
                    "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P01/TC 3 - 3P01.xml",
                    "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P01/TC 4 - 3P01.xml",
                    "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P01/TC 5 - 3P01.xml",
                    "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P01/TC 6 - 3P01.xml",
                    "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P02/TC 1 - 3P02.xml",
                    "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P02/TC 2 - 3P02.xml",
                    "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P02/TC 3 - 3P02.xml",
                    "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P02/TC 4 - 3P02.xml",
                    "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P02/TC 5 - 3P02.xml",
                    "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P02/TC 6 - 3P02.xml",
                    "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P03o/TC 1 - 3P03o.xml",
                    "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P03o/TC 2 - 3P03o.xml",
                    "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P03o/TC 3 - 3P03o.xml",
                    "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P03o/TC 4 - 3P03o.xml",
                    "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P03o/TC 5 - 3P03o.xml",
                    "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P03o/TC 6 - 3P03o.xml",
                    "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P03p/TC 1 - 3P03p.xml",
                    "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P03p/TC 2 - 3P03p.xml",
                    "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P03p/TC 3 - 3P03p.xml",
                    "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P03p/TC 4 - 3P03p.xml",
                    "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P03p/TC 5 - 3P03p.xml",
                    "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P03p/TC 6 - 3P03p.xml"));

    ArrayList<String> allFileNames
            = new ArrayList<>(
            Arrays.asList(
                    "TC 1 - 3P01.xml", "TC 1 - 3P02.xml", "TC 1 - 3P03o.xml", "TC 1 - 3P03p.xml",
                    "TC 2 - 3P01.xml", "TC 2 - 3P02.xml", "TC 2 - 3P03o.xml", "TC 2 - 3P03p.xml",
                    "TC 3 - 3P01.xml", "TC 3 - 3P02.xml", "TC 3 - 3P03o.xml", "TC 3 - 3P03p.xml",
                    "TC 4 - 3P01.xml", "TC 4 - 3P02.xml", "TC 4 - 3P03o.xml", "TC 4 - 3P03p.xml",
                    "TC 5 - 3P01.xml", "TC 5 - 3P02.xml", "TC 5 - 3P03o.xml", "TC 5 - 3P03p.xml",
                    "TC 6 - 3P01.xml", "TC 6 - 3P02.xml", "TC 6 - 3P03o.xml", "TC 6 - 3P03p.xml"));

    @Test
    public void getDirectory() throws Exception {
        assertEquals(rootDirTestOne, one.getRootDirectory());
    }

    @Test
    public void getSubDirectories() throws Exception {
        one.setSubDirectories(one.getRootDirectory());
        assertEquals(subDirectories, one.getSubDirectories());
    }

    @Test
    public void getSubDirectoryNames() throws Exception {
        one.setSubDirectories(one.getRootDirectory());
        one.setSubDirectoryNames();
        assertEquals( "3P01", one.getSubDirectoryNames().get(subDirectories.get(0)));
        assertEquals( "3P02", one.getSubDirectoryNames().get(subDirectories.get(1)));
        assertEquals( "3P03o", one.getSubDirectoryNames().get(subDirectories.get(2)));
        assertEquals( "3P03p", one.getSubDirectoryNames().get(subDirectories.get(3)));
    }

    @Test
    public void getAllFilePaths() throws Exception {
        one.setSubDirectories(one.getRootDirectory());
        one.setSubDirectoryNames();
        one.setAllFilePaths(one.getRootDirectory());

        assertEquals(allFilePaths.get(0),one.getAllFilePaths().get(0));
        assertEquals(allFilePaths.get(6),one.getAllFilePaths().get(6));
        assertEquals(allFilePaths.get(12),one.getAllFilePaths().get(12));
        assertEquals(allFilePaths.get(18),one.getAllFilePaths().get(18));
        assertEquals(allFilePaths, one.getAllFilePaths());
    }

    @Test
    public void getAllFileNames() throws Exception {
        one.setSubDirectories(one.getRootDirectory());
        one.setAllFileNames();
        assertEquals(allFileNames.get(0), one.getAllFileNames().get(0));
        assertEquals(allFileNames.get(4), one.getAllFileNames().get(4));
        assertEquals(allFileNames.get(16), one.getAllFileNames().get(16));
        assertEquals(allFileNames.get(21), one.getAllFileNames().get(21));
    }
}
