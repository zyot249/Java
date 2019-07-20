package com.topica.zyot.shyn.problem;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CodeExecutionWorker extends Thread {
    private String filePath; // path of java file
    private String classPath;

    public CodeExecutionWorker(String filePath) {
        this.filePath = filePath;
        classPath = filePath.substring(0, filePath.lastIndexOf(File.separator));
    }

    @Override
    public void run() {
        // init test cases
        int[] inputs = new int[]{0, 1, 2, 3, 5, 6, 7, 8, 9, 13};
        int[] result = new int[]{-1, 31, 28, 31, 31, 30, 31, 31, 30, -1};
        int score = 0;
        // compile code
        try {
            Process codeCompiling = Runtime.getRuntime().exec("javac -cp " + classPath + " " + filePath);
            codeCompiling.waitFor();
            for (int i = 0; i < inputs.length; i++) {
                if (testCode(inputs[i], result[i]))
                    score++;
            }
            System.out.println("Score: " + score);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            Thread.currentThread().interrupt();
        }
    }

    private boolean testCode(int input, int result) throws IOException, InterruptedException {
        String runFileName = filePath.substring(filePath.lastIndexOf('/') + 1, filePath.lastIndexOf('.'));
        Process codeRunner = Runtime.getRuntime().exec("java -cp " + classPath + " " + runFileName + " " + input);
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
