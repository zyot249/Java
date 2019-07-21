package com.topica.zyot.shyn.utils;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipHandler {
    private static final Logger logger = Logger.getLogger(UnzipHandler.class);
    private static final int BUFFER = 2048;
    private static final HashMap<String, Set<String>> fileJavaOfStudent = new HashMap<>();

    private UnzipHandler() {

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
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(source)));
            doExtract(zis, address, dest);
            zis.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static void doExtract(ZipInputStream zis, String address, String dest) throws IOException {
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
    }

    private static void extractFileContentFromArchive(File file, ZipInputStream zis) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER);
        readAndWriteFileContentFromArchive(zis, bos);
        bos.flush();
        bos.close();
    }

    private static void readAndWriteFileContentFromArchive(ZipInputStream zis, BufferedOutputStream bos) throws IOException {
        int len;
        byte[] data = new byte[BUFFER];
        while ((len = zis.read(data, 0, BUFFER)) != -1) {
            bos.write(data, 0, len);
        }
    }
}
