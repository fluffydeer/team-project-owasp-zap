package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions.basicExpressions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpRequestHeader;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.getters.HeaderFieldGetter;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.getters.HeaderGetter;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.getters.RequestGetter;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.literals.StringLiteral;

import static org.junit.Assert.*;

public class TestContainsExpression {
    private static final String CONTAINS_NAME = "CONTAINS";
    private ContainsExpression containsExpression;

    @Before
    public void setUp(){
        containsExpression = new ContainsExpression();
    }

    @Test
    public void testGetName(){
        Assert.assertEquals(CONTAINS_NAME, containsExpression.getName());
    }

    @Test
    public void testEval() throws HttpMalformedHeaderException {
        //searches for "bread" in the host
        HttpMessage msg = new HttpMessage(new HttpRequestHeader("GET / HTTP/1.1\n Host: www.bread.com\n"));

        HeaderGetter headerGetter = new HeaderGetter();
        headerGetter.setSubExpression(new RequestGetter());

        HeaderFieldGetter headerFieldGetter = new HeaderFieldGetter();
        headerFieldGetter.setlExpression(headerGetter);
        headerFieldGetter.setrExpression(new StringLiteral("Host"));

        containsExpression.setlExpression(headerFieldGetter);
        containsExpression.setrExpression(new StringLiteral("bread"));
        assertTrue(containsExpression.eval(msg));

        containsExpression.setrExpression(new StringLiteral("cake"));
        assertFalse(containsExpression.eval(msg));
    }
}