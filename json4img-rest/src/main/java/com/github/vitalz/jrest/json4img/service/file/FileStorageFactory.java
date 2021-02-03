package com.github.vitalz.jrest.json4img.service.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public final class FileStorageFactory {
    private final Logger log = LoggerFactory.getLogger(FileStorageFactory.class);

    private String sharedDir;

    public String getSharedDir() {
        return sharedDir;
    }

    public void setSharedDir(String sharedDir) {
        this.sharedDir = sharedDir;
    }

    public FileStorage createFileStorageService() {
        log.info("Initializing file storage path variable on a path: '{}'...", this.sharedDir);
        return new FileStorageService(() -> new File(this.sharedDir)); // do not care of current state for file system at init
    }
}
