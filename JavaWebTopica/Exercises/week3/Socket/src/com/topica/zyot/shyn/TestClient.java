package com.topica.zyot.shyn;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestClient {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket client = new Socket("localhost", 1234);
        DataInputStream is = new DataInputStream(client.getInputStream());
        DataOutputStream os = new DataOutputStream(client.getOutputStream());
        String line = is.readLine();
        os.writeBytes("Shynnnnnn");
        client.close();
    }
}
