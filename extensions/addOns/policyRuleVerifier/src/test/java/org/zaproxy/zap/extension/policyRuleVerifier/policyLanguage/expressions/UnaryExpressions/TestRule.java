package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.UnaryExpressions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.TrueExpression;

import static org.junit.Assert.*;

public class TestRule {
    private Rule rule;
    private String name;

    @Before
    public void setUp(){
        name = "cool rule";
        rule = new Rule(name);
    }

    @Test
    public void testSetName(){
        name = "another name";
        rule.setName(name);
        Assert.assertEquals(name, rule.getName());
    }

    @Test
    public void testGetName(){
        Assert.assertEquals(name, rule.getName());
    }

    @Test
    public void testEval(){
        rule.setSubExpression(new TrueExpression());
        Assert.assertTrue(rule.eval(null));
    }
}