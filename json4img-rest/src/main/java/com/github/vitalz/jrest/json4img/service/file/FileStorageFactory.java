package com.github.vitalz.jrest.json4img.service.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public final class FileStorageFactory {
    private final Logger log = LoggerFactory.getLogger(FileStorageFactory.class);

    private String sharedDir;
    private String outputDir;

    public String getSharedDir() {
        return sharedDir;
    }

    public void setSharedDir(String sharedDir) {
        this.sharedDir = sharedDir;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public FileStorage createFileStorageService() {
        log.info("...Initializing file storage:\n\tsharedDir: '{}'\n\toutputDir: {}", this.sharedDir, this.outputDir);
        return new FileStorageService(() -> new File(this.sharedDir), () -> new File(this.outputDir)); // do not care of current state for file system at init
    }
}
