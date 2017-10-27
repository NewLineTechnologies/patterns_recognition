package com.test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        PatternExecutor executor = new PatternExecutor();
//        executor.execute("D:\\patterns\\test_file.txt", false);
        Path currentRelativePath = Paths.get("").toAbsolutePath().resolve("data").resolve("test_file_v3.txt");
        executor.execute(currentRelativePath.toString(), true);
    }
}
