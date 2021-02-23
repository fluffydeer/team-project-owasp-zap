package org.zaproxy.zap.extension.typoSquat.rules;

import java.util.HashSet;
import java.util.Set;

public abstract class RulesAbstract {

    public Set<Rules> getRules() {

        Set<Rules> rules = new HashSet<>();
        rules.add(new CheckSimilarity());

        return rules;
    }
}
