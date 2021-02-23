package org.zaproxy.zap.extension.typoSquat.actions.userActions;

import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.extension.typoSquat.actions.AbstractAction;
import org.zaproxy.zap.extension.typoSquat.session.SessionManager;


public abstract class AbstractUserAction extends AbstractAction {
    protected String uuid;
    protected String originalDomain;

    protected AbstractUserAction(String uuid, String originalDomain) {
        this.uuid = uuid;
        this.originalDomain = originalDomain;
    }

    public abstract boolean execute(HttpMessage msg, SessionManager manager);

    protected boolean isValid(String uuid, String domain) {
        return uuid.equals(this.uuid) && domain.equals(this.originalDomain);
    }

    protected void invalidateOtherActions(SessionManager manager) {
        manager.invalidateDomainKeys(originalDomain);
    }

    public String getUUID() {
        return uuid;
    }

    public String getOriginalDomain() {
        return originalDomain;
    }
}
