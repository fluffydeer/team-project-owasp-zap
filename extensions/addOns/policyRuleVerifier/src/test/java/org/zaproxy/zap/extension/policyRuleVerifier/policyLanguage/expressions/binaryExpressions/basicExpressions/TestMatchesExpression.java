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


public class TestMatchesExpression {
    private static final String MATCHES_NAME = "MATCHES";
    private MatchesExpression matchesExpression;

    @Before
    public void setUp(){
        matchesExpression = new MatchesExpression();
    }

    @Test
    public void testGetName(){
        Assert.assertEquals(MATCHES_NAME, matchesExpression.getName());
    }

    @Test
    public void testEval() throws HttpMalformedHeaderException {
        HttpMessage msg = new HttpMessage(new HttpRequestHeader("GET / HTTP/1.1\n Host: www.sssom@test.com\n"));

        HeaderGetter headerGetter = new HeaderGetter();
        headerGetter.setSubExpression(new RequestGetter());

        HeaderFieldGetter headerFieldGetter = new HeaderFieldGetter();
        headerFieldGetter.setlExpression(headerGetter);
        headerFieldGetter.setrExpression(new StringLiteral("Host"));

        matchesExpression.setlExpression(headerFieldGetter);
        matchesExpression.setrExpression(new StringLiteral("[a-zA-Z0-9-_.]+@[a-zA-Z0-9-_.]+"));
        Assert.assertTrue(matchesExpression.eval(msg));

        msg.setRequestHeader(new HttpRequestHeader("GET / HTTP/1.1\n Host: www.idontmatch.com\n"));
        Assert.assertFalse(matchesExpression.eval(msg));
    }
}