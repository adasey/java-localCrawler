package crawler.creative.local.jlcCrawler.codeProcess;

import crawler.creative.local.jlcCrawler.CodeSyntax;
import crawler.creative.local.jlcCrawler.domain.CodeValue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class JFileAnalyzer {
    String[] exceptList = {"*", "/*", "*/", "/**", "//"};

    public List<String> fileScan(File f) {
        List<String> jSource = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(f);

            while (scanner.hasNextLine()) {
                boolean isPass = false;

                String str = scanner.nextLine();
                for (String s : exceptList) {
                    if (str.contains(s)) {
                        isPass = true;
                        break;
                    }
                }

                if (!isPass) {
                    jSource.add(str);
                }
            }

            return jSource;
        } catch (Exception e) {
            System.out.println("error occur wrong file or wrong character inside - detail : " + e);
        }
        return jSource;
    }

    public List<String> codeSyntaxAnalyzer(List<String> code) {
        CodeValue result = new CodeValue();

        for (String o : code) {
            List<String> collect = Arrays.stream(o.split(" ")).collect(Collectors.toList());

            if (collect.contains(CodeSyntax.CLASS.getName())) {
                int index = collect.indexOf(CodeSyntax.CLASS.getName());
                result.add(collect.get(index + 1));
            }
        }

        return result;
    }
}
