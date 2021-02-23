package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.getters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
import org.parosproxy.paros.network.HttpMessage;

import static org.junit.Assert.*;

public class TestResponseGetter {
    private ResponseGetter getter;
    private final static String name = "RES";


    @Before
    public void setUp(){
        getter = new ResponseGetter();
    }

    @Test
    public void testGetName(){
        Assert.assertEquals(name, getter.getName());
    }

    @Test
    public void testEval() throws HttpMalformedHeaderException {
        String resHeader = "HTTP/1.0 200";
        String resBody = "<html>content</html>";

        HttpMessage msg = new HttpMessage();
        msg.setResponseHeader(resHeader);
        resHeader = msg.getResponseHeader().toString();//have to update the value, bc the layout is changed by processing
        msg.setResponseBody(resBody);
        SingleMessage actualSingleMessage = getter.eval(msg);

        assertEquals(new SingleMessage(resHeader, resBody).getHeader(), actualSingleMessage.getHeader());
        assertEquals(new SingleMessage(resHeader, resBody).getBody(), actualSingleMessage.getBody());
    }

}