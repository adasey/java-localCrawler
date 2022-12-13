package crawler.creative.local.jlcCrawler.controller;

import crawler.creative.local.jlcCrawler.CodeSyntax;
import crawler.creative.local.jlcCrawler.codeProcess.JFileAnalyzer;
import crawler.creative.local.jlcCrawler.domain.BodyContent;
import crawler.creative.local.jlcCrawler.codeProcess.LocalFileSearcher;
import crawler.creative.local.jlcCrawler.domain.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping
@Slf4j
@Controller
public class LocalSearchController {
    private LocalFileSearcher localFileSearcher;

    @GetMapping
    public String startSearch(Model model) {
        LocalFileSearcher localFileSearcher = new LocalFileSearcher(".\\src\\main\\bunch");
        this.localFileSearcher = localFileSearcher;
        List<Value> collect = localFileSearcher.getLocalFile().getJarTitleList().stream().map(this::fileToValue).collect(Collectors.toList());
        model.addAttribute("files", collect);
        return "index";
    }

    @GetMapping("/files/{id}")
    public String foldersJFileDetail(@PathVariable int id, Model model) {
        File which = localFileSearcher.getLocalFile().getJarTitleList().get(id);
        List<File> value = new ArrayList<>();
        localFileSearcher.allFilesSearch(value, which);
        localFileSearcher.getLocalFile().getAllFileMap().put(id, value);

        List<Value> collect = value.stream().map(this::fileToValue).collect(Collectors.toList());
        model.addAttribute("fileIndex", id);
        model.addAttribute("javaFiles", collect);
        return "index";
    }

    private Value fileToValue(File f) {
        return Value.builder().fileDir(f.toString()).build();
    }

    @GetMapping("/files/{id}/details/{val}")
    private String checkDetailOfFiles(@PathVariable int id, @PathVariable int val, @RequestParam(required = false) String syntax, Model model) {
        List<File> files = localFileSearcher.getLocalFile().getAllFileMap().get(id);
        File f = files.get(val);
        JFileAnalyzer jFileAnalyzer = new JFileAnalyzer();
        BodyContent bodyContent = new BodyContent(jFileAnalyzer.fileScan(f));

        List<String> strings = new ArrayList<>();
        if (syntax != null) {
            log.info("check what happen : {}", syntax);
            if (syntax.equals(CodeSyntax.CLASS.getName())) {
                strings = jFileAnalyzer.codeObjectAnalyzer(bodyContent.getContent());
            }
        }

        model.addAttribute("fileIndex", id);
        model.addAttribute("javaClass", strings);
        model.addAttribute("javaDetails", bodyContent);
        return "fileDetail";
    }
}
