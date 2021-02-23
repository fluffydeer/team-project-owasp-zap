package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions.basicExpressions;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.literals.StringLiteral;

public class TestIsExpression {
    private static final String IS_NAME = "IS";
    private IsExpression isExpression;

    @Before
    public void setUp(){
        isExpression = new IsExpression();
    }

    @Test
    public void testGetName(){
        Assert.assertEquals(IS_NAME, isExpression.getName());
    }

    @Test
    public void testEval(){
        isExpression.setrExpression(new StringLiteral("apple"));
        isExpression.setlExpression(new StringLiteral("apple"));
        Assert.assertTrue(isExpression.eval(null));

        isExpression.setlExpression(new StringLiteral("tomato"));
        Assert.assertFalse(isExpression.eval(null));
    }
}