package crawler.creative.local.jlcCrawler;

import crawler.creative.local.jlcCrawler.codeProcess.LocalFileSearcher;

public class CrawlerApplication {

    public static void main(String[] args) {
        LocalFileSearcher localFileSearcher = new LocalFileSearcher(".\\src\\main\\resources\\bunch");
        System.out.println("getJarTitleList = " + localFileSearcher.getLocalFile().getJarTitleList().toString());
//        System.out.println("getAllFolderList = " + localFileSearcher.getLocalFile().getAllFolderList().toString());
    }
}
