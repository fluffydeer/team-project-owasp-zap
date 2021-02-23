package org.zaproxy.zap.extension.policyRuleVerifier.policies;

import org.parosproxy.paros.network.HttpMessage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractPolicy {

    protected Set<RuleInterface> rules = new HashSet<>();

    /**
     * Returns the rules in the policy.
     */
    public final Set<RuleInterface> getRules() {
        return rules;
    }

    /**
     * Method that checks all the response rules.
     * @param msg
     * @return all the names of the rules that were violated.
     */
    public final List<String> checkResponseRules(HttpMessage msg) {
        List<String> violatedRules = new ArrayList<>();
        for (RuleInterface rule: rules){
            if (!rule.responseSatisfiesRule(msg)){
                violatedRules.add(rule.getName());
            }
        }
        return violatedRules;
    }

    /**
     * Method that checks all the request rules.
     * @param msg
     * @return all the names of the rules that were violated.
     */
    public final List<String> checkRequestRules(HttpMessage msg) {
        List<String> violatedRules = new ArrayList<>();
        for (RuleInterface rule: rules){
            if (!rule.requestSatisfiesRule(msg)){
                violatedRules.add(rule.getName());
            }
        }
        return violatedRules;
    }

    /**
     * Initialises the rules in the policy. Adds them to the rules list.
     */
    public abstract void setRules();

    /**
     * Returns the name of the policy.
     */
    public abstract String getName();

}