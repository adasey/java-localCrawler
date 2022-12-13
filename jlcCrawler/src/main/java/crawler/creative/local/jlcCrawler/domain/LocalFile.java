package crawler.creative.local.jlcCrawler.domain;

import lombok.*;

import java.io.File;
import java.util.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocalFile {
    private List<File> jarTitleList;
    private List<File> allFolderList;
    private Map<Integer ,List<File>> allFileMap = new HashMap<>();
}
