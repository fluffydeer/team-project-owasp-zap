package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.getters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestSingleMessage {
    private SingleMessage msg;
    private final static String header = "h";
    private final static String body = "b";

    @Before
    public void setUp(){
        msg = new SingleMessage(header, body);
    }

    @Test
    public void testGetHeader(){
        Assert.assertEquals(header, msg.getHeader());
    }

    @Test
    public void testGetBody(){
        Assert.assertEquals(body, msg.getBody());
    }
}