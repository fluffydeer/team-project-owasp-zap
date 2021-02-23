package org.zaproxy.zap.extension.policyRuleImplementation.ruleImplementations;

import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.extension.policyRuleVerifier.policies.RuleInterface;

/**
 * Rule to flag responses that contain more than numberOfCharacters value
 */
public class CheckResponseCharacterNumber implements RuleInterface {

    private static final int numberOfCharacters = 2000;

    @Override
    public boolean requestSatisfiesRule(HttpMessage msg) {
        return true;

    }

    @Override
    public boolean responseSatisfiesRule(HttpMessage msg) {
        return !(msg.getResponseBody().toString().length() > numberOfCharacters);
    }

    @Override
    public String getName() {
        return "CheckResponseCharacterNumber";
    }
}
