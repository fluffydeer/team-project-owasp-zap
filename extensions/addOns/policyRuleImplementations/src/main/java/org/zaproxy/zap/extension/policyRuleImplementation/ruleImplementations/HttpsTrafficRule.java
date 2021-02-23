package org.zaproxy.zap.extension.policyRuleImplementation.ruleImplementations;

import org.zaproxy.zap.extension.policyRuleVerifier.policies.RuleInterface;
import org.parosproxy.paros.network.HttpMessage;

/**
 * Rule to verify whether a request is secure (Https).
 */
public class HttpsTrafficRule implements RuleInterface {

    @Override
    public boolean requestSatisfiesRule(HttpMessage msg) {
        return msg.getRequestHeader().isSecure();
    }

    @Override
    public boolean responseSatisfiesRule(HttpMessage msg) {
        return true;
    }

    @Override
    public String getName() {
        return "HttpsTrafficRule";
    }
}
