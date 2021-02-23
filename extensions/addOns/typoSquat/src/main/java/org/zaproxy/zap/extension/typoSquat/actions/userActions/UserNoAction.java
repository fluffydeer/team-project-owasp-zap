package org.zaproxy.zap.extension.typoSquat.actions.userActions;

import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.extension.typoSquat.session.SessionManager;

public class UserNoAction extends AbstractUserAction {
    public UserNoAction(String uuid, String originalDomain) {
        super(uuid, originalDomain);
    }

    @Override
    public boolean execute(HttpMessage msg, SessionManager manager) {
        if (! isValid(uuid, originalDomain)) {
            throw new IllegalArgumentException("UUID does not match domain.");
        }

        // Set empty body
        msg.setResponseBody("No action taken.");

        this.invalidateOtherActions(manager);

        return true;
    }
}
