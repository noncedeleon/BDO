import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class XmlTest {

    String xmlDefaultDirectory
            = "/users/jensen/Desktop/BDOs/xmlTestOne/xmlOne/3P01/TC 1 - 3P01.xml";
    String xmlCustomDirectory
            = "/users/jensen/Desktop/BDOs/xmlTest/test.xml";

    Xml xmlDefault = new Xml(xmlDefaultDirectory);
    Xml xmlCustom = new Xml(xmlCustomDirectory, "textBrick", "number");

    // Set and Get mapped xml is not tested

    @Test
    public void getXmlElement() throws Exception {
        assertEquals(xmlDefault.getElement(), "textBlock");
    }

    @Test
    public void getXmlElementCustom() throws Exception {
        assertEquals(xmlCustom.getElement(), "textBrick");
    }

    @Test
    public void getXmlAttribute() throws Exception {
        assertEquals(xmlDefault.getAttribute(), "id");
    }

    @Test
    public void getXmlAttributeCustom() throws Exception {
        assertEquals(xmlCustom.getAttribute(), "number");
    }

    @Test
    public void getXmlFilePath() throws Exception {
        assertEquals(xmlDefault.getFilePath(), xmlDefaultDirectory);
    }

    @Test
    public void getXmlFilePathCustom() throws Exception {
        assertEquals(xmlCustom.getFilePath(), xmlCustomDirectory);
    }

    @Test
    public void getXmlFileName() throws Exception {
        assertEquals(xmlDefault.getFileName(), "TC 1 - 3P01.xml");
    }

    @Test
    public void getXmlFileNameCustom() throws Exception {
        assertEquals(xmlCustom.getFileName(), "test.xml");
    }

    @Test
    public void getXmlParentFileName() throws Exception {
        assertEquals(xmlDefault.getParentFileName(), "3P01");
    }

    @Test
    public void getXmlParentFileNameCustom() throws Exception {
        assertEquals(xmlCustom.getParentFileName(), "xmlTest");
    }

}
