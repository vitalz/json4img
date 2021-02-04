package com.github.vitalz.jrest.json4img.service.file;

import java.io.File;
import java.util.function.Supplier;

public class FileStorageService implements FileStorage {
    private final Supplier<File> sharedDir;
    private final Supplier<File> outputDir;

    public FileStorageService(Supplier<File> sharedDir, Supplier<File> outputDir) {
        this.sharedDir = sharedDir;
        this.outputDir = outputDir;
    }

    @Override
    public Supplier<File> getSharedDir() {
        return this.sharedDir;
    }

    @Override
    public Supplier<File> getOutputDir() {
        return this.outputDir;
    }
}
