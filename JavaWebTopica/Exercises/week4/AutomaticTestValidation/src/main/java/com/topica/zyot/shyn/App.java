package com.topica.zyot.shyn;

import com.topica.zyot.shyn.mail.CheckingMails;
import com.topica.zyot.shyn.mail.SendingMails;
import com.topica.zyot.shyn.problem.CodeExecutionWorker;
import com.topica.zyot.shyn.utils.UnzipHandler;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    private static final Logger logger = Logger.getLogger(App.class);
    private static final String HOST = "imap.gmail.com";// change accordingly
    private static final String EMAIL_USERNAME = "dungnd240998@gmail.com";// change accordingly
    private static final String EMAIL_PASWORD = "conkuncon249";// change accordingly

    public static void main(String[] args) {
        Map<String, Integer> scores = new HashMap<>();
        ExecutorService executorService = Executors.newScheduledThreadPool(5);
        CheckingMails.checkMail(HOST, EMAIL_USERNAME, EMAIL_PASWORD);
        unzipAndValidateHomework(scores, executorService);
        if (!scores.isEmpty()) {
            scores.forEach((address, score) -> {
                String content = "Your score is: " + score;
                SendingMails.sendEmail(EMAIL_USERNAME, EMAIL_PASWORD, address, "ITLAB-HOMEWORK-RESULT", content);
            });
        }
        // send fail
        Set<String> wrongAddresses = CheckingMails.getAllWrongFormatAddresses();
        wrongAddresses.forEach(wrongAddress -> {
            String content = "Wrong Format";
            SendingMails.sendEmail(EMAIL_USERNAME, EMAIL_PASWORD, wrongAddress, "ITLAB-HOMEWORK-WRONG-FORMAT", content);
        });
        executorService.shutdown();
    }

    private static void unzipAndValidateHomework(Map<String, Integer> scores, ExecutorService executorService) {
        Map<String, Set<String>> zipFilePaths = CheckingMails.getAllZipFilePaths();
        if (!zipFilePaths.isEmpty()) {
            zipFilePaths.forEach((address, paths) -> paths.forEach(path -> {
                String destinationDir = path.substring(0, path.lastIndexOf('.'));
                UnzipHandler.extract(address, path, destinationDir);
            }));
            validateHomework(scores, executorService);
        }
    }

    private static void validateHomework(Map<String, Integer> scores, ExecutorService executorService) {
        Map<String, Set<String>> fileJavaPaths = UnzipHandler.getAllFileJavaPaths();
        if (!fileJavaPaths.isEmpty()) {
            fileJavaPaths.forEach((address, paths) -> paths.forEach(path -> {
                CodeExecutionWorker worker = new CodeExecutionWorker(path);
                int grade = 0;
                try {
                    grade = executorService.submit(worker).get();
                } catch (InterruptedException | ExecutionException e) {
                    logger.error(e.getMessage());
                    Thread.currentThread().interrupt();
                }
                Integer currentGrade = scores.get(address);
                if (currentGrade != null) {
                    if (grade > currentGrade) {
                        scores.replace(address, grade);
                    }
                } else
                    scores.put(address, grade);
            }));
        }
    }
}
