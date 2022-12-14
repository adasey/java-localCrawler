package crawler.creative.local.jlcCrawler.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
public class CodeValue {
    private final List<AccessClass> classes = new ArrayList<>();
    private final List<AccessInterface> interfaces = new ArrayList<>();
    private final List<MemberMethod> methods = new ArrayList<>();
    private final List<AccessEnum> enums = new ArrayList<>();
    private final List<String> varName = new ArrayList<>();
    private final List<String> annoName = new ArrayList<>();
}
