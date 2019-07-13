package com.topica.zyot.shyn;

public class Utils {
    public static String getExtensionOfFile(String fileName) {
        if (fileName == null)
            return null;
        int indexOfDot = fileName.indexOf('.');
        return fileName.substring(indexOfDot + 1);
    }
}
