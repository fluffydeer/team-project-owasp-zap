package org.zaproxy.zap.extension.policyRuleImplementation.ruleImplementations;

import org.parosproxy.paros.network.HttpMessage;

/**
 * Rule to verify whether a request body contains a keyword.
 */
public class ContainsKeyWordRule extends FileRule {

    public ContainsKeyWordRule() {
        super("/keyWords.txt");
    }

    @Override
    public boolean requestSatisfiesRule(HttpMessage msg) {
        return !isPresentInList(msg.getRequestBody().toString());
    }

    @Override
    public boolean responseSatisfiesRule(HttpMessage msg) {
        return true;
    }

    @Override
    public String getName() { return "ContainsKeyWordRule"; }
}

