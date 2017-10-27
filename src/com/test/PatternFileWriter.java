package com.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

public class PatternFileWriter {

    public static void write(File filePath, PatternContainer pattern) throws IOException {
        try(FileWriter fw = new FileWriter(filePath, false);
                BufferedWriter bufferedWriter = new BufferedWriter (fw)) {
            for(String line : pattern.getLines().stream().map(TextLine::getFullLine).collect(Collectors.toList())) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }

            String changingWords = "Changing words are: ".concat(String.join(", ", pattern.getChangingWords()));
            bufferedWriter.write(changingWords);
        }
    }
}
