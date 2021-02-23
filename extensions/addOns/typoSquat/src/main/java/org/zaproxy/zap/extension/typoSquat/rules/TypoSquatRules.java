package org.zaproxy.zap.extension.typoSquat.rules;

import org.zaproxy.zap.extension.typoSquat.actions.requestActions.AbstractRequestAction;
import org.zaproxy.zap.extension.typoSquat.actions.requestActions.BlackListedAction;
import org.zaproxy.zap.extension.typoSquat.actions.requestActions.WhiteListedAction;
import org.zaproxy.zap.extension.typoSquat.fileHandler.DomainHandler;

public class TypoSquatRules {

    private DomainHandler domainHandler;
    private ExecuteRules executeRules;

    public TypoSquatRules(DomainHandler domainHandler) {
        this.domainHandler = domainHandler;
        executeRules = new ExecuteRules();
    }

    /**
     * implementation of the rules behind AbstractRequestActions
     *
     * @param hostName The hostname of the request
     * @return AbstractRequestAction    The action to execute
     */
    public synchronized AbstractRequestAction scan(String hostName) {
        if (domainHandler.isWhiteListed(hostName)) {
            return new WhiteListedAction();
        } else if (domainHandler.isBlackListed(hostName)) {
            return new BlackListedAction(domainHandler.getRedirect(hostName));
        } else {
            return executeRules.checkRules(hostName, domainHandler);
        }
    }

}
