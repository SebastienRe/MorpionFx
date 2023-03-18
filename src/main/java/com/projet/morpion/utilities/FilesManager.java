package com.projet.morpion.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilesManager {
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

    public static String getModelSrl(String difficulty) {
        try {
            File file = new File("./resources/config.txt");
            FileReader fr = null;
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                String[] configLu;
                configLu = line.split(":");
                if (configLu[0].equals(difficulty)) {
                    return "model_"+configLu[3]+"_"+configLu[1]+"_"+configLu[2]+".srl";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
