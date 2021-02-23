package org.zaproxy.zap.extension.policyRuleImplementation.ruleImplementations;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.parosproxy.paros.network.HttpMessage;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.mockito.MockitoAnnotations.initMocks;


public class TestHttpsTrafficRule {

    @InjectMocks
    private HttpsTrafficRule rule;

    private HttpMessage httpMessage;

    @Before
    public void setup(){
        initMocks(this);
        httpMessage = new HttpMessage();
    }

    @Test
    public void testIsHttps() {
        try {
            httpMessage.getRequestHeader().setSecure(true);
            assertTrue(rule.requestSatisfiesRule(httpMessage));
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    @Test
    public void testIsNotHttps() {
        try {
            httpMessage.getRequestHeader().setSecure(false);
            assertFalse(rule.requestSatisfiesRule(httpMessage));
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    @Test
    public void testResponse() {
        assertTrue(rule.responseSatisfiesRule(httpMessage));
    }
}
