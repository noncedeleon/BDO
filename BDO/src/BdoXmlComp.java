import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class BdoXmlComp {

    private DirectoryNavigator templateRoot;
    private DirectoryNavigator resultsRoot;
    private TreeMap<String, String> templateFilepathsMappedToResultsFilepaths;

    public BdoXmlComp(DirectoryNavigator templateRoot, DirectoryNavigator resultsRoot) {
        this.templateRoot = templateRoot;
        this.resultsRoot = resultsRoot;
        this.templateFilepathsMappedToResultsFilepaths = new TreeMap<>();
    }

    // Sub directories in DirectorNavigator need to be set
    public boolean templateFileNamesEqualResultsFileNames() {
        ArrayList<String> templateFileNames = templateRoot.getAllFileNames();
        ArrayList<String> resultsFileNames  = resultsRoot.getAllFileNames();

        return templateFileNames.equals(resultsFileNames);
    }

    public void setTemplateXmlFilepathsMappedToResultsXmlFilepaths() {

        if (templateFileNamesEqualResultsFileNames()) {
            ArrayList<String> templateFilePaths = templateRoot.getAllFilePaths();
            ArrayList<String> resultsFilePaths = resultsRoot.getAllFilePaths();

            for (int i = 0; i < templateFilePaths.size(); i++) {
                String templateFile = templateFilePaths.get(i);
                String resultsFile = resultsFilePaths.get(i);
                templateFilepathsMappedToResultsFilepaths.put(templateFile, resultsFile);
            }
        }
    }

    public TreeMap<String, String> getTemplateXmlFilepathsMappedToResultsXmlFilepaths() {
        return templateFilepathsMappedToResultsFilepaths;
    }

    // setTemplateXmlFilepathsMappedToResultsXmlFilepaths MUST be set
    public void as() {
        for (Map.Entry<String, String> entry :
                templateFilepathsMappedToResultsFilepaths.entrySet()) {

            Xml template    = new Xml(entry.getKey());
            Xml results     = new Xml(entry.getValue());

            template.setXmlMappedTextBlocks();
            results.setXmlMappedTextBlocks();

            XmlComparer xmlComparer = new XmlComparer(template, results);
            if (xmlComparer.xmlElementsAreEqual()) {
                if (!xmlComparer.xmlAttributesAreEqual()) {

                    xmlComparer.setTemplateElements();
                    xmlComparer.setUnequalElements();

                    System.out.println(xmlComparer.getUnequalElements());
                }
            }
        }
    }


}
