package yifu.matcher;

public class MatcherFactory {

    public static Matcher createSimpleMatcher(String namePath, String phonePath) {
        return new InMemoryMatcher(namePath, phonePath);
    }
}
