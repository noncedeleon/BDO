import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class FileFinder {

    private String rootDirectory;
    private ArrayList<String> subDirectories;
    private ArrayList<String> allFilePaths;

    FileFinder(String rootDirectory) {
        this.rootDirectory = rootDirectory;
        subDirectories = new ArrayList<>();
    }

    String getRootDirectory() {
        return rootDirectory;
    }

    public void setRootDirectory(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    void setSubDirectories(String rootDirectory) {

        File filePath = new File(rootDirectory);

        if (!filePath.isHidden() && filePath.isDirectory()) {
            File[] files = filePath.listFiles();
            for (File file : files) {
                if (!file.isHidden()) {
                    subDirectories.add(file.getAbsolutePath());
                }
            }
        }
        Collections.sort(subDirectories);
    }

    ArrayList<String> getSubDirectories() {
        return subDirectories;
    }

    public void setAllFilePaths() {
        ArrayList<String> allFiles = new ArrayList<>();

        for (String subDirectory : subDirectories) {
            File directory = new File(subDirectory);
            File[] xmls = directory.listFiles();

            for (File file : xmls) {
                if (file.isFile() && !file.isHidden()) {
                    allFiles.add(file.getAbsolutePath());
                }
            }
        }
        Collections.sort(allFiles);
        allFilePaths = allFiles;
    }

    public ArrayList<String> getAllFilePaths() {
        return allFilePaths;
    }

//    public TreeMap<String, TreeMap<String, String>> getMappedTextBlocks() {
//        return mappedTextBlocks;
//    }

}
