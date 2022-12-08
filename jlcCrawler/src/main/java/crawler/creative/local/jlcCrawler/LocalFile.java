package crawler.creative.local.jlcCrawler;

import lombok.*;

import java.io.File;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocalFile {
    private List<File> jarTitleList;
    private List<File> allFolderList;
}
