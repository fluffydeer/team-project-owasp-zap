package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser;

import org.junit.Assert;
import org.junit.Test;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpRequestHeader;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.EvaluationException;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.UnaryExpressions.Rule;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;

public class TestParser {

    private HttpMessage httpMessage;

    private HttpRequestHeader httpRequestHeader;


    @Test
    public void test() {
        String input = "NOT REQ HEAD Host IS google AND (RES BODY IS something OR REQ HEAD Test CONTAINS test)";
        RuleParser parser = new RuleParser();
        Rule rule = parser.parse(input);
    }

    @Test
    public void testHTTPSRule() throws Exception{
        httpRequestHeader = new HttpRequestHeader
                ("GET / HTTP/1.1\n Host: www.sssom@test.com\n");
        httpMessage = new HttpMessage();
        httpMessage.setRequestHeader(httpRequestHeader);

        String input = "REQ HEAD isSecure IS TRUE";

        RuleParser parser = new RuleParser();

        Rule rule = parser.parse(input);
        try {
            rule.eval(httpMessage);
            Assert.fail();
        } catch (EvaluationException e) {}

    }

    @Test
    public void testREGEXRule() throws Exception{
        httpRequestHeader = new HttpRequestHeader
                ("GET / HTTP/1.1\n Host: www.sssom@test.com\n");

        httpMessage = new HttpMessage();
        httpMessage.setRequestHeader(httpRequestHeader);

        String input = "REQ HEAD Host MATCHES [a-zA-Z0-9-_.]+@[a-zA-Z0-9-_.]+\n";

        RuleParser parser = new RuleParser();

        Rule rule = parser.parse(input);
        assertTrue(rule.eval(httpMessage));
    }

    @Test
    public void testRuleReqStatus() throws Exception{
        httpRequestHeader = new HttpRequestHeader
                ("GET / HTTP/1.1\n Host: www.sssom@test.com\n status: 200");

        httpMessage = new HttpMessage();
        httpMessage.setRequestHeader(httpRequestHeader);

        String input = "REQ HEAD status IN [200, 201] ";
        RuleParser parser = new RuleParser();

        Rule rule = parser.parse(input);
        assertTrue(rule.eval(httpMessage));
    }
}
