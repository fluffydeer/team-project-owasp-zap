package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.getters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpRequestHeader;
import org.zaproxy.zap.network.HttpRequestBody;

public class TestBodyGetter {
    private BodyGetter getter;
    private static final String name = "BODY";

    @Before
    public void setUp(){
        getter = new BodyGetter();
    }

    @Test
    public void testGetName() {
        Assert.assertEquals(name, getter.getName());
    }

    @Test
    public void testEval() throws HttpMalformedHeaderException {
        String reqHeader = "GET / HTTP/1.1\r\nHost: www.domian1.com\r\n";
        String reqBody = "content";
        RequestGetter reqGetter = new RequestGetter();
        getter.setSubExpression(reqGetter);

        HttpMessage msg = new HttpMessage(new HttpRequestHeader(reqHeader), new HttpRequestBody(reqBody));
        String actualValue = getter.eval(msg);
        Assert.assertEquals(reqBody, actualValue);

        msg.setRequestBody("different" + reqBody);
        actualValue = getter.eval(msg);
        Assert.assertNotEquals(reqBody, actualValue);
    }
}

