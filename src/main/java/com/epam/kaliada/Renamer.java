package com.epam.kaliada;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Renamer {
    public void renameFiles(Path[] files, String suffix) throws IOException {
        for (int i = 0; i < files.length; i++) {
            if (checkFiles(files[i])){
                String fileName = files[i].getFileName().toString();
                int dot = fileName.lastIndexOf(".");
                StringBuilder builder = new StringBuilder(fileName);
                builder = builder.insert(dot, suffix);
                String newFileName = builder.toString();
                Files.move(files[i], files[i].resolveSibling(newFileName));
                System.out.println(fileName + "->" + newFileName);
            }else {
                System.out.println("File " + files[i] + " doesn't exist");
            }
        }
    }

    private boolean checkFiles(Path file){
        return Files.exists(file);
    }
}
