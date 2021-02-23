package org.zaproxy.zap.extension.typoSquat.actions.userActions;

import org.zaproxy.zap.extension.typoSquat.fileHandler.DomainHandler;
import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.extension.typoSquat.session.SessionManager;
import org.zaproxy.zap.extension.typoSquat.warningPage.TypoSquatSuggestion;

public class UserRedirectAction extends AbstractUserAction {
    private DomainHandler domainHandler;
    private String redirectDomain;

    public UserRedirectAction(TypoSquatSuggestion suggestion, String originalDomain, DomainHandler domainHandler) {
        super(suggestion.getUUID(), originalDomain);

        this.redirectDomain = suggestion.getDomain();
        this.domainHandler = domainHandler;
    }

    @Override
    public boolean execute(HttpMessage msg, SessionManager manager) {
        if (! isValid(uuid, originalDomain)) {
            throw new IllegalArgumentException("UUID does not match domain.");
        }

        domainHandler.addToBlackList(originalDomain, redirectDomain);
        this.invalidateOtherActions(manager);

        return this.setRedirectResponse(msg, redirectDomain);
    }
}
