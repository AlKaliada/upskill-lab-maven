package com.epam.kaliada;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Renamer {
    private static Logger logger = LogManager.getLogger();

    public void renameFiles(Path[] files, String suffix) throws IOException {
        for (int i = 0; i < files.length; i++) {
            if (checkFiles(files[i])){
                String fileName = files[i].getFileName().toString();
                int dot = fileName.lastIndexOf(".");
                StringBuilder builder = new StringBuilder(fileName);
                builder = builder.insert(dot, suffix);
                String newFileName = builder.toString();
                logger.log(Level.INFO, "start renaming file " + fileName);
                Files.move(files[i], files[i].resolveSibling(newFileName));
                logger.log(Level.INFO, fileName + "->" + newFileName);
            }else {
                logger.log(Level.WARN, "File " + files[i] + " doesn't exist");
            }
        }
    }

    private boolean checkFiles(Path file){
        return file != null && Files.exists(file);
    }
}
