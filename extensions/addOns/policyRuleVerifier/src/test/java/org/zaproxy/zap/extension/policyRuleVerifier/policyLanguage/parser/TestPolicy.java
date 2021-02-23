package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.FalseExpression;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.TrueExpression;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.UnaryExpressions.Rule;

import java.util.List;
import java.util.Set;

public class TestPolicy {
    private static final String POLICY_TEST_NAME = "policy-test-name";
    private static final String content = "lalalala";
    private Policy policy;

    @Before
    public void init() {
        this.policy = new Policy(POLICY_TEST_NAME, content);
    }

    @Test
    public void testPolicyName() {
        Assert.assertEquals(POLICY_TEST_NAME, policy.getName());
    }

    @Test
    public void testPolicyEval() {
        Rule r1 = new Rule("name-r1");
        Rule r2 = new Rule("name-r2");

        r1.setSubExpression(new TrueExpression());
        r2.setSubExpression(new FalseExpression());

        policy.addRule(r1);
        policy.addRule(r2);
        policy.setRules();

        List<String> violatedRules = policy.checkRequestRules(null);
        violatedRules.addAll(policy.checkResponseRules(null));

        Assert.assertFalse(violatedRules.contains(r1.getName()));
        Assert.assertTrue(violatedRules.contains(r2.getName()));
    }
}
