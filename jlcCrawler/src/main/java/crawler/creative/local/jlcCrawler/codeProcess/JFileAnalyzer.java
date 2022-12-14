package crawler.creative.local.jlcCrawler.codeProcess;

import crawler.creative.local.jlcCrawler.CodeSyntax;
import crawler.creative.local.jlcCrawler.domain.*;
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
        List<String> tempClass = new ArrayList<>();
        List<String> tempInterface = new ArrayList<>();
        List<String> tempEnum = new ArrayList<>();
        List<String> tempMethod = new ArrayList<>();

        for (String o : code) {
            List<String> collect = Arrays.stream(o.trim().split(" ")).collect(Collectors.toList());
            collect.removeAll(Arrays.asList("", null));

            if (o.contains(CodeSyntax.CLASS.get())) {
                int index = collect.indexOf(CodeSyntax.CLASS.get());

                try {
                    if (collect.get(index).equals(CodeSyntax.CLASS.get())) {
                        if (!tempClass.contains(collect.get(index + 1))) {
                            tempClass.add(collect.get(index + 1));

                            int check = index - 1;
                            String abstractCheck = "";
                            AccessClass accessClass = null;
                            check = accessCheck(collect, check);

                            if (collect.get(check).contains("abstract")) {
                                abstractCheck = "abstract";
                                check--;
                            }

                            if (check < 0) {
                                check = 0;
                            }

                            accessClass = new AccessClass("package private", abstractCheck, collect.get(index + 1));
                            if (ExceptSyntax.PUBLIC.getList().contains(collect.get(check))) {
                                accessClass = new AccessClass(collect.get(check), abstractCheck, collect.get(index + 1));
                            }
                            result.getClasses().add(accessClass);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("error occur wrong file or wrong class inside - detail : " + e);
                    continue;
                }
                continue;
            }
            
            if (o.contains(CodeSyntax.INTERFACE.get())) {
                int index = collect.indexOf(CodeSyntax.INTERFACE.get());

                try {
                    if (collect.get(index).equals(CodeSyntax.INTERFACE.get())) {
                        if (!tempInterface.contains(collect.get(index + 1))) {
                            tempInterface.add(collect.get(index + 1));
                            int check = accessCheck(collect, index);

                            AccessInterface accessInterface = new AccessInterface("package private", collect.get(index + 1));
                            if (ExceptSyntax.PUBLIC.getList().contains(collect.get(check))) {
                                accessInterface = new AccessInterface(collect.get(check), collect.get(index + 1));
                            }
                            result.getInterfaces().add(accessInterface);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("error occur wrong file or wrong interface inside - detail : " + e);
                    continue;
                }
                continue;
            }

            if (o.contains(CodeSyntax.ENUM.get())) {
                int index = collect.indexOf(CodeSyntax.ENUM.get());

                try {
                    if (collect.get(index).equals(CodeSyntax.INTERFACE.get())) {
                        if (!tempEnum.contains(collect.get(index + 1))) {
                            tempEnum.add(collect.get(index + 1));
                            int check = accessCheck(collect, index);

                            AccessEnum accessEnum = new AccessEnum("package private", collect.get(index + 1));
                            if (ExceptSyntax.PUBLIC.getList().contains(collect.get(check))) {
                                accessEnum = new AccessEnum(collect.get(check), collect.get(index + 1));
                            }
                            result.getEnums().add(accessEnum);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("error occur wrong file or wrong enum inside - detail : " + e);
                    continue;
                }
                continue;
            }

            if (o.contains(CodeSyntax.METHOD.get())) {
                boolean isBreak = false;

                for (String e : ExceptSyntax.EXCEPT.getList()) {
                    if (collect.contains(e)) {
                        isBreak = true;
                        break;
                    }
                }

                String methodCheck = "";
                String check = "";
                int bracketFirst = 1;

                int i = 0;
                for (String val : collect) {
                    if (val.matches("(.*)\\((.*)")) {
                        if (val.startsWith("(")) {
                            isBreak = true;
                            break;
                        }

                        methodCheck = val;
                        bracketFirst = i;
                    }
                    i++;
                }

                if (bracketFirst >= 1) {
                    check = collect.get(bracketFirst - 1);
                    if (!isBreak) {
                        int methodEnd = methodCheck.indexOf(CodeSyntax.METHOD.get());
                        String method = methodCheck.substring(0, methodEnd);
                        if (!(method.matches("(.*)\\.(.*)") || method.matches("(.*)\\[(.*)")) & !(check.matches("[=+\\-!?&|^`,:.~*%/\\\\]+") ||
                                check.contains("new") || check.contains("return")) || (check.contains("<") & check.contains(">"))) {
                            if (!tempMethod.contains(method) & !tempClass.contains(method)) {
                                tempMethod.add(method);
                                MemberMethod memberMethod = new MemberMethod("package private", check, method);

                                if ((bracketFirst - 2) >= 0) {
                                    int checkState = accessCheck(collect, bracketFirst - 2);
                                    String access = collect.get(checkState);

                                    if (ExceptSyntax.PUBLIC.getList().contains(access)) {
                                        memberMethod = new MemberMethod(access, check, method);
                                    }
                                }

                                result.getMethods().add(memberMethod);
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    private int accessCheck(List<String> check, int index) {
        try {
            if (check.get(index).contains("static") || check.get(index).contains("final")) {
                return accessCheck(check, --index);
            } else {
                return index;
            }
        } catch (Exception e) {
            return 0;
        }
    }
}
