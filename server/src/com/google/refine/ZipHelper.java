package com.google.refine;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipHelper {

    public static void extract(String source, String destination) throws IOException {
        byte[] buf = new byte[1024];
        ZipInputStream zipInputStream;
        ZipEntry zipEntry;
        zipInputStream = new ZipInputStream(new FileInputStream(source));

        zipEntry = zipInputStream.getNextEntry();
        while (zipEntry != null) {
            String entryName = destination + zipEntry.getName();
            entryName = entryName.replace('/', File.separatorChar);
            entryName = entryName.replace('\\', File.separatorChar);
            int n;
            FileOutputStream fileOutputStream;
            File newFile = new File(entryName);
            if (zipEntry.isDirectory()) {
                if (!newFile.mkdirs()) {
                    break;
                }
                zipEntry = zipInputStream.getNextEntry();
                continue;
            }

            fileOutputStream = new FileOutputStream(entryName);

            while ((n = zipInputStream.read(buf, 0, 1024)) > -1) {
                fileOutputStream.write(buf, 0, n);
            }

            fileOutputStream.close();
            zipInputStream.closeEntry();
            zipEntry = zipInputStream.getNextEntry();
        }

        zipInputStream.close();
    }
}
