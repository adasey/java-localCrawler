package crawler.creative.local.jlcCrawler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CodeSyntax {
    CLASS("class"),
    INTERFACE("interface"),
    METHOD("("),
    ANNOTATION("@"),
    ENUM("enum"),
    VAR("=");

    private final String name;

    public String get() {
        return this.name;
    }
}
