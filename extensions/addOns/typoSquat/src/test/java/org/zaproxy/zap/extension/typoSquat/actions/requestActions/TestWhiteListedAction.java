package org.zaproxy.zap.extension.typoSquat.actions.requestActions;

import org.junit.Assert;
import org.junit.Test;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpRequestHeader;
import org.zaproxy.zap.extension.typoSquat.actions.TestAbstractAction;
import org.zaproxy.zap.extension.typoSquat.session.SessionManager;

import java.io.IOException;

public class TestWhiteListedAction extends TestAbstractAction {

    @Test
    @Override
    public void testExecute() throws IOException {
        //setup
        HttpMessage msg = new HttpMessage(new HttpRequestHeader("GET / HTTP/1.1\r\nHost: www.google.com\r\n"));
        WhiteListedAction action = new WhiteListedAction();
        SessionManager sessionManager = new SessionManager();
        //action to test
        boolean bool = action.execute(msg, sessionManager);
        //verification
        Assert.assertFalse(bool);
    }
}
