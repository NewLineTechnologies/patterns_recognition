package com.test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class SmartPatternContainer implements PatternContainer {
    private TextLine pattern;
    private int distinctWordIndex = -1;
    private List<TextLine> lines = new LinkedList<>();
    private HashSet<String> changingWords = new HashSet<>();

    public SmartPatternContainer(TextLine patternSample) {
        lines.add(patternSample);
        this.pattern = patternSample;
    }

    public TextLine getPattern() {
        return pattern;
    }

    public List<TextLine> getLines() {
        return lines;
    }

    public HashSet<String> getChangingWords() {
        return changingWords;
    }

    @Override
    public int getDistinctWordIndex() {
        return distinctWordIndex;
    }

    @Override
    public void setDistinctWordIndex(int distinctWordIndex) {
        this.distinctWordIndex = distinctWordIndex;
    }

    public PatternMatch tryMatch(TextLine line) {
        if(distinctWordIndex == -1) {
            return match(pattern, line);
        }
        else {
            PatternMatch match = matchWithPattern(line);
            if(match.getKind().equals(MatchKind.PARTIAL)) {
                match = matchWithLines(line);
                if(match.getKind().equals(MatchKind.FULL)) {
                    // Line is a match to other line, but it's a partial match to a pattern, so create a new instance
                    match = PatternMatch.partialMatch(match.getPattern(), match.getMatch(), match.getDifferentWordIndex());
                }
            }

            return match;
        }
    }

    private PatternMatch match(TextLine supposedPattern, TextLine line) {

        // 1. If line is consistent with pattern then words count should be the same in both lists
        if(line.getWords().size() != supposedPattern.getWords().size()) {
            return PatternMatch.emptyMatch();
        }

        // 2. Check if sentences are different by one word only
        List<Integer> changingWordIndexes = new LinkedList<>();
        for (int i = 0; i < supposedPattern.getWords().size(); i++) {
            if(!supposedPattern.getWords().get(i).equalsIgnoreCase(line.getWords().get(i))) {
                changingWordIndexes.add(i);
            }

            if(changingWordIndexes.size() > 1) {
                return PatternMatch.emptyMatch();
            }
        }

        return PatternMatch.fullMatch(supposedPattern, line, changingWordIndexes.get(0));
    }

    /**
     * Match with pattern by the following rules:
     * - don't compare word at 'distinctWordIndex' index cause it should be different
     * - all other words should match
     * @param line
     * @return
     */
    private PatternMatch matchWithPattern(TextLine line) {
        List<Integer> changingWordIndexes = new LinkedList<>();
        for (int i = 0; i < pattern.getWords().size(); i++) {
            if(i == distinctWordIndex) {
                continue;
            }

            if(!pattern.getWords().get(i).equalsIgnoreCase(line.getWords().get(i))) {
                changingWordIndexes.add(i);
            }
        }

        if(changingWordIndexes.size() > 1) {
            return PatternMatch.emptyMatch();
        }
        else if(changingWordIndexes.size() == 1) {
            // Maybe there is a match to other lines should words at 'distinctWordIndex' index match
            return PatternMatch.partialMatch(pattern, line);
        }

        return PatternMatch.fullMatch(pattern, line, distinctWordIndex);
    }

    /**
     * Find a match to other lines
     * @param line
     * @return
     */
    private PatternMatch matchWithLines(TextLine line) {
        for (int i = 1; i < lines.size(); i++) {
            PatternMatch match = match(lines.get(i), line);
            if(match.getKind().equals(MatchKind.FULL)) {
                return match;
            }
        }

        return PatternMatch.emptyMatch();
    }
}
