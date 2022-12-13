package crawler.creative.local.jlcCrawler.codeProcess;

import crawler.creative.local.jlcCrawler.CodeSyntax;
import crawler.creative.local.jlcCrawler.domain.CodeValue;
import crawler.creative.local.jlcCrawler.domain.ExceptSyntax;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Slf4j
public class JFileAnalyzer {

    public List<String> fileScan(File f) {
        List<String> jSource = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(f);
            boolean isPass = false;

            while (scanner.hasNextLine()) {

                String str = scanner.nextLine();
                for (String s : ExceptSyntax.COMMENT.getList()) {
                    if (str.contains(s)) {
                        log.info("value when checked except : {}, {}", str, s);
                        isPass = true;
                        break;
                    }
                }

                if (!isPass) {
                    jSource.add(str);
                }

                isPass = false;
            }

            return jSource;
        } catch (Exception e) {
            System.out.println("error occur wrong file or wrong character inside - detail : " + e);
        }
        return jSource;
    }

    public CodeValue codeSyntaxAnalyzer(List<String> code) {
        CodeValue result = new CodeValue();

        for (String o : code) {
            List<String> collect = Arrays.stream(o.split(" ")).collect(Collectors.toList());
            log.info("check value : {}", collect);

            if (collect.contains(CodeSyntax.CLASS.get())) {
                int index = collect.indexOf(CodeSyntax.CLASS.get());
                result.getClassName().add(collect.get(index + 1));
                continue;
            }
            
            if (collect.contains(CodeSyntax.INTERFACE.get())) {
                int index = collect.indexOf(CodeSyntax.INTERFACE.get());
                result.getInterfaceName().add(collect.get(index - 1));
                continue;
            }

            if (collect.contains(CodeSyntax.ENUM.get())) {
                int index = collect.indexOf(CodeSyntax.INTERFACE.get());
                result.getInterfaceName().add(collect.get(index - 1));
                continue;
            }

            if (collect.contains(CodeSyntax.METHOD.get())) {
                boolean leave = false;
                int startCheck = collect.indexOf(CodeSyntax.METHOD.get());
                String methodCheck = collect.get(startCheck);
                int leftBracket = methodCheck.indexOf(CodeSyntax.METHOD.get());

                if (leftBracket >= 1) {
                    for (String check : ExceptSyntax.EXCEPT.getList()) {
                        if (methodCheck.contains(check)) {
                            leave = true;
                            break;
                        }
                    }

                    String method = methodCheck.substring(0, leftBracket);

                    if (leave || result.getClassName().contains(method)) {
                        leave = false;
                    } else {
                        result.getMethodName().add(method);
                    }
                }
            }
        }

        return result;
    }
}
