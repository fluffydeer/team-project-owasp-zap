package org.zaproxy.zap.extension.policyRuleImplementation.ruleImplementations;

import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.extension.policyRuleVerifier.policies.RuleInterface;

import static org.parosproxy.paros.network.HttpRequestHeader.DELETE;
import static org.parosproxy.paros.network.HttpRequestHeader.PUT;

/**
 * Rule to verify requests which uses HTTP methods DELETE or PUT
 */
public class CheckHTTPMethod implements RuleInterface {

    @Override
    public boolean requestSatisfiesRule(HttpMessage msg) {

        return !(msg.getRequestHeader().getMethod().equals(PUT) ||
                msg.getRequestHeader().getMethod().equals(DELETE));

    }

    @Override
    public boolean responseSatisfiesRule(HttpMessage msg) {
        return true;
    }

    @Override
    public String getName() {
        return "CheckHTTPMethod";
    }
}
