package com.topica.zyot.shyn.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 6969;
    private static final int FIRST_POSITION = 0;
    private static final String FILE_NAME = "output/outputfile";


    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);
        InputStream in = socket.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);

        byte[] buffer = new byte[Server.SIZE_OF_BUFFER];
        int bytesRead;

        while ( (bytesRead = in.read(buffer)) != -1)
            fileOutputStream.write(buffer, FIRST_POSITION, bytesRead);
        socket.close();
        fileOutputStream.close();
    }
}
