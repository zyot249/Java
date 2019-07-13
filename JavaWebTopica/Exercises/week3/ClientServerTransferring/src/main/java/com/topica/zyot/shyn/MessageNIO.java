package com.topica.zyot.shyn;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class MessageNIO {
    public static void sendStringThroughSocket(String str, SocketChannel socket, int sizeOfBuffer) throws IOException {
        if (str == null || str.length() == 0 || socket == null)
            return;
        ByteBuffer byteBuffer = ByteBuffer.allocate(sizeOfBuffer);
        byteBuffer = ByteBuffer.wrap(str.getBytes());
        socket.write(byteBuffer);
    }

    public static String readStringThroughSocket(SocketChannel socket, int sizeOfBuffer) throws IOException {
        if (socket == null)
            return null;
        ByteBuffer byteBuffer = ByteBuffer.allocate(sizeOfBuffer);
        int bytesRead = socket.read(byteBuffer);
        StringBuilder message = new StringBuilder();
        if (bytesRead > 0 || byteBuffer.position() != 0) {
            for (int i = 0; i < bytesRead; i++) {
                message.append((char) byteBuffer.get(i));
            }
            return message.toString();
        } else return null;
    }
}
