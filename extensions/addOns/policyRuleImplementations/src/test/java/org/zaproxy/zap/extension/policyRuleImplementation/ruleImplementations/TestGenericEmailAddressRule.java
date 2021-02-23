package org.zaproxy.zap.extension.policyRuleImplementation.ruleImplementations;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpRequestHeader;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TestGenericEmailAddressRule {

    @InjectMocks
    private GenericEmailAddressRule genericEmailAddressRule;

    @Mock
    private HttpMessage httpMessage;

    private HttpRequestHeader httpRequestHeader;

    @Before
    public void setup(){
        initMocks(this);
    }

    @Test
    public void messageSatisfiesRule() throws Exception{

        httpRequestHeader = new HttpRequestHeader
                ("GET / HTTP/1.1\r\nHost: www.domain1.com\r\n");

        when(httpMessage.getRequestHeader()).thenReturn(httpRequestHeader);

        assertTrue(genericEmailAddressRule.requestSatisfiesRule(httpMessage));
    }

    @Test
    public void messageSatisfiesRuleWithEmailAddress() throws Exception{

        httpRequestHeader = new HttpRequestHeader
                ("GET / HTTP/1.1\r\nHost: sssom@test.com\r\n");

        when(httpMessage.getRequestHeader()).thenReturn(httpRequestHeader);

        assertFalse(genericEmailAddressRule.requestSatisfiesRule(httpMessage));
    }

    @Test
    public void testResponse() {
        assertTrue(genericEmailAddressRule.responseSatisfiesRule(httpMessage));
    }

    @Test
    public void getName(){
        assertTrue(genericEmailAddressRule.getName().equals("GenericEmailAddressRule"));
    }

}
