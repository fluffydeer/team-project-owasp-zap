package org.zaproxy.zap.extension.policyRuleImplementation.ruleImplementations;

import org.parosproxy.paros.network.HttpMessage;

/**
 * Rule to check whether a request is made to a certain domain.
 */
public class VisitsDomainRule extends FileRule {

    public VisitsDomainRule() {
        super("/domains.txt");
    }

    @Override
    public boolean responseSatisfiesRule(HttpMessage msg) {
        return true;
    }

    @Override
    public boolean requestSatisfiesRule(HttpMessage msg) {
        return !isPresentInList(msg.getRequestHeader().getHostName());
    }

    @Override
    public String getName() { return "VisitsDomainRule"; }

}

