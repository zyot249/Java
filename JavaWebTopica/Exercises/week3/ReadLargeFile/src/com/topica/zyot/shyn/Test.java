package com.topica.zyot.shyn;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Test {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("100MB.txt");
        FileChannel fileChannel = fis.getChannel();
        ByteBuffer bb = ByteBuffer.allocate(102400);
        while (fileChannel.read(bb) != -1){
            System.out.print(new String(bb.array()));
            bb.clear();
        }
        fileChannel.close();
        fis.close();
    }
}
