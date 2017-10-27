package com.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PatternExecutor {

    public void execute(String filePath, boolean useSmartVersion) throws IOException {
        File file = new File(filePath);
        File parentDirectory = file.getParentFile();
        File resultDirectory = getResultsDirectory(parentDirectory);

        PatternProcessor processor = new PatternProcessor(useSmartVersion);
        boolean result = processor.processFile(filePath);
        if(!result) {
            System.out.println("Can't process file");
        }
        else {
            int counter = 1;
            for (PatternContainer container : processor.getPatternContainers()) {
                File patternFile = new File(resultDirectory, "pattern_" + counter + ".txt");
                PatternFileWriter.write(patternFile, container);
                counter++;
            }
        }
    }

    private File getResultsDirectory(File parentDirectory) throws IOException {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss.SSS");
        String text = date.format(formatter);
        Path resultDirectory = Paths.get(parentDirectory.getAbsolutePath(), "results_" + text);
        if(!Files.exists(resultDirectory)) {
            Files.createDirectory(resultDirectory);
        }

        return resultDirectory.toFile();
    }

}
