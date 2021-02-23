package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.FalseExpression;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.TrueExpression;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions.AndExpression;

public class TestAndExpression {
    private TrueExpression trueExpression;
    private FalseExpression falseExpression;
    private AndExpression andExpression;

    @Before
    public void init() {
        this.trueExpression = new TrueExpression();
        this.falseExpression = new FalseExpression();
        this.andExpression = new AndExpression();
    }

    @Test
    public void testTrueAndTrue() {
        andExpression.setlExpression(trueExpression);
        andExpression.setrExpression(trueExpression);

        Assert.assertTrue(andExpression.eval(null));
    }

    @Test
    public void testTrueAndFalse() {
        andExpression.setlExpression(trueExpression);
        andExpression.setrExpression(falseExpression);

        Assert.assertFalse(andExpression.eval(null));
    }

    @Test
    public void testFalseAndTrue() {
        andExpression.setlExpression(falseExpression);
        andExpression.setrExpression(trueExpression);

        Assert.assertFalse(andExpression.eval(null));
    }

    @Test
    public void testFalseAndFalse() {
        andExpression.setlExpression(falseExpression);
        andExpression.setrExpression(falseExpression);

        Assert.assertFalse(andExpression.eval(null));
    }

    @Test
    public void testGetName() {
        Assert.assertEquals("AND", andExpression.getName());
    }
}
