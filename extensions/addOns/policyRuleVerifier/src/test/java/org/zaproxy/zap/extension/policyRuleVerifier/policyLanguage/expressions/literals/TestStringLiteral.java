package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.literals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.parosproxy.paros.network.HttpMessage;


public class TestStringLiteral {
    private StringLiteral literal;
    private static final String testValue = "test";
    private static final String stringLiteralName = "String Literal";

    @Before
    public void setUp(){
        literal = new StringLiteral(testValue);
        Assert.assertNotNull(literal);
    }

    @Test
    public void testGetName() {
        Assert.assertEquals(stringLiteralName, literal.getName());
    }

    @Test
    public void testEval(){
        String actualValue = literal.eval(new HttpMessage());
        Assert.assertEquals(testValue, actualValue);
    }
}