package org.zaproxy.zap.extension.typoSquat.fileHandler;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class TestFileHandler {

    private static final String resourceFolder = "src/test/resources/";
    private static final String buildFolder = "build/test/fileHandler";


    private static final String whiteList = "whiteList.txt";
    private static final String blackList = "blackList.txt";

    private FileHandler fileHandler;

    @Before
    public void setupFileHandler() {
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

        fileHandler = new FileHandler(buildFolder);
    }

    @Test
    public void testFileHandlerConstructor() {
        FileHandler fileHandler = new FileHandler(buildFolder);
        Assert.assertEquals(fileHandler.getDirectoryLocation(), buildFolder);
    }

    @Test
    public void testInitializeFile_alreadyExists() {
        File white = new File(buildFolder + File.separator + whiteList);
        Assert.assertTrue(white.exists() && white.isFile());
        File black = new File(buildFolder + File.separator + blackList);
        Assert.assertTrue(black.exists() && black.isFile());

        fileHandler.initializeFile(buildFolder);

        Assert.assertTrue(white.exists() && white.isFile());
        Assert.assertTrue(black.exists() && black.isFile());
    }

    @Test
    public void testInitializeFile() {
        String path = buildFolder + File.separator + "testLocation/";
        File newDir = new File(path);
        newDir.mkdir();
        fileHandler.initializeFile(path);
        File white = new File(path + File.separator + whiteList);
        Assert.assertTrue(white.exists() && white.isFile());
        File black = new File(path + File.separator + blackList);
        Assert.assertTrue(black.exists() && black.isFile());
    }

    @Test
    public void testCreateWhiteList() {
        String path = buildFolder + File.separator + "whiteListTest.txt";
        File white = new File(path);
        fileHandler.createWhiteList(white);
        try {
            String text = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
            Assert.assertTrue(text.contains("www.domain3.nl"));
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testCreateBlackList() {
        String path = buildFolder + File.separator + "blackListTest.txt";
        File black = new File(path);
        fileHandler.createBlackList(black);
        Assert.assertTrue(black.exists());
        try {
            String text = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
            Assert.assertTrue(text.isEmpty());
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testAddToFile() {
        String domain = "www.cookies.com";
        fileHandler.addToFile(whiteList, domain);
        try {
            String text = new String(Files.readAllBytes(Paths.get(buildFolder + File.separator + whiteList)), StandardCharsets.UTF_8);
            Assert.assertTrue(text.contains(domain));
        } catch (IOException e) {
            Assert.fail("Not able to read whitelist.");
        }
    }

    @Test
    public void testReadWhiteList() {
        LinkedHashSet<String> whiteFile = fileHandler.readWhiteList(whiteList);
        Assert.assertTrue(whiteFile.contains("www.domain1.com"));
        Assert.assertTrue(whiteFile.contains("www.domain4.org"));
    }

    @Test
    public void testReadBlackList() {
        File initBlack = new File(resourceFolder + File.separator + "blackList.txt");
        File black = new File(buildFolder + File.separator + blackList);
        try {
            FileUtils.copyFile(initBlack, black);
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
        HashMap<String, String> blackFile = fileHandler.readBlackList(blackList);
        Assert.assertTrue(blackFile.containsKey("www.domain101.com"));
        Assert.assertEquals(blackFile.get("www.domain101.com"), "redirect1");
    }
}
