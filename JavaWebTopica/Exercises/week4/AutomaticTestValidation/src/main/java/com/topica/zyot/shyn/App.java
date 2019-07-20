package com.topica.zyot.shyn;

import com.topica.zyot.shyn.mail.CheckingMails;
import com.topica.zyot.shyn.utils.UnzipHandler;

import java.util.Set;

public class App {
    private static final String HOST = "imap.gmail.com";// change accordingly
    private static final String EMAIL_USERNAME = "dungnd240998@gmail.com";// change accordingly
    private static final String EMAIL_PASWORD = "conkuncon249";// change accordingly

    public static void main(String[] args) {
        CheckingMails.checkMail(HOST, EMAIL_USERNAME, EMAIL_PASWORD);
        Set<String> zipFilePaths = CheckingMails.getAllZipFilePaths();
        if (!zipFilePaths.isEmpty()) {
            for (String path : zipFilePaths) {
                String dest = path.substring(0, path.lastIndexOf('.'));
                UnzipHandler.extract(path, dest);
            }
        }
    }
}
