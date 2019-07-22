package com.topica.zyot.shyn;

import com.topica.zyot.shyn.mail.CheckingMailsWorker;
import com.topica.zyot.shyn.mail.SendingMailsWorker;
import com.topica.zyot.shyn.problem.CodeSubmitter;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    private static final Logger logger = Logger.getLogger(App.class);
    private static final String HOST = "imap.gmail.com";
    private static final String EMAIL_USERNAME = "dungnd240998@gmail.com";
    private static final String EMAIL_PASWORD = "conkuncon249";
    public static final String TITLE_HOMEWORK_RESULT = "ITLAB-HOMEWORK-RESULT";
    public static final String TITLE_HOMEWORK_WORNG_FORMAT = "ITLAB-HOMEWORK-WRONG-FORMAT";
    private static final Map<String, Integer> scores = new HashMap<>();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newScheduledThreadPool(5);
        CheckingMailsWorker checker = new CheckingMailsWorker(HOST, EMAIL_USERNAME, EMAIL_PASWORD);
        CodeSubmitter submitter = new CodeSubmitter(executorService, scores);
        SendingMailsWorker sender = new SendingMailsWorker(EMAIL_USERNAME, EMAIL_PASWORD, scores, executorService);
        checker.setPriority(Thread.MAX_PRIORITY);
        checker.start();
        submitter.start();
        sender.start();

        while (checker.isAlive() || submitter.isAlive() || sender.isAlive()) ;
        executorService.shutdown();
        logger.info("System shutdown");
    }


}
