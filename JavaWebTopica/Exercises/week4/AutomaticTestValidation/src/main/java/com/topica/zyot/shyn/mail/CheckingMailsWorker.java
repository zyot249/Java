package com.topica.zyot.shyn.mail;

import org.apache.log4j.Logger;

public class CheckingMailsWorker extends Thread {
    private static final Logger logger = Logger.getLogger(CheckingMailsWorker.class);
    private String host;
    private String user;
    private String password;
    private long timeStone;

    public CheckingMailsWorker(String host, String user, String password) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.timeStone = System.currentTimeMillis();
    }

    @Override
    public void run() {
        while (true) {
            long currentTime = System.currentTimeMillis();
            long duration = currentTime - timeStone;
            logger.info("duration : " + duration);
            if (duration >= 20000)
                break;
            int numOfMsg = CheckingMails.checkMail(host, user, password);
            logger.info("num of messages : " + numOfMsg);
            if (numOfMsg != 0)
                timeStone = System.currentTimeMillis();
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        logger.info("Checking Mail Worker shutdown");
    }
}
