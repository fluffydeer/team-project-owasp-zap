package org.zaproxy.zap.extension.typoSquat.actions.userActions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpRequestHeader;
import org.zaproxy.zap.extension.typoSquat.session.SessionManager;
import org.zaproxy.zap.network.HttpRequestBody;
import org.zaproxy.zap.network.HttpResponseBody;

import java.util.UUID;


public class TestUserNoAction extends TestAbstractUserAction {
    private UserNoAction userResponseNoAction;
    private HttpMessage msg;
    private UUID no_action_uuid;
    private static final String DOMAIN = "google.com";
    private static final String ERROR_DOMAIN = "goggle.com";

    @Before
    public void setUp() throws HttpMalformedHeaderException {
        no_action_uuid = UUID.randomUUID();
        userResponseNoAction = new UserNoAction(no_action_uuid.toString(), DOMAIN);
        msg = new HttpMessage(
                new HttpRequestHeader("GET https://google.com HTTP/1.1"),
                new HttpRequestBody("some data")
            );
    }

    @Test
    @Override
    public void testExecute(){
        SessionManager sessionManager = new SessionManager();

        boolean bool = userResponseNoAction.execute(msg, sessionManager);           //correct scenario

        Assert.assertTrue(bool);
        Assert.assertEquals(new HttpResponseBody("No action taken."), msg.getResponseBody());       //expecting body
    }
}
