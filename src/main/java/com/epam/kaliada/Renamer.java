package com.epam.kaliada;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Renamer {
    private final static Logger LOGGER = LogManager.getLogger();

    public void renameFiles(Path[] files, String suffix) throws IOException {
        for (int i = 0; i < files.length; i++) {
            if (checkFiles(files[i])){
                String fileName = files[i].getFileName().toString();
                int dot = fileName.lastIndexOf(".");
                StringBuilder builder = new StringBuilder(fileName);
                builder = builder.insert(dot, suffix);
                String newFileName = builder.toString();
                LOGGER.log(Level.INFO, String.format("start renaming file %s", fileName));
                Files.move(files[i], files[i].resolveSibling(newFileName));
                LOGGER.log(Level.INFO, String.format("%s -> %s", fileName, newFileName));
            }else {
                LOGGER.log(Level.WARN, String.format("File %s doesn't exist", files[i]));
            }
        }
    }

    private boolean checkFiles(Path file){
        return file != null && Files.exists(file);
    }
}
