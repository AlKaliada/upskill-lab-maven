package com.epam.kaliada.service;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Renamer {
    private final static Logger LOGGER = LogManager.getLogger();
    private Map<String, String> renamedFiles = new HashMap<>();

    public Map<String, String> getRenamedFiles() {
        return Collections.unmodifiableMap(renamedFiles);
    }

    public void renameFiles(List<Path> files, String suffix) throws IOException {
        LOGGER.log(Level.TRACE, "start renaming files");
        for (int i = 0; i < files.size(); i++) {
            if (checkFiles(files.get(i))){
                String fileName = files.get(i).getFileName().toString();
                int dot = fileName.lastIndexOf(".");
                StringBuilder builder = new StringBuilder(fileName);
                builder = builder.insert(dot, suffix);
                String newFileName = builder.toString();
                LOGGER.log(Level.TRACE, "start renaming file {}", fileName);
                Files.move(files.get(i), files.get(i).resolveSibling(newFileName));
                renamedFiles.put(fileName, newFileName);
                LOGGER.log(Level.TRACE, "{} -> {}", fileName, newFileName);
            }else {
                LOGGER.log(Level.WARN, "File {} doesn't exist", files.get(i));
            }
        }
        LOGGER.log(Level.TRACE, "finish renaming files");
    }

    private boolean checkFiles(Path file){
        return file != null && Files.exists(file);
    }
}
