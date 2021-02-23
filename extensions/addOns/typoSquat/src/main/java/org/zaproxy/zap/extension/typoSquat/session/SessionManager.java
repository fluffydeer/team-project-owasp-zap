package org.zaproxy.zap.extension.typoSquat.session;

import org.zaproxy.zap.extension.typoSquat.actions.userActions.AbstractUserAction;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SessionManager {
    private static final Map<String, List<String>> domainMap = new HashMap<>();
    private static final Map<String, AbstractUserAction> actionMap = new HashMap<>();

    public SessionManager() {

    }

    public void addAction(AbstractUserAction action) {
        if (! domainMap.containsKey(action.getOriginalDomain())) {
            domainMap.put(action.getOriginalDomain(), new LinkedList<>());
        }

        domainMap.get(action.getOriginalDomain()).add(action.getUUID());
        actionMap.put(action.getUUID(), action);
    }

    public boolean hasAction(String uuid) {
        return actionMap.containsKey(uuid);
    }

    public AbstractUserAction getAction(String uuid) throws IllegalArgumentException {
        if (! actionMap.containsKey(uuid)) {
            throw new IllegalArgumentException("This uuid is not valid.");
        }

        return actionMap.get(uuid);
    }

    public void invalidateDomainKeys(String domain) {
        if (! domainMap.containsKey(domain)) {
            return;
        }

        if (domainMap.get(domain).isEmpty()) {
            domainMap.remove(domain);
            return;
        }

        for (String uuid : domainMap.get(domain)) {
            actionMap.remove(uuid);
        }

        domainMap.remove(domain);
    }
}
