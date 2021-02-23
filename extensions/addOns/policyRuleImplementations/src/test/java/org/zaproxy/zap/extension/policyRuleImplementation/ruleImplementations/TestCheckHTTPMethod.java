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

public class TestCheckHTTPMethod {

    @InjectMocks
    private CheckHTTPMethod checkHTTPMethod;

    @Mock
    private HttpMessage httpMessage;

    private HttpRequestHeader httpRequestHeader;

    @Before
    public void setup(){
        initMocks(this);
    }

    @Test
    public void messageSatisfiesRuleFalse() throws Exception{

        httpRequestHeader = new HttpRequestHeader
                ("PUT / HTTP/1.1\r\nHost: www.domain1.com\r\n");

        when(httpMessage.getRequestHeader()).thenReturn(httpRequestHeader);

        assertFalse(checkHTTPMethod.requestSatisfiesRule(httpMessage));
    }

    @Test
    public void messageSatisfiesRuleTrue() throws Exception{

        httpRequestHeader = new HttpRequestHeader
                ("GET / HTTP/1.1\r\nHost: sssom@test.com\r\n");

        when(httpMessage.getRequestHeader()).thenReturn(httpRequestHeader);

        assertTrue(checkHTTPMethod.requestSatisfiesRule(httpMessage));
    }

    @Test
    public void testResponse() {
        assertTrue(checkHTTPMethod.responseSatisfiesRule(httpMessage));
    }


    @Test
    public void getName(){
        assertTrue(checkHTTPMethod.getName().equals("CheckHTTPMethod"));
    }
}
