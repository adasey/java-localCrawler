package crawler.creative.local.jlcCrawler.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class MemberMethod {
    private final String accessModifier;
    private final String returnType;
    private final String methodName;
}
