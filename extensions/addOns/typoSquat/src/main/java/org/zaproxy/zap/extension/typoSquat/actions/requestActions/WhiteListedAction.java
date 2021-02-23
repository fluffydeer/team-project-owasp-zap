package org.zaproxy.zap.extension.typoSquat.actions.requestActions;

import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.extension.typoSquat.session.SessionManager;

public class WhiteListedAction extends AbstractRequestAction {

    public WhiteListedAction() {}

    /**
     * If a page is whitelisted, we show the original response and we don't have to do anything.
     * We return false, because the message can be sent to the original server.
     * @param msg
     */
    @Override
    public boolean execute(HttpMessage msg, SessionManager sessionManager) {
        return false;
    }
}
