package com.topica.zyot.shyn.mail;

import com.topica.zyot.shyn.App;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class SendingMailsWorker extends Thread {
    private static final Logger logger = Logger.getLogger(SendingMailsWorker.class);
    private long timeStone;
    private String username;
    private String password;
    private final Map<String, Integer> scores;
    private ExecutorService executor;

    public SendingMailsWorker(String username, String password, Map<String, Integer> scores, ExecutorService executor) {
        this.username = username;
        this.password = password;
        this.scores = scores;
        this.executor = executor;
        this.timeStone = System.currentTimeMillis();
    }

    @Override
    public void run() {
        while (true) {
            boolean active = false;
            long currentTime = System.currentTimeMillis();
            long duration = currentTime - timeStone;
            logger.info("duration : " + duration);
            if (duration >= 40000)
                break;
            Set<String> wrongAddresses = CheckingMails.getAllWrongFormatAddresses();
            if (!wrongAddresses.isEmpty()) {
                wrongAddresses.forEach(wrongAddress -> {
                    String title = App.TITLE_HOMEWORK_WORNG_FORMAT;
                    String content = "Wrong Format";
                    ReplyTask replyTask = new ReplyTask(username, password, wrongAddress, title, content);
                    executor.submit(replyTask);
                });
                active = true;
            }
            Map<String, Integer> cloneOfScores;
            synchronized (scores) {
                if (!scores.isEmpty()) {
                    cloneOfScores = new HashMap<>(scores);
                    scores.clear();
                    active = true;
                } else
                    cloneOfScores = Collections.emptyMap();
            }
            if (!cloneOfScores.isEmpty()) {
                cloneOfScores.forEach((address, score) -> {
                    String title = App.TITLE_HOMEWORK_RESULT;
                    String content = "Your score is: " + score;
                    SendingMails.sendEmail(username, password, address, title, content);
                });
            }

            if (active)
                timeStone = System.currentTimeMillis();
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        logger.info("Sending Mail Worker shutdown");
    }
}
