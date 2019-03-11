import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class DirectoryNavigator {

    private String rootDirectory;
    private Map<String, String> subDirectoryNames;
    private ArrayList<String> subDirectories;
    private ArrayList<String> allFileNames;
    private ArrayList<String> allFilePaths;

    public DirectoryNavigator(String rootDirectory) {
        this.rootDirectory = rootDirectory;
        this.subDirectories = new ArrayList<>();
        this.subDirectoryNames = new TreeMap<>();
    }

    public String getRootDirectory() {
        return rootDirectory;
    }

    public void setSubDirectories(String rootDirectory) {
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

    public ArrayList<String> getSubDirectories() {
        return subDirectories;
    }


    // MUST run setSubDirectories as first step
    public void setSubDirectoryNames() {
//        setSubDirectories(rootDirectory);

        for (String subDirectory : getSubDirectories()) {
            Path path = Paths.get(subDirectory);
            subDirectoryNames.put(subDirectory, path.getName(path.getNameCount() - 1).toString());
        }

    }

    public Map<String, String> getSubDirectoryNames() {
        return subDirectoryNames;
    }


    // MUST run setSubDirectories as first step
    public void setAllFilePaths(String rootDirectory) {
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


    // MUST run setSubDirectories as first step
    public void setAllFileNames() {
        ArrayList<String> filenames = new ArrayList<>();

        for (String subDirectory : subDirectories) {
            File directory = new File(subDirectory);
            File[] xmls = directory.listFiles();

            for (File file : xmls) {
                if (file.isFile() && !file.isHidden()) {
                    filenames.add(file.getName());
                }
            }
        }
        Collections.sort(filenames);
        allFileNames = filenames;
    }

    public ArrayList<String> getAllFileNames() {
        return allFileNames;
    }

}
