package org.zaproxy.zap.extension.typoSquat;

import org.parosproxy.paros.core.proxy.OverrideMessageProxyListener;
import org.parosproxy.paros.extension.ExtensionAdaptor;
import org.parosproxy.paros.extension.ExtensionHook;
import org.parosproxy.paros.network.HtmlParameter;
import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.extension.typoSquat.actions.requestActions.AbstractRequestAction;
import org.zaproxy.zap.extension.typoSquat.fileHandler.DomainHandler;
import org.zaproxy.zap.extension.typoSquat.rules.TypoSquatRules;
import org.zaproxy.zap.extension.typoSquat.session.SessionManager;
import org.zaproxy.zap.extension.typoSquat.actions.userActions.AbstractUserAction;

import java.io.IOException;

public class ExtensionTypoSquat extends ExtensionAdaptor implements OverrideMessageProxyListener {

    private static final String EXTENSION_NAME = "TypoSquat Extension";

    private TypoSquatRules rules;
    private DomainHandler domainHandler;
    private SessionManager sessionManager;

    public ExtensionTypoSquat() {
        this(System.getProperty("user.dir"));
    }

    public ExtensionTypoSquat(String path) {
        super(EXTENSION_NAME);

        domainHandler = new DomainHandler(path);
        rules = new TypoSquatRules(domainHandler);
        sessionManager = new SessionManager();
    }

    /**
     * @param   msg     The mutable HttpMessage from the client.
     * @return  true    if the message has to bypass the server.
     *          false   if the message has to be passed to the server.
     *          Action classes decide whether to bypass or pass.
     */
    @Override
    public boolean onHttpRequestSend(HttpMessage msg) {
        String domain = msg.getRequestHeader().getHostName();

        if (isUserAction(msg)) {
            return handleUserInput(msg);
        }

        AbstractRequestAction action = rules.scan(domain);

        try {
            return action.execute(msg, sessionManager);
        } catch (IOException e) {
            e.printStackTrace();

            // When an exception occurs, do not let
            //      the request pass to the server!
            msg.setResponseBody("An error occurred.");
            return true;
        }
    }

    private boolean handleUserInput(HttpMessage msg) {
        String uuid = getUUIDFromHttpMessage(msg);

        if (! sessionManager.hasAction(uuid)) {
            msg.setResponseBody("Action is invalid or has expired.");
            return true;
        }

        AbstractUserAction urAction = sessionManager.getAction(uuid);

        return urAction.execute(msg, sessionManager);
    }

    /**
     * We don't do anything with the responses received from the server.
     * We just send it through.
     */
    @Override
    public boolean onHttpResponseReceived(HttpMessage msg) {
        return false;
    }

    private String getUUIDFromHttpMessage(HttpMessage msg) {
        for (HtmlParameter param : msg.getUrlParams()) {
            if (param.getName().equals("ZAPROXY-typosquat-extension-uuid")) {
                return param.getValue();
            }
        }
        return null;
    }

    private boolean isUserAction(HttpMessage msg) {
        return (getUUIDFromHttpMessage(msg) != null);
    }

    @Override
    public void hook(ExtensionHook extensionHook) {
        super.hook(extensionHook);

        extensionHook.addOverrideMessageProxyListener(this);
    }

    @Override
    public String getAuthor() {
        return "DSS - Group 3";
    }

    @Override
    public int getArrangeableListenerOrder() {
        return 0;
    }

    @Override
    public boolean canUnload() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Automatic prevention of TypoSquats";
    }

    protected DomainHandler getDomainHandler() {
        return this.domainHandler;
    }
}