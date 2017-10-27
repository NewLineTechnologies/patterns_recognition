package com.test;

import java.util.Arrays;
import java.util.List;

public class TextLine {
    private static int DatePartLength = 20;
    private String fullLine;
    private String meaningfulPart;
    private String datePart;
    private List<String> words;

    private TextLine(String fullLine, String meaningfulPart, String datePart) {
        this.fullLine = fullLine;
        this.meaningfulPart = meaningfulPart;
        this.datePart = datePart;
        words = Arrays.asList( this.meaningfulPart.split("\\s"));
    }

    public String getFullLine() {
        return fullLine;
    }

    public String getMeaningfulPart() {
        return meaningfulPart;
    }
    public String getDatePart() {
        return datePart;
    }

    public static TextLine parse(String line)
    {
        String datePart = line.substring(0, DatePartLength);
        String meaningfulPart = line.substring(DatePartLength);
        return new TextLine(line, meaningfulPart, datePart);
    }

    public List<String> getWords() {
        return words;
    }
}
