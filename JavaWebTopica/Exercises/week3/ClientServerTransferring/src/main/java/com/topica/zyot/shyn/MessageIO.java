package com.topica.zyot.shyn;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MessageIO {
    public static void sendStringThroughSocket(String str, OutputStream outputStream, int sizeOfBuffer) throws IOException {
        if (str == null || str.length() == 0)
            return;
        byte[] buffer = new byte[sizeOfBuffer];
        buffer = str.getBytes();
        outputStream.write(buffer);
        outputStream.flush();
    }

    public static String readStringThroughSocket(InputStream inputStream, int sizeOfBuffer) throws IOException {
        byte[] buffer = new byte[sizeOfBuffer];
        int bytesRead = inputStream.read(buffer);
        StringBuilder message = new StringBuilder();
        if (bytesRead != MyConst.END_OF_STREAM) {
            for (int i = 0; i < bytesRead; i++) {
                message.append((char) buffer[i]);
            }
            return message.toString();
        } else return null;
    }
}
