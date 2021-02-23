package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestPolicyParser {
    private PolicyParser policyParser;

    @Before
    public void setup() {
        policyParser = new PolicyParser();
    }

    @Test
    public void testEmptyPolicyFile() {
        try {
            policyParser.parse("");
        } catch (ParseException e) {

        }
    }

    @Test
    public void testParsePolicyName() {
        try {
            policyParser.parse("R:rulename:RES BODY IS Hello;");
            Assert.fail("No policy specified, should fail");
        } catch (ParseException e) {}

        try {
            String policyName = "policyName";
            Policy parsedPolicy = policyParser.parse("P:" + policyName + ";R:r1:RES BODY IS Hello;");

            Assert.assertEquals(policyName, parsedPolicy.getName());
        } catch (ParseException e) {
            e.printStackTrace();
            Assert.fail("Should succeed");
        }
    }

    @Test
    public void testIllegalRuleFormat() {
        try {
            policyParser.parse("P:p;R:rulename RES BODY IS Hello;");
            Assert.fail("The rule must not parsed without distinction between name and body, should fail");
        } catch (ParseException e) {}

        try {
            policyParser.parse("P:p;R:RES BODY IS Hello;");
            Assert.fail("The rule must not be parsed without name, should fail");
        } catch (ParseException e) {}
    }

    @Test
    public void testNonRuleIncluded() {
        try {
            policyParser.parse("P:p;I love breakfast!");
            Assert.fail("The policy file must only contain valid statements, should fail");
        } catch (ParseException e) {}
    }
}
