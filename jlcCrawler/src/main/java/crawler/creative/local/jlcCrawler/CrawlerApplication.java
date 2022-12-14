package crawler.creative.local.jlcCrawler;

import crawler.creative.local.jlcCrawler.codeProcess.JFileAnalyzer;
import crawler.creative.local.jlcCrawler.codeProcess.LocalFileSearcher;
import crawler.creative.local.jlcCrawler.domain.*;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CrawlerApplication {
    private static final List<String> folderName = List.of("class", "interface", "enum", "method");
    private static final String locate = ".\\src\\main\\bunch";
    private static final String fileLocate = ".\\src\\main\\resources\\csvPackage";
    private static final LocalFileSearcher insertCSVLocate = new LocalFileSearcher(fileLocate);
    private static final LocalFileSearcher localFileSearcher = new LocalFileSearcher(locate);

    public static void main(String[] args) {
        List<Value> needInsert = insertCSVLocate.getLocalFile().getJarTitleList().stream().map(CrawlerApplication::fileToValue).collect(Collectors.toList());
        List<Value> collect = localFileSearcher.getLocalFile().getJarTitleList().stream().map(CrawlerApplication::fileToValue).collect(Collectors.toList());

        int i = 0;
//        folder(collect);
//        folderGen(needInsert);
        for (Value v : collect) {
            File which = localFileSearcher.getLocalFile().getJarTitleList().get(i);

            List<File> value = new ArrayList<>();
            localFileSearcher.allFilesSearch(value, which);

            for (File f : value) {
                JFileAnalyzer jFileAnalyzer = new JFileAnalyzer();
                BodyContent bodyContent = new BodyContent(jFileAnalyzer.fileScan(f));
                CodeValue strings = jFileAnalyzer.codeSyntaxAnalyzer(bodyContent.getContent());

                String fileDir = needInsert.get(i).getFileDir();
                String pathBack = f.toString().replace(which.toString(), "").replace("\\", ".").replace(".java", ".csv");
                File csvClassFolder = new File(fileDir + "\\class\\" + pathBack.substring(1));
                File csvInterfaceFolder = new File(fileDir + "\\interface\\" + pathBack);
                File csvEnumFolder = new File(fileDir + "\\enum\\" + pathBack);
                File csvMethodFolder = new File(fileDir + "\\method\\" + pathBack);
                BufferedWriter bwc = null;
                BufferedWriter bwi = null;
                BufferedWriter bwe = null;
                BufferedWriter bwm = null;

                try {
                    if (strings.getClasses().size() > 0) {
                        bwc = new BufferedWriter(new FileWriter(csvClassFolder, false));
                        String bondingC = "";

                        bondingC = "access,abstract,class";
                        bwc.write(bondingC);
                        bwc.newLine();

                        for (AccessClass ac : strings.getClasses()) {
                            bondingC = ac.getAccessModifier() + "," + ac.getCheckAbstract() + "," + ac.getClassName();

                            bwc.write(bondingC);
                            bwc.newLine();
                        }
                    }

                    if (strings.getInterfaces().size() > 0) {
                        bwi = new BufferedWriter(new FileWriter(csvInterfaceFolder, false));
                        String bondingI = "";

                        bondingI = "access,interface";
                        bwi.write(bondingI);
                        bwi.newLine();

                        for (AccessInterface ai : strings.getInterfaces()) {
                            bondingI = ai.getAccessModifier() + "," + ai.getInterfaceName();

                            bwi.write(bondingI);
                            bwi.newLine();
                        }
                    }

                    if (strings.getEnums().size() > 0) {
                        bwe = new BufferedWriter(new FileWriter(csvEnumFolder, false));
                        String bondingE = "";

                        bondingE = "access,enum";
                        bwe.write(bondingE);
                        bwe.newLine();

                        for (AccessEnum ae : strings.getEnums()) {
                            bondingE = ae.getAccessModifier() + "," + ae.getEnumName();

                            bwe.write(bondingE);
                            bwe.newLine();
                        }
                    }

                    if (strings.getMethods().size() > 0) {
                        bwm = new BufferedWriter(new FileWriter(csvMethodFolder, false));
                        String bondingM = "";

                        bondingM = "access,return,method";
                        bwm.write(bondingM);
                        bwm.newLine();

                        for (MemberMethod mm : strings.getMethods()) {
                            bondingM = mm.getAccessModifier() + "," + mm.getReturnType() + "," + mm.getMethodName();

                            bwm.write(bondingM);
                            bwm.newLine();
                        }
                    }
                } catch (IOException ie) {
                    log.error(String.valueOf(ie));
                } finally {
                    try {
                        if (bwc != null) {
                            bwc.flush();
                            bwc.close();
                        }

                        if (bwi != null) {
                            bwi.flush();
                            bwi.close();
                        }

                        if (bwe != null) {
                            bwe.flush();
                            bwe.close();
                        }

                        if (bwm != null) {
                            bwm.flush();
                            bwm.close();
                        }
                    } catch (IOException ie) {
                        log.error(String.valueOf(ie));
                    }
                }
            }
            i++;
        }
    }

    private static Value fileToValue(File f) {
        return Value.builder().fileDir(f.toString()).build();
    }

    private static void folder(List<Value> vs) {
        for (Value v : vs) {
            log.info("path of folder : {}", fileLocate + v.getFileDir());
            File Folder = new File(fileLocate + v.getFileDir().replace(locate, ""));

            if (!Folder.exists()) {
                try {
                    boolean mkdir = Folder.mkdir();
                    if (mkdir) {
                        log.info("folder generator.");
                    } else {
                        log.info("folder already exist.");
                    }
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        }
    }

    private static void folderGen(List<Value> vs) {
        for (Value v : vs) {
            log.info("path of folder : {}", fileLocate + v.getFileDir());

            for (String fol : folderName) {
                log.info("value of post : {}", fol);
                File Folder = new File(v.getFileDir() + "\\" + fol);

                if (!Folder.exists()) {
                    try {
                        boolean mkdir = Folder.mkdir();
                        if (mkdir) {
                            log.info("folder generator.");
                        } else {
                            log.info("folder already exist.");
                        }
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
            }
        }
    }
}
