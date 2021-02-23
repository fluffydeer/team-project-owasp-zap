package org.zaproxy.zap.extension.policyRuleImplementation.ruleImplementations;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.parosproxy.paros.network.HttpMessage;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestContainsKeywordRule {

    private ContainsKeyWordRule rule;

    private HttpMessage httpMessage;

    @Before
    public void setup(){
        rule = new ContainsKeyWordRule();
        httpMessage = new HttpMessage();
    }

    @Test
    public void testContainsKeyword() {
        httpMessage.setRequestBody("This is a string containing words that are in the keywords list.");
        assertFalse(rule.requestSatisfiesRule(httpMessage));
    }

    @Test
    public void testDoesntContainsKeyword() {
        httpMessage.setRequestBody("This is a string containing words that are not in the list.");
        assertTrue(rule.requestSatisfiesRule(httpMessage));
    }

    @Test
    public void testResponse() {
        assertTrue(rule.responseSatisfiesRule(httpMessage));
    }

    @Test
    public void testGetName(){
        assertEquals("ContainsKeyWordRule", rule.getName());
    }
}
