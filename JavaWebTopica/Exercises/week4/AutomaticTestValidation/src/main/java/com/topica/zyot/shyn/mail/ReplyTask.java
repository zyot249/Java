package com.topica.zyot.shyn.mail;

public class ReplyTask implements Runnable {
    private String username;
    private String password;
    private String to;
    private String title;
    private String content;

    public ReplyTask(String username, String password, String to, String title, String content) {
        this.username = username;
        this.password = password;
        this.to = to;
        this.title = title;
        this.content = content;
    }

    @Override
    public void run() {
        SendingMails.sendEmail(username, password, to, title, content);
    }
}
