package crawler.creative.local.jlcCrawler.domain;

import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum ExceptSyntax {
    COMMENT(Arrays.asList("*", "/*", "*/", "/**", "//")),
    EXCEPT(Arrays.asList("catch", "else", "for", "if", "switch", "while")),
    PUBLIC(Arrays.asList("public", "private", "protected"));

    private final List<String> value;

    public List<String> getList() {
        return value;
    }
}
