package org.zaproxy.zap.extension.policyRuleVerifier.reporting;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestPolicyRuleReport {

    private PolicyRuleReport report;

    @Before
    public void setUp() {
        this.report = new PolicyRuleReport();
    }

    @Test
    public void testRaiseRule() {
        report.addReportedRule("policy1", "rule1", "host1");
        Assert.assertTrue(report.getReportedRules().get(0).contains("policy1"));
        Assert.assertTrue(report.getReportedRules().get(0).contains("rule1"));
        Assert.assertTrue(report.getReportedRules().get(0).contains("host1"));
    }
}
