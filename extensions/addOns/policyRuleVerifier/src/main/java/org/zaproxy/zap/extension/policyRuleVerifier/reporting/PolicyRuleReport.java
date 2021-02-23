package org.zaproxy.zap.extension.policyRuleVerifier.reporting;

import java.util.ArrayList;
import java.util.List;

public class PolicyRuleReport {

    private List<String> reportedRules = new ArrayList<>();

    public PolicyRuleReport() {

    }

    /**
     * Adds a violated rule to the reported rules list.
     * @param policy: The policy name associated with the violated rule.
     * @param rule: The violated rule name.
     * @param host: The host at which the rule was violated.
     */
    public void addReportedRule(String policy, String rule, String host){
        reportedRules.add(getDescription(policy, rule, host));
    }

    /**
     * Creates a description of the reported rule.
     * @param policy: The policy name associated with the violated rule.
     * @param rule: The violated rule name.
     * @param host: The host at which the rule was violated.
     */
    private String getDescription(String policy, String rule, String host) {
        return "On host: " + host + " - Policy_" + policy + ".rule_" + rule + " violated";
    }

    /**
     * Returns the list of reported rules.
     */
    public List<String> getReportedRules() {
        return this.reportedRules;
    }
}
