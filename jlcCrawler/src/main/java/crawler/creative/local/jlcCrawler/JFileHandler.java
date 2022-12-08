package crawler.creative.local.jlcCrawler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JFileHandler {
    String[] exceptList = {"*", "/*", "*/", "/**"};

    public List<String> fileScan(File f) {
        List<String> jSource = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(f);

            while (scanner.hasNextLine()) {
                boolean needPass = false;

                String str = scanner.nextLine();
                for (String s : exceptList) {
                    if (str.startsWith(s)) {
                        needPass = true;
                        break;
                    }
                }
                if (needPass) {
                    jSource.add(str);
                }
            }

            return jSource;
        } catch (Exception e) {
            System.out.println("error occur wrong file or wrong character inside - detail : " + e);
        }
        return jSource;
    }
}
