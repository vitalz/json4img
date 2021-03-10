package com.github.vitalz.jrest.json4img.service.file;

import org.apache.commons.io.FilenameUtils;

import java.util.function.BiFunction;

public final class ConvertedFileName implements BiFunction<String, String, String> {

    @Override
    public String apply(String fileName, String ext2) {
        return String.format("%1$s.%2$s", FilenameUtils.removeExtension(fileName), ext2);
    }
}