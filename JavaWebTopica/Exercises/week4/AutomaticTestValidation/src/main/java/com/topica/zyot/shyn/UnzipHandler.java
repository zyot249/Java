package com.topica.zyot.shyn;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipHandler {
    private static final int BUFFER = 2048;
    private static final String INPUT_ZIP_FILE = "output/windnd249@gmail.com-CV-Nguyen-Duc-Dung.zip";
    private static final String OUTPUT_FOLDER = "UnzipOutput/CV";

    public static void main(String[] args) {
        final String SOURCE_ZIPDIR = "output/windnd249@gmail.com-CV-Nguyen-Duc-Dung.zip";
        // creating the destination dir using the zip file path
        // by truncating the ".zip" part
        String DESTINATION_DIR = SOURCE_ZIPDIR.substring(0, SOURCE_ZIPDIR.lastIndexOf('.'));
        //System.out.println("" + DESTINATION_DIR);
        extract(SOURCE_ZIPDIR, DESTINATION_DIR);
    }

    public static void extract(String source, String dest) {
        try {
            File root = new File(dest);
            if (!root.exists()) {
                root.mkdir();
            }
            BufferedOutputStream bos = null;
            // zipped input
            FileInputStream fis = new FileInputStream(source);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String fileName = entry.getName();
                File file = new File(dest + File.separator + fileName);
                if (!entry.isDirectory()) {
                    extractFileContentFromArchive(file, zis);
                } else {
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                }
                zis.closeEntry();
            }
            zis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void extractFileContentFromArchive(File file, ZipInputStream zis) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER);
        int len;
        byte data[] = new byte[BUFFER];
        while ((len = zis.read(data, 0, BUFFER)) != -1) {
            bos.write(data, 0, len);
        }
        bos.flush();
        bos.close();
    }
}
