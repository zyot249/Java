package com.topica.zyot.shyn.problem;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class CodeExecutionWorker implements Callable<Integer> {
    private static final Logger logger = Logger.getLogger(CodeExecutionWorker.class);
    private String filePath; // path of java file
    private String classPath;

    public CodeExecutionWorker(String filePath) {
        this.filePath = filePath;
        classPath = filePath.substring(0, filePath.lastIndexOf(File.separator));
    }

    @Override
    public Integer call() {
        // init test cases
        int[] inputs = new int[]{0, 1, 2, 3, 5, 6, 7, 8, 9, 13};
        int[] result = new int[]{-1, 31, 28, 31, 31, 30, 31, 31, 30, -1};
        int score = 0;
        // compile code
        try {
            Process codeCompiling = Runtime.getRuntime().exec(new StringBuilder()
                    .append("javac -cp ")
                    .append(classPath).append(" ")
                    .append(filePath).toString());
            codeCompiling.waitFor();
            for (int i = 0; i < inputs.length; i++) {
                if (testCode(inputs[i], result[i]))
                    score++;
            }
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
            return -1;
        } finally {
            Thread.currentThread().interrupt();
        }
        return score;
    }

    private boolean testCode(int input, int result) throws IOException, InterruptedException {
        String runFileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1, filePath.lastIndexOf('.'));
        Process codeRunner = Runtime.getRuntime().exec(new StringBuilder()
                .append("java -cp ")
                .append(classPath).append(" ")
                .append(runFileName).append(" ")
                .append(input).toString());
        BufferedReader reader = new BufferedReader(new InputStreamReader(codeRunner.getInputStream()));
        String line = reader.readLine();
        if (line != null) {
            try {
                int output = Integer.parseInt(line);
                return output == result;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        codeRunner.waitFor();
        return false;
    }
}
