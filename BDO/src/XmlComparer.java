import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class XmlComparer {

    private Xml xmlTemplate;
    private Xml xmlResult;
    private TreeMap<String, String> xmlTemplateMapping;
    private TreeMap<String, String> xmlResultMapping;
    private Set<String> xmlTemplateElements;
    private Map<String, Map<String, String>> testCaseUnequalElements;

    // is xmlTemplateMapping & xmlResultMapping in here as a functional call good form?
    public XmlComparer(Xml xmlTemplate, Xml xmlResult) {
        this.xmlTemplate                = xmlTemplate;
        this.xmlTemplateMapping         = xmlTemplate.getXmlMappedTextBlocks();
        this.xmlResult                  = xmlResult;
        this.xmlResultMapping           = xmlResult.getXmlMappedTextBlocks();
        this.xmlTemplateElements        = new TreeSet<>();
        this.testCaseUnequalElements    = new TreeMap<>();
    }

    public Xml getXmlTemplate() {
        return xmlTemplate;
    }

    public Xml getXmlResult() {
        return xmlResult;
    }

    public boolean xmlElementIsEqual() {
        return xmlTemplate.getElement()
                .equals(xmlResult.getElement());
    }

    public boolean xmlElementsAreEqual() {
        boolean xmlElementsAreEqual = false;

        if(xmlElementIsEqual()) {
            List<String> templateElements
                    = new ArrayList<>(xmlTemplate.getXmlMappedTextBlocks().keySet());
            List<String> resultsElements
                    = new ArrayList<>(xmlResult.getXmlMappedTextBlocks().keySet());
            xmlElementsAreEqual = templateElements.equals(resultsElements);
        }
        return xmlElementsAreEqual;
    }


    public boolean xmlAttributeIsEqual() {
        return xmlTemplate
                .getAttribute()
                .equals(xmlResult.getAttribute());
    }

    public boolean xmlAttributesAreEqual() {
        boolean isEqual = false;

        if (xmlAttributeIsEqual()) {
            List<String> templateAttributes = new ArrayList<>(xmlTemplateMapping.values());
            List<String> resultsElements = new ArrayList<>(xmlResultMapping.values());

            isEqual = templateAttributes.equals(resultsElements);
        }
        return isEqual;
    }


    public boolean templateFilenameEqualsResultsFilename() {
        return xmlTemplate
                .getFileName()
                .equals(xmlResult.getFileName());
    }

    public boolean templateFilepathEqualsResultsFilepath() {
        return xmlTemplate
                .getFilePath()
                .equals(xmlResult.getFilePath());
    }

    public boolean templateParentFilenameEqualsResultsParentFilename() {
        return xmlTemplate
                .getParentFileName()
                .equals(xmlResult.getParentFileName());
    }


    public void setTemplateElements() {
        if (xmlElementsAreEqual()) {
            xmlTemplateElements.addAll(xmlTemplateMapping.keySet());
        } else {
            System.out.println("Template and result xml elements are not identical. Check result xml file.");
        }
    }

    // Need to setTemplateElements()
    public Set<String> getTemplateElements() {
        if (xmlTemplateElements.isEmpty()) {
            setTemplateElements();
        }
        return xmlTemplateElements;
    }

    // Need to setTemplateElements()
    public void setUnequalElements() {

        Map<String, String> unequalElements = new TreeMap<>();
        // Seems like bad programming
        if (xmlTemplateElements.isEmpty()) {
            setTemplateElements();
        }
        for (String element : xmlTemplateElements) {
            if (!xmlTemplateMapping.get(element).equals(xmlResultMapping.get(element))) {
                unequalElements.put(element, xmlResultMapping.get(element));
            }
        }
        testCaseUnequalElements.put(xmlResult.getFilePath(), unequalElements);
    }

    public Map<String, Map<String, String>> getUnequalElements() {
        return testCaseUnequalElements;
    }

}
