package com.topica.zyot.shyn.io;

import com.topica.zyot.shyn.MyConst;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int SIZE_OF_BUFFER = 64 * 1024;
    private static final int FIRST_POSITION = 0;

    public static void main(String[] args) throws IOException {
        final String FILE_NAME = "input/4K Video (Ultra HD) - The Secret Beach - Wonders of Nature with Relaxing Music.mp4";
        ServerSocket serverSocket = new ServerSocket(MyConst.SERVER_PORT);
        Socket socket = serverSocket.accept();
        OutputStream outputStream = socket.getOutputStream();
        FileInputStream fileInputStream = new FileInputStream(FILE_NAME);

        byte[] buffer = new byte[SIZE_OF_BUFFER];
        int bytesRead;
        long totalSent = 0;
        long time = System.currentTimeMillis();
        while ((bytesRead = fileInputStream.read(buffer)) != MyConst.END_OF_STREAM) {
            if (bytesRead > 0) {
                outputStream.write(buffer, FIRST_POSITION, bytesRead);
                totalSent += bytesRead;
                System.out.println("sent " + totalSent);
            }
        }
        System.out.println(new StringBuilder()
                .append("Sent ")
                .append(totalSent)
                .append(" bytes in ")
                .append(System.currentTimeMillis() - time)
                .append("ms.")
                .toString());

        socket.close();
    }
}
