package org.zaproxy.zap.extension.typoSquat.actions.requestActions;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpRequestHeader;
import org.zaproxy.zap.extension.typoSquat.fileHandler.DomainHandler;
import org.zaproxy.zap.extension.typoSquat.session.SessionManager;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;

public class TestTypoSquatAction {

    private static final String resourceFolder = "src/test/resources/";
    private static final String originalFolder = "fileHandler/originalTestFiles";
    private static final String tempFolder = "fileHandler/tmp";

    private TypoSquatAction typoSquatAction;
    private SessionManager sessionManager;
    private HttpMessage msg;

    @Before
    public void setup() {
        sessionManager = new SessionManager();
        msg = createMsg();

        DomainHandler domainHandler = createDomainHandler();
        LinkedHashSet<String> matchedDomains = createMatchedDomainsSet();

        typoSquatAction = new TypoSquatAction(matchedDomains, domainHandler);
    }

    private DomainHandler createDomainHandler() {
        try {
            FileUtils.copyDirectory(
                    new File(resourceFolder + originalFolder),
                    new File(resourceFolder + tempFolder)
            );

            return new DomainHandler(resourceFolder + tempFolder);
        } catch (IOException e) {
            Assert.fail("Test setup failed");
            throw new IllegalStateException("failed to setup DomainHandler");
        }
    }

    private LinkedHashSet<String> createMatchedDomainsSet() {
        LinkedHashSet<String> set = new LinkedHashSet<>();
        set.add("domain1.com");
        set.add("domain2.be");
        set.add("domain3.org");
        return set;
    }

    private HttpMessage createMsg() {
        String header = "GET /docs/index.html HTTP/1.1\r\n" +
                        "Host: www.original_domain.com";
        try {
            return new HttpMessage(new HttpRequestHeader(header));
        } catch (HttpMalformedHeaderException e) {
            Assert.fail("Problem with setting up dummy http message");
            throw new IllegalStateException("Problem with setting up dummy http message");
        }
    }

    @Test
    public void testExecute() {
        try {
            boolean result = typoSquatAction.execute(msg, sessionManager);
            Assert.assertTrue(result);
            Assert.assertEquals(expectedHeader,
                    msg.getResponseHeader().toString().trim().replaceAll("\\n|\\r\\n",
                            ""));

        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    private static final String expectedHeader = "HTTP/1.0 200" +
                                                 "Content-Type: text/html; charset=utf-8"
                                                         .replaceAll("\\n|\\r\\n",
                                                         "");

}
