package org.zaproxy.zap.extension.typoSquat.actions.requestActions;

import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.extension.typoSquat.session.SessionManager;

public class BlackListedAction extends AbstractRequestAction {

    private String redirectDomain;

    public BlackListedAction(String redirectDomain) { this.redirectDomain = redirectDomain; }

    public String getRedirectDomain() { return this.redirectDomain; }

    /**
     * If a domain is blacklisted, we redirect to the corresponding whitelisted domain.
     * We return true, because we are sending our own response back to the user.
     * @param msg
     */
    @Override
    public boolean execute(HttpMessage msg, SessionManager sessionManager) {
        // Redirect the user
        msg.getResponseHeader().setStatusCode(307);
        msg.getResponseHeader().setHeader("Location", "//" + this.redirectDomain);
        return true;
    }

}
