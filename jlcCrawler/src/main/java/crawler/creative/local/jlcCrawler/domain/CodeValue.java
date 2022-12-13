package crawler.creative.local.jlcCrawler.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
public class CodeValue {
    private final List<String> className = new ArrayList<>();
    private final List<String> interfaceName = new ArrayList<>();
    private final List<String> methodName = new ArrayList<>();
    private final List<String> enumName = new ArrayList<>();
    private final List<String> varName = new ArrayList<>();
    private final List<String> annoName = new ArrayList<>();
}
