
package org.zaproxy.zap.extension.typoSquat.actions;

import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.extension.typoSquat.session.SessionManager;

import java.io.IOException;

public abstract class AbstractAction {
    /**
     * This message executes the needed action for the type of typosquat scan result we get.
     * Depending on these actions, we return true or false to send the original request to
     * the server or to just return it to the client immediately with our own response.
     * @param msg
     * @return
     * @throws IOException
     */
    abstract public boolean execute(HttpMessage msg, SessionManager sessionManager) throws IOException;

    protected boolean setRedirectResponse(HttpMessage msg, String location) {
        msg.getResponseHeader().setStatusCode(307);
        msg.getResponseHeader().setHeader("Location", "//" + location);
        return true;
    }
}
