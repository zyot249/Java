package com.topica.zyot.shyn.utils;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipHandler {
    private static final int BUFFER = 2048;
    private static final HashMap<String, Set<String>> fileJavaOfStudent = new HashMap<>();

    public static void main(String[] args) {
        final String sourceZipdir = "output/windnd249@gmail.com-CV-Nguyen-Duc-Dung.zip";
        // creating the destination dir using the zip file path
        // by truncating the ".zip" part
        String destinationDir = sourceZipdir.substring(0, sourceZipdir.lastIndexOf('.'));
        extract("windnd249@gmail.com", sourceZipdir, destinationDir);
    }

    private static void addFileJavaOfStudent(String address, String filePath) {
        synchronized (fileJavaOfStudent) {
            Set<String> paths = fileJavaOfStudent.get(address);
            if (paths == null) {
                paths = new HashSet<>();
                paths.add(filePath);
                fileJavaOfStudent.put(address, paths);
            } else {
                paths.add(filePath);
                fileJavaOfStudent.replace(address, paths);
            }
        }
    }

    public static Map<String, Set<String>> getAllFileJavaPaths() {
        synchronized (fileJavaOfStudent) {
            if (fileJavaOfStudent.isEmpty())
                return Collections.emptyMap();
            else {
                HashMap<String, Set<String>> paths = new HashMap<>(fileJavaOfStudent);
                fileJavaOfStudent.clear();
                return paths;
            }
        }
    }

    public static void extract(String address, String source, String dest) {
        try {
            File root = new File(dest);
            if (!root.exists()) {
                root.mkdir();
            }
            // zipped input
            FileInputStream fis = new FileInputStream(source);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String fileName = entry.getName();
                String filePath = dest + File.separator + fileName;
                File file = new File(filePath);
                if (!entry.isDirectory()) {
                    String extension = fileName.substring(fileName.lastIndexOf('.'));
                    if (extension.equalsIgnoreCase(".java")) {
                        addFileJavaOfStudent(address, filePath);
                    }
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
        byte[] data = new byte[BUFFER];
        while ((len = zis.read(data, 0, BUFFER)) != -1) {
            bos.write(data, 0, len);
        }
        bos.flush();
        bos.close();
    }
}
