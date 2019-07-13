package com.topica.zyot.shyn.nio;

import com.topica.zyot.shyn.MessageNIO;
import com.topica.zyot.shyn.MyConst;
import com.topica.zyot.shyn.StringSplitter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class Client {
    public static void main(String[] args) throws IOException {
        final String FILE_NAME = "output/outputfile";
        String fullnameOfFile = "";
        SocketChannel socket = SocketChannel.open(new InetSocketAddress(MyConst.SERVER_IP, MyConst.SERVER_PORT));
        socket.configureBlocking(false);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(Server.SIZE_OF_BUFFER);

        while (true) {
            String message = MessageNIO.readStringThroughSocket(socket, Server.SIZE_OF_BUFFER);
            if (message != null) {
                StringSplitter splitter = new StringSplitter(message);
                String cmd = splitter.next();
                if (cmd.equalsIgnoreCase("INFO")) {
                    System.out.println("Get info of file");
                    String extension = splitter.next();
                    System.out.println(extension);
                    fullnameOfFile = new StringBuilder()
                            .append(FILE_NAME)
                            .append(".")
                            .append(extension)
                            .toString();

                    // reply to server
                    MessageNIO.sendStringThroughSocket("OK client", socket, com.topica.zyot.shyn.io.Server.SIZE_OF_BUFFER);
                } else if (cmd.equalsIgnoreCase("START")) {
                    System.out.println("Start to read file");

                    // reply to server
                    MessageNIO.sendStringThroughSocket("OK client", socket, com.topica.zyot.shyn.io.Server.SIZE_OF_BUFFER);

                    if (fullnameOfFile.length() != 0) {
                        FileOutputStream outputStream = new FileOutputStream(fullnameOfFile);
                        FileChannel fileChannel = outputStream.getChannel();
                        byteBuffer.clear();
                        while (socket.read(byteBuffer) >= 0 || byteBuffer.position() != 0) {
                            byteBuffer.flip();
                            fileChannel.write(byteBuffer);
                            byteBuffer.compact();
                        }
                        fileChannel.close();
                        outputStream.close();
                        System.out.println("Reading file finished!");
                        break;
                    }
                }
            }
        }

        socket.close();
    }
}
