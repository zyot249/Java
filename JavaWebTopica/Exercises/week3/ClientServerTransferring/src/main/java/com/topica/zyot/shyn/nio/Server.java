package com.topica.zyot.shyn.nio;

import com.topica.zyot.shyn.MyConst;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {
    public static final int SIZE_OF_BUFFER = 64 * 1024;

    public static void main(String[] args) throws IOException {
        final String FILE_NAME = "input/4K Video (Ultra HD) - The Secret Beach - Wonders of Nature with Relaxing Music.mp4";
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(MyConst.SERVER_PORT));
        SocketChannel socket = serverSocket.accept();
        FileInputStream inputStream = new FileInputStream(FILE_NAME);
        FileChannel fileChannel = inputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(SIZE_OF_BUFFER);
        long totalSent = 0;
        int len;
        long time = System.currentTimeMillis();
        while (fileChannel.read(byteBuffer) != MyConst.END_OF_STREAM) {
            byteBuffer.flip();
            len = socket.write(byteBuffer);
            byteBuffer.compact();
            totalSent += len;
            System.out.println("sent " + totalSent);
            byteBuffer.clear();
        }
        System.out.println(new StringBuilder()
                .append("Sent ")
                .append(totalSent)
                .append(" bytes in ")
                .append(System.currentTimeMillis() - time)
                .append("ms.")
                .toString());

        fileChannel.close();
        inputStream.close();
        serverSocket.close();
    }
}
