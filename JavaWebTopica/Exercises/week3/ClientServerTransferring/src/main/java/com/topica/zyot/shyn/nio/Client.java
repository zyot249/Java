package com.topica.zyot.shyn.nio;

import com.topica.zyot.shyn.MyConst;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class Client {
    public static void main(String[] args) throws IOException {
        final String FILE_NAME = "output/outputfile";
        SocketChannel socket = SocketChannel.open(new InetSocketAddress(MyConst.SERVER_IP, MyConst.SERVER_PORT));
        socket.configureBlocking(false);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(Server.SIZE_OF_BUFFER);
        FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
        FileChannel fileChannel = outputStream.getChannel();
        byteBuffer.clear();
        while (socket.read(byteBuffer) >= 0 || byteBuffer.position() != 0) {
            byteBuffer.flip();
            fileChannel.write(byteBuffer);
            byteBuffer.compact();
        }

        fileChannel.close();
        outputStream.close();
        socket.close();
    }
}
