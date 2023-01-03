package com.github.vitalz.jrest.json4img.service.file;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class FileStorageServiceTest {

    @Rule
    public TemporaryFolder tempInDirectory = new TemporaryFolder();
    @Rule
    public TemporaryFolder tempOutDirectory = new TemporaryFolder();

    @Test
    public void test() {
        File testInDir = tempInDirectory.getRoot();
        File testOutDir = tempOutDirectory.getRoot();

        FileStorage fileStorage = new FileStorageService(() -> testInDir, () -> testOutDir);

        assertEquals(testInDir.getAbsolutePath(), fileStorage.getSharedDir().get().getAbsolutePath());
        assertEquals(testOutDir.getAbsolutePath(), fileStorage.getOutputDir().get().getAbsolutePath());
    }

}
