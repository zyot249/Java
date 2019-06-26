package com.topica.zyot.shyn;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DevLog {
	public static final String LOG_FILE = "/home/shyn/Github/zyot249/Java/JavaWebTopica/Exercises/week2/log.txt";

    public static void log(Object message) {
        if (message == null) {
            return;
        }

        // Make sure the path exists.
        new File(LOG_FILE).getParentFile().mkdirs();
        //
        FileWriter writer = null;
        try {
            writer = new FileWriter(LOG_FILE, true);
            writer.append(message.toString());
            writer.append("\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                writer.close();
            } catch (IOException e1) {
            }
        }
    }
}
