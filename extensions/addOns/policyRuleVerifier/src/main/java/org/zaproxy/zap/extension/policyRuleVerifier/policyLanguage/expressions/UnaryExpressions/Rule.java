package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.UnaryExpressions;

import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.extension.policyRuleVerifier.policies.RuleInterface;

public class Rule extends UnaryExpression<Boolean, Boolean> implements RuleInterface {
    public String name;

    public Rule(String ruleName) {
        super();
        this.name = ruleName;
    }

    public void setName(String ruleName) {
        name = ruleName;
    }

    @Override
    public boolean requestSatisfiesRule(HttpMessage msg) {
        return true;
    }

    @Override
    public boolean responseSatisfiesRule(HttpMessage msg) {
        return subExpression.eval(msg);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Boolean eval(HttpMessage msg) {
        return subExpression.eval(msg);
    }
}
