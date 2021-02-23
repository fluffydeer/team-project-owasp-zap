package org.zaproxy.zap.extension.typoSquat.actions.requestActions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpRequestHeader;
import org.zaproxy.zap.extension.typoSquat.actions.TestAbstractAction;
import org.zaproxy.zap.extension.typoSquat.fileHandler.DomainHandler;
import org.zaproxy.zap.extension.typoSquat.fileHandler.FileHandler;
import org.zaproxy.zap.extension.typoSquat.session.SessionManager;

import java.io.File;
import java.io.IOException;

public class TestNoTypoSquatAction extends TestAbstractAction {

    private static final String buildFolder = "build/test";
    private static DomainHandler domainHandler;

    @Before
    public void init() {
        //empty the build/test/fileWriter directory
        File buildDir = new File(buildFolder);

        if (! buildDir.exists()) {
            buildDir.mkdirs();
        }

        new FileHandler(buildFolder);
        domainHandler = new DomainHandler(buildFolder);
    }

    @Test
    public void testNoTypoSquatConstructor() {
        NoTypoSquatAction action = new NoTypoSquatAction("www.abercrombie.com", domainHandler);

        Assert.assertEquals("www.abercrombie.com", action.getDomainToBeAdded());
        Assert.assertEquals(domainHandler, action.getDomainHandler());
    }

    @Test
    @Override
    public void testExecute() throws IOException {
        //setup
        HttpMessage msg = new HttpMessage(new HttpRequestHeader("GET / HTTP/1.1\r\nHost: www.abercrombie.com\r\n"));
        NoTypoSquatAction action = new NoTypoSquatAction("www.abercrombie.com", domainHandler);
        SessionManager sessionManager = new SessionManager();
        //action to test
        boolean bool = action.execute(msg, sessionManager);
        //verification
        Assert.assertTrue(domainHandler.isWhiteListed("www.abercrombie.com"));
        Assert.assertFalse(bool);
    }
}
