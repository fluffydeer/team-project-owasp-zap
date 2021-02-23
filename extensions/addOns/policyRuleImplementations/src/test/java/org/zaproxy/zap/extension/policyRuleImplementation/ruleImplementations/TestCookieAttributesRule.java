package org.zaproxy.zap.extension.policyRuleImplementation.ruleImplementations;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.parosproxy.paros.network.HttpHeader;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpRequestHeader;

import static org.mockito.MockitoAnnotations.initMocks;

public class TestCookieAttributesRule {

    @InjectMocks
    private CookieAttributesRule rule;

    private HttpMessage msg;
    private String cookie_safe;

    @Before
    public void setup() throws HttpMalformedHeaderException {
        initMocks(this);
        msg = new HttpMessage(new HttpRequestHeader("GET / HTTP/1.1\r\nHost: www.domian1.com\r\n"));
        cookie_safe = "Set-Cookie: name=lol; Domain=droop.be; Secure; HttpOnly; samesite=strict";
    }

    @Test
    public void testGetName(){
        Assert.assertEquals("CookieAttributesRule",rule.getName());
    }

    @Test
    public void testResponse_OK() {
        msg.getResponseHeader().setHeader(HttpHeader.SET_COOKIE, cookie_safe);
        Assert.assertTrue(rule.responseSatisfiesRule(msg));
    }

    @Test
    public void testResponse_NOK() {
        msg.getResponseHeader().setHeader(HttpHeader.SET_COOKIE, "session=lol");
        Assert.assertFalse(rule.responseSatisfiesRule(msg));
    }
}
