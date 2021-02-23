package org.zaproxy.zap.extension.policyRuleVerifier.policies;

import org.parosproxy.paros.network.HttpMessage;

public interface RuleInterface {

    /**
     * Checks the request rule on this HttpMessage.
     * @param msg: The HttpMessage to check.
     * @return false if the rule is violated
     */
    boolean requestSatisfiesRule(HttpMessage msg);

    /**
     * Checks the response rule on this HttpMessage.
     * @param msg: The HttpMessage to check.
     * @return false if the rule is violated
     */
    boolean responseSatisfiesRule(HttpMessage msg);

    /**
     * Returns the name of the rule.
     */
    String getName();
}