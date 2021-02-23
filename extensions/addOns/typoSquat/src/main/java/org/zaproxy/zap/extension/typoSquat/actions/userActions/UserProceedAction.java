package org.zaproxy.zap.extension.typoSquat.actions.userActions;

import org.zaproxy.zap.extension.typoSquat.fileHandler.DomainHandler;
import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.extension.typoSquat.session.SessionManager;

public class UserProceedAction extends AbstractUserAction {
    private DomainHandler domainHandler;

    public UserProceedAction(String uuid, String originalDomain, DomainHandler domainHandler) {
        super(uuid, originalDomain);

        this.domainHandler = domainHandler;
    }

    @Override
    public boolean execute(HttpMessage msg, SessionManager manager) {
        if (! isValid(uuid, originalDomain)) {
            throw new IllegalArgumentException("UUID does not match domain.");
        }

        domainHandler.addToWhiteList(originalDomain);
        this.invalidateOtherActions(manager);

        return this.setRedirectResponse(msg, originalDomain);
    }
}
