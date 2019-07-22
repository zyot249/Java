package com.topica.zyot.shyn.problem;

import com.topica.zyot.shyn.mail.CheckingMails;
import com.topica.zyot.shyn.utils.UnzipHandler;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class CodeSubmitter extends Thread {
    private static final Logger logger = Logger.getLogger(CodeSubmitter.class);
    private ExecutorService executorService;
    private final Map<String, Integer> scores;
    private long timeStone;

    public CodeSubmitter(ExecutorService executorService, Map<String, Integer> scores) {
        this.executorService = executorService;
        this.scores = scores;
        this.timeStone = System.currentTimeMillis();
    }

    @Override
    public void run() {
        while (true) {
            long currentTime = System.currentTimeMillis();
            long duration = currentTime - timeStone;
            logger.info("duration : " + duration);
            if (duration >= 40000)
                break;
            boolean active = unzipAndValidateHomework(executorService);
            if (active)
                timeStone = System.currentTimeMillis();
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        logger.info("Code Submitter shutdown");
    }

    private boolean unzipAndValidateHomework(ExecutorService executorService) {
        boolean active = false;
        Map<String, Set<String>> zipFilePaths = CheckingMails.getAllZipFilePaths();
        if (!zipFilePaths.isEmpty()) {
            zipFilePaths.forEach((address, paths) -> paths.forEach(path -> {
                String destinationDir = path.substring(0, path.lastIndexOf('.'));
                UnzipHandler.extract(address, path, destinationDir);
            }));
            validateHomework(executorService);
            active = true;
        }
        return active;
    }

    private void validateHomework(ExecutorService executorService) {
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
                updateScores(address, grade);
            }));
        }
    }

    private void updateScores(String address, int grade) {
        synchronized (scores) {
            Integer currentGrade = scores.get(address);
            if (currentGrade != null) {
                if (grade > currentGrade) {
                    scores.replace(address, grade);
                }
            } else
                scores.put(address, grade);
        }
    }
}
