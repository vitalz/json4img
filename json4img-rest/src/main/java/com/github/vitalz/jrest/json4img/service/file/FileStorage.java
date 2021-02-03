package com.github.vitalz.jrest.json4img.service.file;

import java.io.File;
import java.util.function.Supplier;

public interface FileStorage {
    Supplier<File> getSharedDir();
}
