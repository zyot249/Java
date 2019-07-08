package com.topica.zyot.shyn.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerV2 {
    private static final int SERVER_PORT = 6969;
    public static final int SIZE_OF_BUFFER = 32 * 1024;
    private static final String FILE_NAME = "input/4K Video (Ultra HD) - The Secret Beach - Wonders of Nature with Relaxing Music.mp4";

    public static void main(String... args) throws IOException {
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(SERVER_PORT));
        SocketChannel socket = serverSocket.accept();
        FileInputStream fis = new FileInputStream(FILE_NAME);
        FileChannel fileChannel = fis.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(SIZE_OF_BUFFER);
        long totalSent = 0;
        int len;
        long time = System.currentTimeMillis();
        while (fileChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            len = socket.write(byteBuffer);
            byteBuffer.compact();
            totalSent += len;
            System.out.println("sent " + totalSent);
            byteBuffer.clear();
        }
        fileChannel.close();
        fis.close();
        serverSocket.close();
        System.out.println("Sent " + totalSent + " bytes in "
                + (System.currentTimeMillis() - time) + "ms.");
    }
}
