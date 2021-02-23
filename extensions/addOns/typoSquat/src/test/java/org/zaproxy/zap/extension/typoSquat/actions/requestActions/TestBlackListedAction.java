package org.zaproxy.zap.extension.typoSquat.actions.requestActions;

import org.junit.Assert;
import org.junit.Test;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpRequestHeader;
import org.zaproxy.zap.extension.typoSquat.actions.TestAbstractAction;
import org.zaproxy.zap.extension.typoSquat.session.SessionManager;

import java.io.IOException;

public class TestBlackListedAction extends TestAbstractAction {

    @Test
    public void testBlackListedConstructor() {
        BlackListedAction action = new BlackListedAction("www.google.com");
        Assert.assertEquals("www.google.com", action.getRedirectDomain());
    }

    @Test
    @Override
    public void testExecute() throws IOException {
        //setup
        HttpMessage msg = new HttpMessage(new HttpRequestHeader("GET / HTTP/1.1\r\nHost: www.goggle.com\r\n"));
        BlackListedAction action = new BlackListedAction("www.google.com");
        SessionManager sessionManager = new SessionManager();
        //action to test
        boolean bool = action.execute(msg, sessionManager);
        //verification
        Assert.assertEquals(307, msg.getResponseHeader().getStatusCode());
        Assert.assertEquals("//www.google.com", msg.getResponseHeader().getHeader("Location"));
        Assert.assertTrue(bool);
    }
}
