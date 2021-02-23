package org.zaproxy.zap.extension.typoSquat.rules;

import org.zaproxy.zap.extension.typoSquat.actions.requestActions.AbstractRequestAction;
import org.zaproxy.zap.extension.typoSquat.actions.requestActions.NoTypoSquatAction;
import org.zaproxy.zap.extension.typoSquat.actions.requestActions.TypoSquatAction;
import org.zaproxy.zap.extension.typoSquat.fileHandler.DomainHandler;

import java.util.LinkedHashSet;

class ExecuteRules extends RulesAbstract {

    AbstractRequestAction checkRules(String hostName, DomainHandler domainHandler) {
        boolean isTypoSquat;
        LinkedHashSet<String> matchedDomains = new LinkedHashSet<>();
        for (String whiteDomain : domainHandler.getWhiteList()) {
            for (Rules rule : getRules()) {
                isTypoSquat = rule.isSimilar(hostName, whiteDomain);
                if (isTypoSquat) {
                    matchedDomains.add(whiteDomain);
                    return new TypoSquatAction(matchedDomains, domainHandler);
                }
            }
        }
        return new NoTypoSquatAction(hostName, domainHandler);
    }

}
