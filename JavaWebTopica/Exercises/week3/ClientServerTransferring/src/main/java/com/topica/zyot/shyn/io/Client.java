package com.topica.zyot.shyn.io;

import com.topica.zyot.shyn.MyConst;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Client {
    private static final int FIRST_POSITION = 0;

    public static void main(String[] args) throws IOException {
        final String FILE_NAME = "output/outputfile";
        Socket socket = new Socket(MyConst.SERVER_IP, MyConst.SERVER_PORT);
        InputStream in = socket.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);

        byte[] buffer = new byte[Server.SIZE_OF_BUFFER];
        int bytesRead;

        while ((bytesRead = in.read(buffer)) != MyConst.END_OF_STREAM) {
            fileOutputStream.write(buffer, FIRST_POSITION, bytesRead);
        }
        socket.close();
        fileOutputStream.close();
    }
}
