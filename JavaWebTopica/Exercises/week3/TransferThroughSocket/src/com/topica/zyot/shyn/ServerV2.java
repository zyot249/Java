package com.topica.zyot.shyn;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;

public class ServerV2 {
    public static void main(String... args) throws IOException {
        int port = args.length < 1 ? 55555 : Integer.parseInt(args[0]);
        ServerSocketChannel ss = ServerSocketChannel.open();
        ss.socket().bind(new InetSocketAddress(port));
        while (!ss.socket().isClosed()) {
            SocketChannel s = ss.accept();
            System.out.println(new Date() + ": Accepted a connection");
            long start = System.nanoTime();
            ByteBuffer bytes = ByteBuffer.allocateDirect(32*1024);
            int len;
            long total = 0;
            // Thank you @EJP, for a more elegant single loop.
            while ((len = s.read(bytes)) >= 0 || bytes.position() > 0) {
                bytes.flip();
                s.write(bytes);
                bytes.compact();
                total += len;
            }
            long time = System.nanoTime() - start;
            System.out.printf(new Date() + ": Transfer rate was %.1f MB/s%n", total * 1e9 / 1024 / 1024 / time);
        }
        ss.close();
    }
}
