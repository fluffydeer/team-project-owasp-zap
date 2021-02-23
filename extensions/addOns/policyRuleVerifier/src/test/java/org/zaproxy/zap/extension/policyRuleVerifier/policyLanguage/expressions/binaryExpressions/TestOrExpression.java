package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.FalseExpression;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.TrueExpression;

public class TestOrExpression {
    private FalseExpression falseExpression;
    private TrueExpression trueExpression;
    private OrExpression orExpression;

    @Before
    public void init() {
        this.falseExpression = new FalseExpression();
        this.trueExpression = new TrueExpression();
        this.orExpression = new OrExpression();
    }

    @Test
    public void testTrueOrTrue() {
        orExpression.setlExpression(trueExpression);
        orExpression.setrExpression(trueExpression);

        Assert.assertTrue(orExpression.eval(null));
    }

    @Test
    public void testTrueOrFalse() {
        orExpression.setlExpression(trueExpression);
        orExpression.setrExpression(falseExpression);

        Assert.assertTrue(orExpression.eval(null));
    }

    @Test
    public void testFalseOrTrue() {
        orExpression.setlExpression(falseExpression);
        orExpression.setrExpression(trueExpression);

        Assert.assertTrue(orExpression.eval(null));
    }

    @Test
    public void testFalseOrFalse() {
        orExpression.setlExpression(falseExpression);
        orExpression.setrExpression(falseExpression);

        Assert.assertFalse(orExpression.eval(null));
    }

    @Test
    public void testGetName() {
        Assert.assertEquals("OR", orExpression.getName());
    }
}
