package org.zaproxy.zap.extension.policyRuleImplementation.ruleImplementations;

import org.junit.Before;
import org.junit.Test;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpRequestHeader;

import static org.junit.Assert.*;


public class TestVisitsDomainRule {

    private VisitsDomainRule rule;
    private HttpMessage msg;

    @Before
    public void setUp(){
        rule = new VisitsDomainRule();
        msg = new HttpMessage();
    }

    @Test
    public void testGetName(){
        assertEquals("VisitsDomainRule", rule.getName());
    }

    @Test
    public void testUserVisitsDomainFromList() throws HttpMalformedHeaderException {
        msg = new HttpMessage(new HttpRequestHeader("GET / HTTP/1.1\r\nHost: www.domainIsInTheList.com\r\n"));
        assertFalse(rule.requestSatisfiesRule(msg));
    }

    @Test
    public void testUserDoesntVisitDomainFromList() throws HttpMalformedHeaderException {
        msg = new HttpMessage(new HttpRequestHeader("GET / HTTP/1.1\r\nHost: www.domainIsNotInTheList.com\r\n"));
        assertTrue(rule.requestSatisfiesRule(msg));
    }

    @Test
    public void testResponseSatisfiesRule(){
        assertTrue(rule.responseSatisfiesRule(msg));
    }
}