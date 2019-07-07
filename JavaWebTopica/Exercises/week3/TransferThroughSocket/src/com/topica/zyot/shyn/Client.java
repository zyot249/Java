package com.topica.zyot.shyn;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 6969;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);
        InputStream in = socket.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream("output/4K Video (Ultra HD) - The Secret Beach - Wonders of Nature with Relaxing Music.mp4");

        byte[] buffer = new byte[64 * 1024];
        int bytesRead;

        while ( (bytesRead = in.read(buffer)) != -1)
            fileOutputStream.write(buffer, 0, bytesRead);
        socket.close();
        fileOutputStream.close();
    }
}
