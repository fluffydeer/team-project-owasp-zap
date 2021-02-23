package org.zaproxy.zap.extension.typoSquat.fileHandler;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

public class TestDomainHandler {

    private static final String resourceFolder = "src/test/resources/";
    private static final String originalFolder = "fileHandler/originalTestFiles";
    private static final String tempFolder = "fileHandler/tmp";

    private static final String whiteList = "whiteList.txt";
    private static final String blackList = "blackList.txt";

    private DomainHandler domainHandler;

    @Before
    public void setupDomainHandler() {
        try {
            FileUtils.copyDirectory(new File(resourceFolder + originalFolder),
                                    new File(resourceFolder + tempFolder));
            domainHandler = new DomainHandler(resourceFolder + tempFolder);
        } catch (IOException e) {
            Assert.fail("Test setup failed");
        }
    }

    @After
    public void reset() {
        try {
            FileUtils.cleanDirectory(new File(resourceFolder+tempFolder));
        } catch (IOException e) {
            Assert.fail();
        }
    }

    @Test
    public void testInitialiseWhiteList() {
        for (String domain : readFile(resourceFolder + originalFolder + File.separator + whiteList)) {
            if (!domainHandler.isWhiteListed(domain)) {
                Assert.fail();
            }
        }
    }

    @Test
    public void testInitialiseBlackList() {
        for (String blackListPair : readFile(resourceFolder + originalFolder + File.separator + blackList)) {
            String domain = blackListPair.split(", ")[0];
            String redirect = blackListPair.split(", ")[1];

            if (! domainHandler.isBlackListed(domain) ||
                   ! domainHandler.getRedirect(domain).equals(redirect)) {
                Assert.fail();
            }
        }
    }

    @Test
    public void testNotIsWhiteListed() {
        Assert.assertFalse(domainHandler.isWhiteListed("nonExistingDomainName"));
    }

    @Test
    public void testNotIsBlackListed() {
        Assert.assertFalse(domainHandler.isBlackListed("nonExistingDomainName"));
    }

    @Test
    public void addToWhiteList() {
        String newDomain = "www.newDomain.test";
        domainHandler.addToWhiteList(newDomain);
        Assert.assertTrue(domainHandler.isWhiteListed(newDomain));
        Assert.assertTrue(fileContains(resourceFolder+tempFolder+File.separator+whiteList, newDomain));
    }

    @Test
    public void addToBlackList() {
        String newDomain = "www.newDomain.test";
        String redirectDomain = "redirectDomain";
        domainHandler.addToBlackList(newDomain, redirectDomain);
        Assert.assertTrue(domainHandler.isBlackListed(newDomain));
        Assert.assertEquals(redirectDomain, domainHandler.getRedirect(newDomain));
        Assert.assertTrue(fileContains(resourceFolder+tempFolder+File.separator+blackList, newDomain + ", " + redirectDomain));
    }


    private ArrayList<String> readFile(String filePath) {
        ArrayList<String> list = new ArrayList<>();
        File file = new File(filePath);
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception e) {
            Assert.fail("Problem while loading file: " + file);
        }
        return list;
    }

    private boolean fileContains(String file, String string) {
        return readFile(file).contains(string);
    }
}
