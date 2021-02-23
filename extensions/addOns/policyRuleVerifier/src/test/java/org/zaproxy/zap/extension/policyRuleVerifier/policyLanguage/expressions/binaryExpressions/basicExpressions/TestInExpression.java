package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions.basicExpressions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.literals.ListLiteral;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.literals.StringLiteral;

import java.util.ArrayList;
import java.util.List;


public class TestInExpression {
    private static final String IN_NAME = "IN";
    private InExpression inExpression;

    @Before
    public void setUp(){
        inExpression = new InExpression();
    }

    @Test
    public void testGetName(){
        Assert.assertEquals(IN_NAME, inExpression.getName());
    }

    @Test
    public void testEval(){
        List<String> list = new ArrayList<>();
        list.add("pear");
        list.add("apple");
        list.add("banana");
        ListLiteral listLiteral = new ListLiteral(list);

        inExpression.setrExpression(listLiteral);
        inExpression.setlExpression(new StringLiteral("apple"));
        Assert.assertTrue(inExpression.eval(null));

        inExpression.setlExpression(new StringLiteral("tomato"));
        Assert.assertFalse(inExpression.eval(null));
    }
}