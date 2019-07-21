package com.topica.zyot.shyn.mail;

import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;

public class CheckingMails {
    private static final Logger logger = Logger.getLogger(CheckingMails.class);
    private static final Set<String> wrongFormatAddresses = new HashSet<>();
    private static final Map<String, Set<String>> zipFilePathsOfStudents = new HashMap<>();

    private CheckingMails() {

    }

    public static void checkMail(String host, String user, String password) {
        try {
            Store store = connect(host, user, password);
            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            logger.info("The number of messages : " + messages.length);
            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                String mailSubject = message.getSubject();
                // get mail whose subject starts with "ITLAB-HOMEWORK"
                if (Pattern.matches("ITLAB-HOMEWORK.*", mailSubject)) {
                    checkContentOfMessages(message, i);
                }
            }
            //close the store and folder objects
            emailFolder.close(false);
            store.close();
        } catch (MessagingException | IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void checkContentOfMessages(Message message, int i) throws IOException, MessagingException {
        boolean isWrongMail = true;
        // get address
        Address[] addresses = message.getFrom();
        String address = addresses == null ? null : ((InternetAddress) addresses[0]).getAddress();
        logInfoOfMessage(message, address, i);

        // Iterate multiparts
        // get attachments
        Multipart multipart = (Multipart) message.getContent();
        logger.info("parts: " + multipart.getCount());
        for (int k = 0; k < multipart.getCount(); k++) {
            BodyPart bodyPart = multipart.getBodyPart(k);
            if (!Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
                logger.info("part " + (k + 1) + " doesn't have attachment");
            } else {
                logger.info("part " + (k + 1) + " has attachments");
                String fileName = bodyPart.getFileName();
                String fileExtension = fileName.substring(fileName.lastIndexOf('.'));
                if (fileExtension.equalsIgnoreCase(".zip")) {
                    isWrongMail = false;
                    boolean success = getAttachment(address, bodyPart);
                    if (success) {
                        // TODO: set read flag
                    } else {
                        // TODO: set unread flag
                    }
                } else {
                    addWrongFormatAddress(address);
                }
            }
        }
        if (isWrongMail) {
            addWrongFormatAddress(address);
        }
    }

    private static void logInfoOfMessage(Message message, String address, int i) throws MessagingException {
        logger.info("---------------------------------");
        logger.info("Email Number " + (i + 1));
        logger.info("Subject: " + message.getSubject());
        logger.info("From: " + address);
        logger.info("Text: " + message.getContentType());
    }

    private static Store connect(String host, String user, String password)
            throws MessagingException {
        final String PROTOCOL = "imaps";
        // config properties
        Properties properties = new Properties();

        properties.put("mail.store.protocol", PROTOCOL);
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", "993");
        properties.put("mail.imap.starttls.enable", "true");
        Session emailSession = Session.getDefaultInstance(properties);

        //create the POP3 store object and connect with the pop server
        Store store = emailSession.getStore(PROTOCOL);
        // connect
        store.connect(host, user, password);
        return store;
    }

    private static boolean getAttachment(String address, BodyPart bodyPart) {
        boolean success = true;
        try {
            String fileName = bodyPart.getFileName();
            logger.info("Find a file: " + fileName);
            InputStream is = bodyPart.getInputStream();
            String outputFilePath = "output/" + address + "-" + fileName;
            File f = new File(outputFilePath);
            FileOutputStream fos = new FileOutputStream(f);
            readFile(is, fos);
            fos.close();
            addZipFilePath(address, outputFilePath);
        } catch (IOException | MessagingException e) {
            logger.error(e.getMessage());
            success = false;
        }
        return success;
    }

    private static void readFile(InputStream is, FileOutputStream fos) throws IOException {
        byte[] buf = new byte[64 * 1024];
        int bytesRead;
        while ((bytesRead = is.read(buf)) != -1) {
            fos.write(buf, 0, bytesRead);
        }
    }

    private static void addWrongFormatAddress(String address) {
        synchronized (wrongFormatAddresses) {
            wrongFormatAddresses.add(address);
        }
    }

    public static Set<String> getAllWrongFormatAddresses() {
        synchronized (wrongFormatAddresses) {
            if (wrongFormatAddresses.isEmpty())
                return Collections.emptySet();
            else {
                HashSet<String> wrongAddresses = new HashSet<>(wrongFormatAddresses);
                wrongFormatAddresses.clear();
                return wrongAddresses;
            }
        }
    }

    private static void addZipFilePath(String address, String filePath) {
        synchronized (zipFilePathsOfStudents) {
            Set<String> paths = zipFilePathsOfStudents.get(address);
            if (paths == null) {
                paths = new HashSet<>();
                paths.add(filePath);
                zipFilePathsOfStudents.put(address, paths);
            } else {
                paths.add(filePath);
                zipFilePathsOfStudents.replace(address, paths);
            }
        }
    }

    public static Map<String, Set<String>> getAllZipFilePaths() {
        synchronized (zipFilePathsOfStudents) {
            if (zipFilePathsOfStudents.isEmpty())
                return Collections.emptyMap();
            else {
                HashMap<String, Set<String>> paths = new HashMap<>(zipFilePathsOfStudents);
                zipFilePathsOfStudents.clear();
                return paths;
            }
        }
    }
}
