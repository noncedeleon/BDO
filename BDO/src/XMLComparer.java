import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TreeMap;

public class XMLComparer {
    TreeMap<String, TreeMap<String, String>> xmlTemplate;
    TreeMap<String, TreeMap<String, String>> xmlResult;
    ArrayList<String> xmlDirectories;

    public XMLComparer(TreeMap<String, TreeMap<String, String>> xmlTemplate,
                       TreeMap<String, TreeMap<String, String>> xmlResult) {
        this.xmlTemplate = xmlTemplate;
        this.xmlResult = xmlResult;
    }

    public boolean directoriesAreEqual() {

        // EQUALITY PROBLEM
        // need to get the file name, not the whole path
        return xmlTemplate.keySet().equals(xmlResult.keySet());
    }

    public void setXmlDirectories() {
        xmlDirectories = new ArrayList<>();

        if (directoriesAreEqual()) {
            xmlDirectories.addAll(xmlTemplate.keySet());
        }
    }

    public ArrayList<String> getXmlDirectories() {
        return xmlDirectories;
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

    public void compareXMLs() {
        TreeMap<String, String> mismatches = null;

        if(directoriesAreEqual()) {
            for (String directory : xmlTemplate.keySet()) {
                // xml file paths (x24)
//                System.out.println(directory);

                TreeMap<String, String> xmlTemplateBlocks = xmlTemplate.get(directory);
                System.out.println("template " + xmlTemplateBlocks);

                TreeMap<String, String> xmlResultBlocks = xmlResult.get(directory);
                System.out.println("result " + xmlResultBlocks);

                // element = 10.10
                //.get(element) = Y
                for (String element : xmlTemplateBlocks.keySet()) {

//                    System.out.println("result " + xmlResultBlocks.get(element)
//                            + " template " + xmlTemplateBlocks.get(element));

//                    if (!element.equals(xmlResultBlocks.get(element))) {
//                        mismatches.put(directory, xmlResultBlocks.get(element));
                        //                    System.out.println(xmlResultBlocks.get(element));
//                    }
                }
                //            System.out.println(mismatches);
            }
        }
    }


}
