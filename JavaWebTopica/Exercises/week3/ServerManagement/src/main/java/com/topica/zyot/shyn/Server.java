package com.topica.zyot.shyn;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int SERVER_PORT = 6969;
    public static final int SIZE_OF_BUFFER = 64 * 1024;
    private static ServerSocket mServerSocket;

    public static void main(String[] args) throws IOException {
        mServerSocket = new ServerSocket(SERVER_PORT);
        ConnectionThread thread1 = new ConnectionThread();
        ConnectionThread thread2 = new ConnectionThread();
        thread1.start();
//        thread2.start();
        while (thread1.isAlive() || thread2.isAlive()) ;
        mServerSocket.close();
    }

    private static class ConnectionThread extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    Socket socket = mServerSocket.accept();
                    DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                    while (!socket.isClosed()) {
                        byte[] buffer = new byte[SIZE_OF_BUFFER];
                        int readBytes = inputStream.read(buffer);
                        StringBuilder message = new StringBuilder();
                        for (int i = 0; i < readBytes; i++) {
                            message.append((char) buffer[i]);
                        }
                        if (message.toString().equalsIgnoreCase("exit"))
                            socket.close();
                        if (!message.toString().isEmpty())
                            System.out.println(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
