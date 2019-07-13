package com.topica.zyot.shyn.io;

import com.topica.zyot.shyn.MessageIO;
import com.topica.zyot.shyn.MyConst;
import com.topica.zyot.shyn.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int SIZE_OF_BUFFER = 64 * 1024;
    private static final int FIRST_POSITION = 0;

    public static void main(String[] args) throws IOException {
        final String FILE_NAME = "input/4K Video (Ultra HD) - The Secret Beach.mp4";
        ServerSocket serverSocket = new ServerSocket(MyConst.SERVER_PORT);

        // wait to connect
        System.out.println("Waiting to connect...............");
        Socket socket = serverSocket.accept();
        System.out.println("Successfully connected!");
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();

        // send extension
        String extension = Utils.getExtensionOfFile(FILE_NAME);
        StringBuilder infoMsg = new StringBuilder()
                .append("INFO ")
                .append(extension);
        MessageIO.sendStringThroughSocket(infoMsg.toString(), outputStream, SIZE_OF_BUFFER);

        // wait response from client
        MessageIO.readStringThroughSocket(inputStream, SIZE_OF_BUFFER);

        // send signal to start transfer file content
        MessageIO.sendStringThroughSocket("START transfer", outputStream, SIZE_OF_BUFFER);

        // wait response from client
        MessageIO.readStringThroughSocket(inputStream, SIZE_OF_BUFFER);

        // send file to client
        long time = System.currentTimeMillis();
        long totalSent = sendFileThroughSocket(FILE_NAME, outputStream);
        System.out.println(new StringBuilder()
                .append("Server sent ")
                .append(totalSent)
                .append(" bytes in ")
                .append(System.currentTimeMillis() - time)
                .append("ms.")
                .toString());

        outputStream.close();
        inputStream.close();
        socket.close();
        serverSocket.close();
    }

    private static long sendFileThroughSocket(String fileName, OutputStream outputStream) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        byte[] buffer = new byte[SIZE_OF_BUFFER];
        int bytesRead;
        long totalSent = 0;
        while ((bytesRead = fileInputStream.read(buffer)) != MyConst.END_OF_STREAM) {
            if (bytesRead > 0) {
                outputStream.write(buffer, FIRST_POSITION, bytesRead);
                totalSent += bytesRead;
                System.out.println(new StringBuilder()
                        .append("Server sent: ")
                        .append(totalSent)
                        .append(" bytes.")
                        .toString());
            }
        }
        outputStream.flush();
        return totalSent;
    }
}
