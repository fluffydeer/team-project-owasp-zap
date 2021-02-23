package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.getters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpRequestHeader;

import static org.junit.Assert.*;

public class TestRequestGetter {
    private RequestGetter getter;
    private final static String name = "REQ";


    @Before
    public void setUp(){
        getter = new RequestGetter();
    }

    @Test
    public void testGetName(){
        Assert.assertEquals(name, getter.getName());
    }

    @Test
    public void testEval() throws HttpMalformedHeaderException {
        String reqHeader = "GET / HTTP/1.1\\r\\nHost: www.domain.com\\r\\n";
        String reqBody = "<html>content</html>";

        HttpMessage msg = new HttpMessage(new HttpRequestHeader(reqHeader));
        //have to update the value, bc the layout is changed by processing
        reqHeader = msg.getRequestHeader().toString();
        msg.setRequestBody(reqBody);
        SingleMessage actualSingleMessage = getter.eval(msg);

        assertEquals(new SingleMessage(reqHeader, reqBody).getHeader(), actualSingleMessage.getHeader());
        assertEquals(new SingleMessage(reqHeader, reqBody).getBody(), actualSingleMessage.getBody());
    }

}