package com.topica.zyot.shyn.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class ClientV2 {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 6969;
    private static final String FILE_NAME = "output/outputfile";

    public static void main(String... args) throws IOException {
        SocketChannel socket = SocketChannel.open(new InetSocketAddress(SERVER_IP, SERVER_PORT));
        socket.configureBlocking(false);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(ServerV2.SIZE_OF_BUFFER);
        FileOutputStream fos = new FileOutputStream(FILE_NAME);
        FileChannel fileChannel = fos.getChannel();
        while (socket.read(byteBuffer) >= 0 || byteBuffer.position() > 0) {
            byteBuffer.flip();
            fileChannel.write(byteBuffer);
            byteBuffer.compact();
            byteBuffer.clear();
        }

        fileChannel.close();
        fos.close();
        socket.close();
    }
}
