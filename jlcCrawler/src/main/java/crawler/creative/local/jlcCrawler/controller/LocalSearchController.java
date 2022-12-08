package crawler.creative.local.jlcCrawler.controller;

import crawler.creative.local.jlcCrawler.JFileHandler;
import crawler.creative.local.jlcCrawler.service.LocalFileSearcher;
import crawler.creative.local.jlcCrawler.domain.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping
@Controller
public class LocalSearchController {
    private LocalFileSearcher localFileSearcher;

    @GetMapping
    public String startSearch(Model model) {
        LocalFileSearcher localFileSearcher = new LocalFileSearcher(".\\src\\main\\bunch");
        this.localFileSearcher = localFileSearcher;
        List<Value> collect = localFileSearcher.getLocalFile().getJarTitleList().stream().map(this::fileToValue).collect(Collectors.toList());
        System.out.println("collect = " + collect);
        model.addAttribute("files", collect);
        return "index";
    }

    @GetMapping("/{id}")
    public String foldersJFileDetail(@PathVariable int id, Model model) {
        File which = localFileSearcher.getLocalFile().getJarTitleList().get(id);
        List<File> value = new ArrayList<>();
        localFileSearcher.allFilesSearch(value, which);
        localFileSearcher.getLocalFile().getAllFileMap().put(id, value);

        List<Value> collect = value.stream().map(this::fileToValue).collect(Collectors.toList());
        model.addAttribute("javaFiles", collect);
        return "index";
    }

    private Value fileToValue(File f) {
        return Value.builder().fileDir(f.toString()).build();
    }

    @GetMapping("/{id}/{val}")
    private String checkDetailOfFiles(@PathVariable int id, @PathVariable int val, Model model) {
        List<File> files = localFileSearcher.getLocalFile().getAllFileMap().get(id);
        File f = files.get(val);
        JFileHandler jFileHandler = new JFileHandler();
        jFileHandler.fileScan(f);
//        model.addAttribute("javaDetails", collect);
        return "index";
    }
}
