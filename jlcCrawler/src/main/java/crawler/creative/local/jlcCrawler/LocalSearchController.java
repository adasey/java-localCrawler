package crawler.creative.local.jlcCrawler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
@Controller
public class LocalSearchController {
    private LocalFileSearcher localFileSearcher;

    @GetMapping
    public String startSearch(Model model) {
        LocalFileSearcher localFileSearcher = new LocalFileSearcher(".\\src\\main\\resources\\bunch");
        model.addAttribute("files", localFileSearcher.getJarTitleList());
        return "index";
    }

    @GetMapping("/folders")
    public String valueFolderSearched() {
        return localFileSearcher.getJarTitleList().toString();
    }
}
