package com.zyot.shyn.properties;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertiesDemo {
    public static void main(String[] args) {
        FileReader reader = null;
        try {
            reader = new FileReader("./src/main/resources/config.properties");

            Properties p = new Properties();
            p.load(reader);
            System.out.println(p.getProperty("username"));
            System.out.println(p.getProperty("password"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
