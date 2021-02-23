package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions;

import org.parosproxy.paros.network.HttpMessage;

public class OrExpression extends BinaryExpression<Boolean, Boolean, Boolean> {
    private static final String OR_NAME = "OR";

    public OrExpression() {
        super();
    }

    @Override
    public String getName() {
        return OR_NAME;
    }

    @Override
    public Boolean eval(HttpMessage msg) {
        return (lExpression.eval(msg) || rExpression.eval(msg));
    }
}
