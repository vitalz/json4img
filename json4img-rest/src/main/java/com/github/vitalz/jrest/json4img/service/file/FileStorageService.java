package com.github.vitalz.jrest.json4img.service.file;

import java.io.File;
import java.util.function.Supplier;

public class FileStorageService implements FileStorage {
    private final Supplier<File> sharedDir;

    public FileStorageService(Supplier<File> sharedDir) {
        this.sharedDir = sharedDir;
    }

    @Override
    public Supplier<File> getSharedDir() {
        return this.sharedDir;
    }
}
