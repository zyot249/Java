package com.topica.zyot.shyn.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int SERVER_PORT = 6969;
    public static final int SIZE_OF_BUFFER = 64 * 1024;
    private static final int FIRST_POSITION = 0;
    private static final String FILE_NAME = "input/4K Video (Ultra HD) - The Secret Beach - Wonders of Nature with Relaxing Music.mp4";

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        Socket socket = serverSocket.accept();
        OutputStream outputStream = socket.getOutputStream();
        FileInputStream fileInputStream = new FileInputStream(FILE_NAME);

        byte[] buffer = new byte[SIZE_OF_BUFFER];
        int bytesRead;
        long totalSent = 0;
        long time = System.currentTimeMillis();
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            if (bytesRead > 0) {
                outputStream.write(buffer, FIRST_POSITION, bytesRead);
                totalSent += bytesRead;
                System.out.println("sent " + totalSent);
            }
        }
        socket.close();

        System.out.println("Sent " + totalSent + " bytes in "
                + (System.currentTimeMillis() - time) + "ms.");

    }
}
