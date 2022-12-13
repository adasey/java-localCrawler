package crawler.creative.local.jlcCrawler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeSyntax {
    CLASS("class"),
    INTERFACE("interface"),
    METHOD("("),
    ANNOTATION("@"),
    VAR("=");

    private final String name;
}
