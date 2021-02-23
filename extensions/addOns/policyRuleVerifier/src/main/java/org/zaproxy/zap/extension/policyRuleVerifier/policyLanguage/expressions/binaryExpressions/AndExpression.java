package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions;

import org.parosproxy.paros.network.HttpMessage;

public class AndExpression extends BinaryExpression<Boolean, Boolean, Boolean> {
    private static final String AND_NAME = "AND";

    public AndExpression() {
        super();
    }

    @Override
    public String getName() {
        return AND_NAME;
    }

    @Override
    public Boolean eval(HttpMessage msg) {
        return (lExpression.eval(msg) && rExpression.eval(msg));
    }
}
