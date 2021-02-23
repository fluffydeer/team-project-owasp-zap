package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.getters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpRequestHeader;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.EvaluationException;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.literals.StringLiteral;
import org.zaproxy.zap.network.HttpRequestBody;

import static org.mockito.Mockito.when;


public class TestHeaderFieldGetter {
    private HeaderFieldGetter headerFieldGetter;
    private static final String name = "HeaderFieldGetter";

    @Before
    public void setUp(){
        headerFieldGetter = new HeaderFieldGetter();
    }

    @Test
    public void testGetName() {
        Assert.assertEquals(name, headerFieldGetter.getName());
    }

    @Test
    public void testEval() throws HttpMalformedHeaderException {
        String reqHeader = "GET / HTTP/1.1\r\nHost: www.domain.com\r\n";
        HttpMessage msg = new HttpMessage(new HttpRequestHeader(reqHeader));

        StringLiteral stringLiteral = new StringLiteral("Host");    //with field name specified
        HeaderGetter headerGetter = new HeaderGetter();
        RequestGetter requestGetter = new RequestGetter();

        headerGetter.setSubExpression(requestGetter);
        headerFieldGetter.setlExpression(headerGetter);
        headerFieldGetter.setrExpression(stringLiteral);

        String actualValue = headerFieldGetter.eval(msg);
        Assert.assertEquals("www.domain.com", actualValue);
    }

    @Test(expected = EvaluationException.class)
    public void testThrowEvaluationException() throws HttpMalformedHeaderException {
        String reqHeader = "GET / HTTP/1.1";    //request without any fields
        HttpMessage msg = new HttpMessage(new HttpRequestHeader(reqHeader));

        StringLiteral stringLiteralMock = new StringLiteral("value");
        HeaderGetter headerGetter = new HeaderGetter();
        RequestGetter requestGetter = new RequestGetter();

        headerGetter.setSubExpression(requestGetter);
        headerFieldGetter.setlExpression(headerGetter);
        headerFieldGetter.setrExpression(stringLiteralMock);

        when(headerFieldGetter.eval(msg)).thenThrow(new EvaluationException("Field not found", headerFieldGetter));
        headerFieldGetter.eval(msg);
    }


}