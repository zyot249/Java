package com.topica.zyot.shyn.io;

import com.topica.zyot.shyn.MessageIO;
import com.topica.zyot.shyn.MyConst;
import com.topica.zyot.shyn.StringSplitter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    private static final int FIRST_POSITION = 0;

    public static void main(String[] args) throws IOException {
        final String FILE_NAME = "output/outputfile";
        String fullnameOfFile = "";
        Socket socket = new Socket(MyConst.SERVER_IP, MyConst.SERVER_PORT);
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        byte[] buffer = new byte[Server.SIZE_OF_BUFFER];
        int bytesRead;

        while (true) {
            String message = MessageIO.readStringThroughSocket(inputStream, Server.SIZE_OF_BUFFER);
            if (message != null) {
                StringSplitter splitter = new StringSplitter(message);
                String cmd = splitter.next();
                if (cmd.equalsIgnoreCase("INFO")) {
                    System.out.println("Get info of file");
                    String extension = splitter.next();
                    System.out.println(extension);
                    fullnameOfFile = new StringBuilder()
                            .append(FILE_NAME)
                            .append(".")
                            .append(extension)
                            .toString();

                    // reply to server
                    MessageIO.sendStringThroughSocket("OK client", outputStream, Server.SIZE_OF_BUFFER);
                } else if (cmd.equalsIgnoreCase("START")) {
                    System.out.println("Start to read file");

                    // reply to server
                    MessageIO.sendStringThroughSocket("OK client", outputStream, Server.SIZE_OF_BUFFER);

                    if (fullnameOfFile.length() != 0) {
                        FileOutputStream fileOutputStream = new FileOutputStream(fullnameOfFile);
                        while ((bytesRead = inputStream.read(buffer)) != MyConst.END_OF_STREAM) {
                            fileOutputStream.write(buffer, FIRST_POSITION, bytesRead);
                        }
                        fileOutputStream.close();
                        System.out.println("Reading file finished!");
                        break;
                    }
                }
            }
        }

        inputStream.close();
        outputStream.close();
        socket.close();
    }
}
