package com.epam.kaliada;

import java.nio.file.Path;

public class ConfigInfo {

    private Path[] files;
    private String suffix;

    public ConfigInfo(Path[] files, String suffix) {
        this.files = files;
        this.suffix = suffix;
    }

    public Path[] getFiles() {
        return files;
    }

    public void setFiles(Path[] files) {
        this.files = files;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
