package crawler.creative.local.jlcCrawler.domain;

import lombok.*;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class AccessInterface {
    private final String accessModifier;
    private final String interfaceName;
}
