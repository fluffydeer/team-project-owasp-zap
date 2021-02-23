package org.zaproxy.zap.extension.policyRuleImplementation.ruleImplementations;

import org.zaproxy.zap.extension.policyRuleVerifier.policies.RuleInterface;
import org.parosproxy.paros.network.HttpMessage;

import java.util.regex.Pattern;

/**
 * Rule to verify if the request contains generic email address format
 */

public class GenericEmailAddressRule implements RuleInterface {

    private static final Pattern EMAIL_ADDRESS_PATTERN =
            Pattern.compile("[a-zA-Z0-9-_.]+@[a-zA-Z0-9-_.]+", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean requestSatisfiesRule(HttpMessage msg) {
        return !EMAIL_ADDRESS_PATTERN.matcher(msg.getRequestHeader().
                getHeadersAsString()).find();

    }

    @Override
    public boolean responseSatisfiesRule(HttpMessage msg) {
        return true;
    }

    @Override
    public String getName() {
        return "GenericEmailAddressRule";
    }
}
