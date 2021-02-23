package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser;

import org.zaproxy.zap.extension.policyRuleVerifier.policies.AbstractPolicy;
import org.zaproxy.zap.extension.policyRuleVerifier.policies.RuleInterface;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.UnaryExpressions.Rule;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Policy extends AbstractPolicy {
    private String name;
    private String policyContent;
    private Set<RuleInterface> expRules = new HashSet<>();

    /**
     * Construct a Policy.
     *
     * @param   policyName    The name of the policy.
     */
    public Policy(String policyName, String content) {
        name = policyName;
        policyContent = content;
    }

    /**
     * Sets the rules of the AbstractPolicy class.
     */
    @Override
    public void setRules() {
        rules = expRules;
    }

    /** @return name */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the contents of the policy.
     */
    public String getPolicyContent() {
        return policyContent;
    }

    /**
     * Adds a new rule to the policy.
     *
     * @param   newRule     The new rules to add.
     */
    public void addRule(Rule newRule) {
        expRules.add(newRule);
    }

    /**
     * Adds all rules in the collection to the policy
     * @param rules: The rules to be added.
     */
    public void addAllRules(Collection<Rule> rules) {
        expRules.addAll(rules);
    }

}
