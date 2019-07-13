package com.topica.zyot.shyn.nio;

import com.topica.zyot.shyn.MessageNIO;
import com.topica.zyot.shyn.MyConst;
import com.topica.zyot.shyn.Utils;

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
        final String FILE_NAME = "input/4K Video (Ultra HD) - The Secret Beach.mp4";
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(MyConst.SERVER_PORT));

        // wait to connect
        System.out.println("Waiting to connect...............");
        SocketChannel socket = serverSocket.accept();
        System.out.println("Successfully connected!");

        // send extension
        String extension = Utils.getExtensionOfFile(FILE_NAME);
        StringBuilder infoMsg = new StringBuilder()
                .append("INFO ")
                .append(extension);
        MessageNIO.sendStringThroughSocket(infoMsg.toString(), socket, SIZE_OF_BUFFER);

        // wait response from client
        MessageNIO.readStringThroughSocket(socket, SIZE_OF_BUFFER);

        // send signal to start transfer file content
        MessageNIO.sendStringThroughSocket("START transfer", socket, SIZE_OF_BUFFER);

        // wait response from client
        MessageNIO.readStringThroughSocket(socket, SIZE_OF_BUFFER);

        // send file to client
        long time = System.currentTimeMillis();
        long totalSent = sendFileThroughSocket(FILE_NAME, socket);
        System.out.println(new StringBuilder()
                .append("Sent ")
                .append(totalSent)
                .append(" bytes in ")
                .append(System.currentTimeMillis() - time)
                .append("ms.")
                .toString());

        serverSocket.close();
    }

    private static long sendFileThroughSocket(String fileName, SocketChannel socket) throws IOException {
        FileInputStream inputStream = new FileInputStream(fileName);
        FileChannel fileChannel = inputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(SIZE_OF_BUFFER);
        long totalSent = 0;
        int len;
        while (fileChannel.read(byteBuffer) != MyConst.END_OF_STREAM) {
            byteBuffer.flip();
            len = socket.write(byteBuffer);
            byteBuffer.compact();
            totalSent += len;
            System.out.println("sent " + totalSent);
            byteBuffer.clear();
        }
        fileChannel.close();
        inputStream.close();
        return totalSent;
    }
}
