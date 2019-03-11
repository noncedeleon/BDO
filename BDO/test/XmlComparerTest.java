import org.junit.Test;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class XmlComparerTest {

    String xmlDefaultDirectory
            = "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P01/TC 1 - 3P01.xml";

    String xmlResultsDirectory
            = "/users/jensen/Desktop/BDOs/xmlTestTwo/xmlTwo/3P01/TC 1 - 3P01.xml";

    Xml defaultXml = new Xml(xmlDefaultDirectory);
    Xml resultsXml = new Xml(xmlResultsDirectory);


    XmlComparer xmlComparer = new XmlComparer(defaultXml, resultsXml);

    @Test
    public void getDefaultXml() throws Exception {
        assertEquals(defaultXml, xmlComparer.getXmlTemplate());
    }

    @Test
    public void getResultsXml() throws Exception {
        assertEquals(resultsXml, xmlComparer.getXmlResult());
    }

    @Test
    public void xmlElementIsEqual() throws Exception {
        assertTrue(xmlComparer.xmlElementIsEqual());
    }

    @Test
    public void xmlElementsAreEqual() throws Exception {
        assertTrue(xmlComparer.xmlElementsAreEqual());
    }

    @Test
    public void xmlAttributeIsEqual() throws Exception {
        assertTrue(xmlComparer.xmlAttributeIsEqual());
    }

    @Test
    public void xmlAttributesAreEqual() throws Exception {
        assertTrue(xmlComparer.xmlAttributesAreEqual());
    }

    @Test
    public void defaultFilenameEqualsResultsFilename() throws Exception {
        assertTrue(xmlComparer.templateFilenameEqualsResultsFilename());
    }

    @Test
    public void defaultFilepathEqualsResultsFilepath() throws Exception {
        assertFalse(xmlComparer.templateFilepathEqualsResultsFilepath());
    }

    @Test
    public void templateParentFilenameEqualsResultsParentFilename() throws Exception {
        assertTrue(xmlComparer.templateParentFilenameEqualsResultsParentFilename());
    }

    @Test
    public void getTemplateElements() throws Exception {
        assertEquals(xmlComparer.getTemplateElements(), resultsXml.getXmlMappedTextBlocks().keySet());
        assertEquals(xmlComparer.getTemplateElements(), defaultXml.getXmlMappedTextBlocks().keySet());
        assertEquals(resultsXml.getXmlMappedTextBlocks().keySet(),
                defaultXml.getXmlMappedTextBlocks().keySet());
    }

    @Test
    public void getUnequalElements() throws Exception {
        Map<String, String> errors = new TreeMap<>();
        Map<String, Map<String, String>> fileAndErrors = new TreeMap<>();

        errors.put("100", "N");
        fileAndErrors.put(xmlResultsDirectory, errors);

        xmlComparer.setUnequalElements();
        for (Map.Entry<String, Map<String, String>> entry : fileAndErrors.entrySet()) {
            assertEquals(fileAndErrors.keySet(), xmlComparer.getUnequalElements().keySet());
            Map<String, String> error = entry.getValue();
            assertEquals(error.keySet(), errors.keySet());
            assertEquals(error.values(), errors.values());
        }

    }
}
