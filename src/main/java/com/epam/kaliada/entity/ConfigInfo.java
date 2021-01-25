package com.epam.kaliada.entity;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ConfigInfo {

    private List<Path> files;
    private String suffix;

    public ConfigInfo() {
        files = new ArrayList<>();
    }

    public List<Path> getFiles() {
        return files;
    }

    public void setFiles(List<Path> files) {
        this.files = files;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void addFile(Path path){
        files.add(path);
    }
}
