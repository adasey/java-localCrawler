package crawler.creative.local.jlcCrawler.domain;

import lombok.*;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class AccessClass {
    private final String accessModifier;
    private final String checkAbstract;
    private final String className;
}
