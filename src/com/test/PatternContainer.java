package com.test;

import java.util.HashSet;
import java.util.List;

public interface PatternContainer {
    TextLine getPattern();

    List<TextLine> getLines();

    HashSet<String> getChangingWords();

    int getDistinctWordIndex();
    void setDistinctWordIndex(int index);

    PatternMatch tryMatch(TextLine line);

    default void addMatch(PatternMatch match) {
        if(getDistinctWordIndex() == -1) {
            setDistinctWordIndex(match.getDifferentWordIndex());
        }

        if(getChangingWords().isEmpty()) {
            getChangingWords().add(match.getPattern().getWords().get(getDistinctWordIndex()));
        }

        getChangingWords().add(match.getMatch().getWords().get(getDistinctWordIndex()));
        getLines().add(match.getMatch());
    }
}
