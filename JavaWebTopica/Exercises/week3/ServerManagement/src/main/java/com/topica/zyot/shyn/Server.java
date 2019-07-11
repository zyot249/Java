package com.topica.zyot.shyn;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static final String NOTIFY_SUCCESSFUL_CONNECTED = "\nSuccessfully Connected!";
    private static final int SERVER_PORT = 6969;
    public static final int SIZE_OF_BUFFER = 64 * 1024;
    private static ServerSocket mServerSocket;
    private static ArrayList<WorkerThread> workers;
    private static final int mMaxThreads = 1;

    public static void main(String[] args) throws IOException {
        mServerSocket = new ServerSocket(SERVER_PORT);
        workers = new ArrayList<>();
        while (true) {
            if (workers.size() < mMaxThreads) {
                Socket socket = mServerSocket.accept();
                System.out.println(NOTIFY_SUCCESSFUL_CONNECTED);
                WorkerThread workerThread = new WorkerThread(socket);
                workers.add(workerThread);
                workerThread.start();
            }
        }
    }

    private static class WorkerThread extends Thread {
        private Socket socket;

        private static final String NOTIFY_FINISHED = "Finished!";
        private static final String MSG_FINISH = "exit";

        WorkerThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
//                System.out.println(NOTIFY_SUCCESSFUL_CONNECTED);
                System.out.println(socket);
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                while (true) {
                    byte[] buffer = new byte[SIZE_OF_BUFFER];
                    int readBytes = inputStream.read(buffer);
                    StringBuilder message = new StringBuilder();
                    for (int i = 0; i < readBytes; i++) {
                        message.append((char) buffer[i]);
                    }
                    if (!message.toString().isEmpty()) {
                        if (message.toString().equalsIgnoreCase(MSG_FINISH)) {
                            System.out.println(NOTIFY_FINISHED);
                            break;
                        } else {
                            System.out.println(message);
                        }
                    }
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            workers.remove(this);
        }
    }
}
