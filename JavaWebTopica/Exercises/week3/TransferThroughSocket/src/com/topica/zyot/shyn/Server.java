package com.topica.zyot.shyn;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int SERVER_PORT = 6969;

    public static void main(String[] args) throws IOException {
        ServerSocket servsock = new ServerSocket(SERVER_PORT);
        Socket socket = servsock.accept();


        OutputStream out = socket.getOutputStream();
        FileInputStream fileInputStream = new FileInputStream("input/4K Video (Ultra HD) - The Secret Beach - Wonders of Nature with Relaxing Music.mp4");

        byte[] buffer = new byte[64 * 1024];
        int bytesRead;
        long totalSent = 0;
        long time = System.currentTimeMillis();
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            if (bytesRead > 0) {
                out.write(buffer, 0, bytesRead);
                totalSent += bytesRead;
                System.out.println("sent " + totalSent);
            }
        }
        socket.close();

        System.out.println("Sent " + totalSent + " bytes in "
                + (System.currentTimeMillis() - time) + "ms.");

    }
}
