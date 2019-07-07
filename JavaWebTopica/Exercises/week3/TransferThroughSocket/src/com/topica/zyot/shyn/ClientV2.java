package com.topica.zyot.shyn;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;

public class ClientV2 {
    public static void main(String ... args) throws IOException {
        String hostname = args.length < 1 ? "localhost" : args[0];
        int port = args.length < 2 ? 55555 : Integer.parseInt(args[1]);
        double seconds = args.length < 3 ? 10 : Double.parseDouble(args[2]);

        SocketChannel s = SocketChannel.open(new InetSocketAddress(hostname, port));
        s.configureBlocking(false);
        ByteBuffer bytes = ByteBuffer.allocateDirect(32*1024);

        System.out.printf(new Date()+ " Sending for %.1f seconds.%n", seconds);
        long start = System.nanoTime();
        long dataSent = 0, dataReceived = 0;
        // run for 10 seconds.
        while(start + seconds*1e9 > System.nanoTime()) {
            bytes.clear();
            int wlen = s.write(bytes);
            if (wlen < 0) throw new IOException();
            dataSent += wlen;

            bytes.clear();
            int rlen = s.read(bytes);
            if (rlen < 0) throw new EOFException();
            dataReceived += rlen;
        }
        System.out.println(new Date()+ " ... sent.");

        while(dataReceived < dataSent) {
            bytes.clear();
            int rlen = s.read(bytes);
            if (rlen < 0) throw new EOFException();
            dataReceived += rlen;
        }
        s.close();
        long time = System.nanoTime() - start;
        System.out.println(new Date()+ " ... received "+dataReceived);
        System.out.printf("Send and received %.1f MB/s%n", dataReceived * 1e9/1024/1024/time);
    }
}
