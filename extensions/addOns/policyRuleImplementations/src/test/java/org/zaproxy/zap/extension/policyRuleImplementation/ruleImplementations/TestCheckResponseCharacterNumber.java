package org.zaproxy.zap.extension.policyRuleImplementation.ruleImplementations;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.network.HttpResponseBody;

import java.nio.charset.Charset;
import java.util.Random;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TestCheckResponseCharacterNumber {

    @InjectMocks
    private CheckResponseCharacterNumber checkResponseCharacterNumber;

    @Mock
    private HttpMessage httpMessage;

    private HttpResponseBody httpResponseBody;

    @Before
    public void setup(){
        initMocks(this);
    }

    @Test
    public void messageSatisfiesRule(){

        httpResponseBody = new HttpResponseBody("No more than 200 characters");

        when(httpMessage.getResponseBody()).thenReturn(httpResponseBody);

        assertTrue(checkResponseCharacterNumber.responseSatisfiesRule(httpMessage));
    }

    @Test
    public void messageDoesNotSatisfiesRule(){

        byte[] array = new byte[3002]; // length is bounded by 2001
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));

        httpResponseBody = new HttpResponseBody(generatedString);

        when(httpMessage.getResponseBody()).thenReturn(httpResponseBody);

        assertFalse(checkResponseCharacterNumber.responseSatisfiesRule(httpMessage));
    }

    @Test
    public void requestSatisfiesRule() {
        assertTrue(checkResponseCharacterNumber.requestSatisfiesRule(httpMessage));
    }

    @Test
    public void getName(){
        assertTrue(checkResponseCharacterNumber.getName().equals("CheckResponseCharacterNumber"));
    }
}
