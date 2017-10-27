package com.test;

import java.util.*;

public class SimplePatternContainer implements PatternContainer {
    private TextLine pattern;
    private int distinctWordIndex = -1;
    private List<TextLine> lines = new LinkedList<>();
    private HashSet<String> changingWords = new HashSet<>();

    public SimplePatternContainer(TextLine patternSample) {
        lines.add(patternSample);
        this.pattern = patternSample;
    }

    @Override
    public TextLine getPattern() {
        return pattern;
    }

    @Override
    public List<TextLine> getLines() {
        return lines;
    }

    @Override
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

    @Override
    public PatternMatch tryMatch(TextLine line) {

        // 1. If line is consistent with pattern then words count should be the same in both lists
        if(line.getWords().size() != pattern.getWords().size()) {
            return PatternMatch.emptyMatch();
        }

        // 2. Check if sentences are different by one word only
        List<Integer> changingWordIndexes = new LinkedList<>();
        for (int i = 0; i < pattern.getWords().size(); i++) {
            if(!pattern.getWords().get(i).equalsIgnoreCase(line.getWords().get(i))) {
                changingWordIndexes.add(i);
            }

            if(changingWordIndexes.size() > 1) {
                return PatternMatch.emptyMatch();
            }
        }

        // this algorithm doesn't take into account matches to the lines that are already a match to a pattern
        return PatternMatch.fullMatch(pattern, line, changingWordIndexes.get(0));
    }
}
