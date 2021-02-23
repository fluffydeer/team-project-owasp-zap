package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.UnaryExpressions;

import org.parosproxy.paros.network.HttpMessage;

public class NotExpression extends UnaryExpression<Boolean, Boolean> {
    private static final String NOT_NAME = "NOT";

    public NotExpression() {
        super();
    }

    @Override
    public String getName() {
        return NOT_NAME;
    }

    @Override
    public Boolean eval(HttpMessage msg) {
        return (! subExpression.eval(msg));
    }
}
