package com.topica.zyot.shyn.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class CheckingMails {
    private static Logger logger;

    public static void check(String host, String storeType, String user, String password) {
        try {
            //create properties field
            Properties properties = new Properties();

            properties.put("mail.store.protocol", "imaps");
            properties.put("mail.imap.host", host);
            properties.put("mail.imap.port", "993");
            properties.put("mail.imap.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);

            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("imaps");

            store.connect(host, user, password);

            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            logger.log(Level.INFO, "messages.length : {0}", messages.length);

            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                String mailSubject = message.getSubject();
                // get mail whose subject starts with "ITLAB-HOMEWORK"
                if (Pattern.matches("ITLAB-HOMEWORK.*", mailSubject)) {
                    // get address
                    Address[] addresses = message.getFrom();
                    String address = addresses == null ? null : ((InternetAddress) addresses[0]).getAddress();
                    System.out.println("---------------------------------");
                    System.out.println("Email Number " + (i + 1));
                    System.out.println("Subject: " + message.getSubject());
                    System.out.println("From: " + address);
                    System.out.println("Text: " + message.getContentType());

                    // Iterate multiparts
                    // get attachments
                    Multipart multipart = (Multipart) message.getContent();
                    System.out.println("parts: " + multipart.getCount());
                    for (int k = 0; k < multipart.getCount(); k++) {
                        BodyPart bodyPart = multipart.getBodyPart(k);
                        if (!Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
                            System.out.println("part " + (k + 1) + " doesn't have attachment");
                        } else {
                            System.out.println("part " + (k + 1) + " has attachments");
                            getAttachment(address, bodyPart);
                        }
                    }
                }

            }

            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getAttachment(String address, BodyPart bodyPart) throws Exception {
        String fileName = bodyPart.getFileName();
        String fileExtension = fileName.substring(fileName.lastIndexOf('.'));
        if (fileExtension.equalsIgnoreCase(".zip")) {
            logger.log(Level.INFO, "Find a file: " + fileName);
            InputStream is = bodyPart.getInputStream();
            File f = new File("output/" + address + "-" + fileName);
            FileOutputStream fos = new FileOutputStream(f);
            byte[] buf = new byte[64 * 1024];
            int bytesRead;
            while ((bytesRead = is.read(buf)) != -1) {
                fos.write(buf, 0, bytesRead);
            }
            fos.close();
        } else {
            System.out.println("Attachment isn't a zip file");
        }
    }

    public static void main(String[] args) {
        logger = Logger.getLogger(CheckingMails.class.getSimpleName());
        String host = "imap.gmail.com";// change accordingly
        String mailStoreType = "imap";
        String username = "dungnd240998@gmail.com";// change accordingly
        String pasword = "conkuncon249";// change accordingly

        check(host, mailStoreType, username, pasword);

    }
}
