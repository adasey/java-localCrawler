package crawler.creative.local.jlcCrawler;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Slf4j
public class LocalFileSearcher {
    private final LocalFile localFile = new LocalFile();
    private List<File> jarTitleList = new ArrayList<>();

    public LocalFileSearcher(String dirPath) {
        File dir = new File(dirPath);
        onlyFolderSearch(dir);

//        for (File f : localFile.getJarTitleList()) {
//            List<File> files = allFilesAndFolderSearch(f);
//            localFile.setAllFolderList(files);
//        }

        File f = localFile.getJarTitleList().get(0);
        List<File> test = new ArrayList<>();
        allFolderSearch(test, f);
        List<File> value = new ArrayList<>();
        for (File t : test) {
            allFilesSearch(value, t);
        }
        System.out.println("test = " + test);
        System.out.println("value = " + value);
    }

    private List<File> allFilesSearch(List<File> eachFiles, File dir) {
        File[] files = dir.listFiles();

        if (files != null) {
            if (files.length > 0) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        allFilesSearch(eachFiles, f);
                    } else {
                        eachFiles.add(f);
                    }
                }
            }
        }

        return eachFiles;
    }

    private void allFolderSearch(List<File> eachFolderFiles, File dir) {
        FilenameFilter noCodeFileFilter = (path, name) -> !name.contains(".java");

        File[] files = dir.listFiles(noCodeFileFilter);

        if (files != null) {
            if (files.length > 0) {
                for (File f : files) {
                    eachFolderFiles.add(f);
                    allFolderSearch(eachFolderFiles, f);
                }
            }
        }
    }

    private void onlyFolderSearch(File dir) {
        FilenameFilter noCodeFileFilter = (path, name) -> !name.contains(".java");

        File[] files = dir.listFiles(noCodeFileFilter);
        if (files != null) {
            if (files.length > 0) {
                this.localFile.setJarTitleList(Arrays.asList(files));
            }
        }
    }
}
