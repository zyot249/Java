package com.topica.zyot.shyn;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(1234);
        Socket client = server.accept();
        DataInputStream is = new DataInputStream(client.getInputStream());
        DataOutputStream os = new DataOutputStream(client.getOutputStream());
        String line = is.readLine();
        os.writeBytes("Shynnnnnn");
        while ((line = is.readLine()) != null){
            System.out.println(line);
        }
        client.close();
    }
}
