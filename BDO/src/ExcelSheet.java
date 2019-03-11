import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ExcelSheet {

    private DirectoryNavigator directoryNavigator;
    private String excelMaster;
    private String excelCopy;
    private int columnInitial;
    private int rowOffset;
    private int columnIterator;
    private String element;
    private String attribute;
    private Map<String, String> tabNames;

    ExcelSheet(DirectoryNavigator directoryNavigator, String excelMaster, String excelCopy) {
        this.directoryNavigator = directoryNavigator;
        this.excelMaster = excelMaster;
        this.excelCopy = excelCopy;
        this.columnInitial = 3;
        this.rowOffset = 5;
        this.columnIterator = 5;
        this.element = "textBlock";
        this.attribute = "id";
//        this.tabNames = new TreeMap<>();
        this.tabNames = directoryNavigator.getSubDirectoryNames();
    }

    public void setColumnInitial(int columnInitial) {
        this.columnInitial = columnInitial;
    }

    public int getColumnInitial() {
        return columnInitial;
    }

    public void setRowOffset(int rowOffset) {
        this.rowOffset = rowOffset;
    }

    public int getRowOffset() {
        return rowOffset;
    }

    public void setColumnIterator(int columnIterator) {
        this.columnIterator = columnIterator;
    }

    public int getColumnIterator() {
        return columnIterator;
    }


    public void setElement(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }


    public String getExcelCopy() {
        return excelCopy;
    }

    public String getExcelMaster() {
        return excelMaster;
    }


    WritableWorkbook createCopy(String excelMaster, String excelCopy) {
        Workbook original;
        WritableWorkbook copy = null;
        try {
            original = Workbook.getWorkbook(new File(excelMaster));
            copy = Workbook.createWorkbook(new File(excelCopy), original);
        } catch (IOException | BiffException e) {
            e.printStackTrace();
        }
        return copy;
    }

    DirectoryNavigator getDirectoryNavigator() {
        return directoryNavigator;
    }


//    void setSubDirectoryNames() {
//        for (String subDirectory : directoryNavigator.getSubDirectories()) {
//            Path path = Paths.get(subDirectory);
//            tabNames.put(subDirectory, path.getName(path.getNameCount() - 1).toString());
//        }
//    }

    private void writeColumn(WritableWorkbook results,
                             TreeMap<String, String> xml,
                             String tabName) {

        WritableSheet sheet = results.getSheet(tabName);
        try {
            for (Map.Entry<String, String> entry : xml.entrySet()) {
                String textBlock = entry.getKey();
                String yesOrNo = entry.getValue();

                Label textBlockNumber = new Label(columnInitial, rowOffset, textBlock);
                sheet.addCell(textBlockNumber);
                columnInitial++;

                Label yesOrNoLabel = new Label(columnInitial, rowOffset, yesOrNo);
                sheet.addCell(yesOrNoLabel);
                columnInitial--;
                rowOffset++;
            }
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    void writeWorkbook(WritableWorkbook results) {
        // Should this be called here?

        directoryNavigator.setSubDirectoryNames();

        for (String tabName : tabNames.keySet()) {
            TreeMap<String, String> xml;

            DirectoryNavigator temp = new DirectoryNavigator(tabName);
            temp.setSubDirectories(temp.getRootDirectory());
            ArrayList<String> xmls = temp.getSubDirectories();

            int columns = 0;
            for (String file : xmls) {
                Xml testCase = new Xml(file, element, attribute);
                testCase.setXmlMappedTextBlocks();
                xml = testCase.getXmlMappedTextBlocks();
                writeColumn(results, xml, tabNames.get(tabName));
                rowOffset -= xml.size();
                columnInitial += columnIterator;
                columns++;
            }
            columnInitial -= columns * columnIterator;
        }
        try {
            results.write();
            results.close();
            System.out.println("Results spreadsheet written with xml comparison data");
        } catch (IOException | WriteException e) {
            e.printStackTrace();
        }
    }

}