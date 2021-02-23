package org.zaproxy.zap.extension.policyRuleImplementation.ruleImplementations;

import org.zaproxy.zap.extension.policyRuleVerifier.policies.RuleInterface;
import org.parosproxy.paros.network.HtmlParameter;
import org.parosproxy.paros.network.HttpMessage;

import java.util.Set;

/**
 * Rule to check whether a response contains the cookie flags 'secure,' 'HttpOnly' and 'samesite=strict'
 */
public class CookieAttributesRule implements RuleInterface {
    @Override
    public boolean requestSatisfiesRule(HttpMessage msg) {
        return true;
    }

    @Override
    public boolean responseSatisfiesRule(HttpMessage msg) {
        for (HtmlParameter c : msg.getResponseHeader().getCookieParams()) {
            Set<String> flags = c.getFlags();

            if (!flags.contains("Secure") ||
                !flags.contains("HttpOnly") ||
                !flags.contains("samesite=strict")) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getName() {
        return "CookieAttributesRule";
    }
}
