package com.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class PatternProcessor {
    private boolean useSmartVersion;
    private List<PatternContainer> patternContainers = new LinkedList<>();

    public List<PatternContainer> getPatternContainers() {
        return patternContainers;
    }

    public PatternProcessor(boolean useSmartVersion) {
        this.useSmartVersion = useSmartVersion;
    }

    public boolean processFile(String filePath) throws IOException {
        Path path = new File(filePath).toPath();
        if (!Files.exists(path)) {
            // Or maybe throw an exception...
            return false;
        }

        try (Stream<String> lines = Files.lines(path)) {
            lines.map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .forEach(this::processLine);
        }

        return true;
    }

    public void processLine(String line) {
        int matchesCount = 0;
        TextLine textLine = TextLine.parse(line);
        int i = 0;
        List<PatternContainer> partialMatchContainers = new ArrayList<>();
        while(i < patternContainers.size()) {
            PatternContainer patternContainer = patternContainers.get(i);
            PatternMatch match = patternContainer.tryMatch(textLine);
            if(match.getKind().equals(MatchKind.FULL)) {
                patternContainer.addMatch(match);
                matchesCount++;
            }
            else if(match.getKind().equals(MatchKind.PARTIAL)) {
                // Suppose we have 3 lines:
                // 1. Elvis left the building
                // 2. King left the building
                // 3. King left the car
                // Second line matches first one, so that's a pattern
                // Third line doesn't match the existing pattens, but it matches the second line,
                // so that's a partial match, cause we need to create a new match from the second a third lines
                PatternContainer container = createContainer(match.getPattern());
                container.addMatch(match);
                partialMatchContainers.add(container);
                matchesCount++;
            }

            i++;
        }

        if(matchesCount == 0) {
            // Since this line didn't match to anything - consider it a new pattern
            PatternContainer container = createContainer(textLine);
            patternContainers.add(container);
        }
        else if (!partialMatchContainers.isEmpty()) {
            reducePartialMatchContainers(partialMatchContainers);
        }
    }

    private void reducePartialMatchContainers(List<PatternContainer> partialMatchContainers) {
        int i = 0;
        while(i < partialMatchContainers.size() - 1) {
            PatternMatch match = partialMatchContainers.get(i).tryMatch(partialMatchContainers.get(i + 1).getPattern());
            if(match.getKind().equals(MatchKind.FULL)) {
                partialMatchContainers.get(i).addMatch(PatternMatch.fullMatch(match.getPattern(), match.getPattern(), match.getDifferentWordIndex()));
                partialMatchContainers.remove(i+1);
            }
            else {
                i++;
            }
        }

        patternContainers.addAll(partialMatchContainers);
    }

    private PatternContainer createContainer(TextLine pattern) {
        if(useSmartVersion) {
            return new SmartPatternContainer(pattern);
        }

        return new SimplePatternContainer(pattern);
    }
}
