package org.zaproxy.zap.extension.typoSquat.session;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.extension.typoSquat.actions.userActions.AbstractUserAction;

public class TestSessionManager {

    private SessionManager sessionManager;

    private class DummyAction extends AbstractUserAction {
        protected DummyAction(String uuid, String originalDomain) {
            super(uuid, originalDomain);
        }

        @Override
        public boolean execute(HttpMessage msg, SessionManager manager) {
            return false;
        }
    }

    @Before
    public void setup() {
        sessionManager = new SessionManager();
    }

    @Test
    public void addActionNewDomain() {
        DummyAction action = new DummyAction("uuid", "original_domain.com");
        sessionManager.addAction(action);

        Assert.assertTrue(sessionManager.hasAction("uuid"));
        Assert.assertEquals(action, sessionManager.getAction("uuid"));
    }

    @Test
    public void addActionExistingDomain() {
        DummyAction action1 = new DummyAction("uuid1", "original_domain.com");
        sessionManager.addAction(action1);
        DummyAction action2 = new DummyAction("uuid2", "original_domain.com");
        sessionManager.addAction(action2);
        sessionManager.getAction("uuid2");

        Assert.assertEquals(action1, sessionManager.getAction("uuid1"));
        Assert.assertEquals(action2, sessionManager.getAction("uuid2"));
    }

    @Test
    public void invalidateDomain() {
        addActionExistingDomain();
        sessionManager.invalidateDomainKeys("original_domain.com");

        try {
            sessionManager.getAction("uuid1");
            sessionManager.getAction("uuid2");
            Assert.fail();
        } catch (IllegalArgumentException e) {}
    }

}
