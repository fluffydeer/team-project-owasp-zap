package org.zaproxy.zap.extension.typoSquat.actions.requestActions;

import org.zaproxy.zap.extension.typoSquat.fileHandler.DomainHandler;
import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.extension.typoSquat.session.SessionManager;

public class NoTypoSquatAction extends AbstractRequestAction {
    private String domainToBeAdded;
    private DomainHandler domainHandler;

    public NoTypoSquatAction(String domainTobeAdded, DomainHandler domainHandler) {
        this.domainToBeAdded = domainTobeAdded;
        this.domainHandler = domainHandler;
    }

    /**
     * When there is no typosquat detected, the domain is added to the whitelist
     * and the response message is just sent through, so we return false as the request is sent to the original server.
     *
     * @param msg
     */
    @Override
    public boolean execute(HttpMessage msg, SessionManager sessionManager) {
        this.domainHandler.addToWhiteList(this.domainToBeAdded);
        return false;
    }

    public String getDomainToBeAdded() {
        return domainToBeAdded;
    }

    public DomainHandler getDomainHandler() {
        return domainHandler;
    }
}
