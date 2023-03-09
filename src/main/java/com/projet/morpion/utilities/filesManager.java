package com.projet.morpion.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class filesManager {
    public static List<String> getFilesInDirectory(String directoryPath) {
        List<String> fileList = new ArrayList<>();
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) throw new IllegalArgumentException("The path is not found");
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileList.add(file.getName());
                }
            }
        }
        return fileList;
    }

    public static void deleteFile(String s) {
        File file = new File(s);
        if (file.exists()) {
            file.delete();
        }
    }
}
