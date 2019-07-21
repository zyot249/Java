package com.topica.zyot.shyn.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class CheckingMails {
    private static Logger logger = Logger.getLogger(CheckingMails.class.getSimpleName());
    private static final Set<String> wrongFormatAddresses = new HashSet<>();
    private static final Map<String, Set<String>> zipFilePathsOfStudents = new HashMap<>();

    public static void checkMail(String host, String user, String password) {
        try {
            Store store = connect(host, user, password);
            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            logger.log(Level.INFO, "messages.length : {0}", messages.length);
            boolean isWrongMail;
            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                String mailSubject = message.getSubject();
                // get mail whose subject starts with "ITLAB-HOMEWORK"
                if (Pattern.matches("ITLAB-HOMEWORK.*", mailSubject)) {
                    isWrongMail = true;
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
            }
            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
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
            logger.log(Level.INFO, "Find a file: " + fileName);
            InputStream is = bodyPart.getInputStream();
            String outputFilePath = "output/" + address + "-" + fileName;
            File f = new File(outputFilePath);
            FileOutputStream fos = new FileOutputStream(f);
            readFile(is, fos);
            fos.close();
            addZipFilePath(address, outputFilePath);
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
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


    public static void main(String[] args) {
        logger = Logger.getLogger(CheckingMails.class.getSimpleName());
        String host = "imap.gmail.com";// change accordingly
        String username = "dungnd240998@gmail.com";// change accordingly
        String pasword = "conkuncon249";// change accordingly

        checkMail(host, username, pasword);

    }
}
