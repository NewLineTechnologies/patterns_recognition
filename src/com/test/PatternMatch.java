package com.test;

public class PatternMatch {
    private TextLine pattern;
    private TextLine match;
    private int differentWordIndex;
    private MatchKind kind;

    private PatternMatch() {
    }

    public TextLine getPattern() {
        return pattern;
    }

    private void setPattern(TextLine pattern) {
        this.pattern = pattern;
    }

    public TextLine getMatch() {
        return match;
    }

    private void setMatch(TextLine match) {
        this.match = match;
    }

    public int getDifferentWordIndex() {
        return differentWordIndex;
    }

    private void setDifferentWordIndex(int differentWordIndex) {
        this.differentWordIndex = differentWordIndex;
    }

    public MatchKind getKind() {
        return kind;
    }

    private void setKind(MatchKind kind) {
        this.kind = kind;
    }

    public static PatternMatch emptyMatch() {
        PatternMatch patternMatch = new PatternMatch();
        patternMatch.setKind(MatchKind.EMPTY);
        return patternMatch;
    }

    public static PatternMatch partialMatch(TextLine pattern, TextLine match) {
        PatternMatch patternMatch = new PatternMatch();
        patternMatch.setKind(MatchKind.PARTIAL);
        patternMatch.setPattern(pattern);
        patternMatch.setMatch(match);
        return patternMatch;
    }

    public static PatternMatch partialMatch(TextLine pattern, TextLine match, int differentWordIndex) {
        PatternMatch patternMatch = new PatternMatch();
        patternMatch.setKind(MatchKind.PARTIAL);
        patternMatch.setPattern(pattern);
        patternMatch.setMatch(match);
        patternMatch.setDifferentWordIndex(differentWordIndex);
        return patternMatch;
    }

    public static PatternMatch fullMatch(TextLine pattern, TextLine match, int differentWordIndex) {
        PatternMatch patternMatch = new PatternMatch();
        patternMatch.setKind(MatchKind.FULL);
        patternMatch.setPattern(pattern);
        patternMatch.setMatch(match);
        patternMatch.setDifferentWordIndex(differentWordIndex);
        return patternMatch;
    }
}
