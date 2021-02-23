package org.zaproxy.zap.extension.typoSquat;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class TestUtils {
    public static void setupDirectory(String buildFolder) {
        //empty the build/test/fileHandler directory
        File buildDir = new File(buildFolder);
        if (buildDir.exists()) {
            try {
                FileUtils.deleteDirectory(buildDir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //create the build/test directory and create a fileHandler object
        buildDir.mkdirs();
    }
}
