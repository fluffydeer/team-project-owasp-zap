package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.UnaryExpressions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.FalseExpression;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.TrueExpression;

public class TestNotExpression {
    private NotExpression notExpression;
    private static final String name = "NOT";

    @Before
    public void init() {
        this.notExpression = new NotExpression();
    }

    @Test
    public void testGetName(){
        Assert.assertEquals(name, notExpression.getName());
    }

    @Test
    public void testNotTrue() {
        notExpression.setSubExpression(new TrueExpression());

        Assert.assertFalse(notExpression.eval(null));
    }

    @Test
    public void testNotFalse() {
        notExpression.setSubExpression(new FalseExpression());

        Assert.assertTrue(notExpression.eval(null));
    }
}
