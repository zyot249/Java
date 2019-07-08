package com.topica.zyot.shyn;

import java.io.*;
import java.net.Socket;

public class Client2 {
    private static final String CLIENT_NAME = "Shyn";
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 6969;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        byte[] buffer = new byte[Server.SIZE_OF_BUFFER];
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        while (!socket.isClosed()) {
            String message = reader.readLine();
            if (!message.isEmpty()) {
                buffer = (CLIENT_NAME + ": " + message).getBytes();
                outputStream.write(buffer);
                if (message.equalsIgnoreCase("exit"))
                    break;
            }
        }
        inputStreamReader.close();
        socket.close();
    }
}
