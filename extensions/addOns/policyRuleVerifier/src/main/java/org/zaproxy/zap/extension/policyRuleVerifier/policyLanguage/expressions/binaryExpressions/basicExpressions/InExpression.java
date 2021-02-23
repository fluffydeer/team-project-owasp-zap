package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions.basicExpressions;

import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions.BinaryExpression;

import java.util.List;

public class InExpression extends BinaryExpression<String, List<String>, Boolean> {
    private static final String IN_NAME = "IN";

    @Override
    public String getName() {
        return IN_NAME;
    }

    @Override
    public Boolean eval(HttpMessage msg) {
        return rExpression.eval(msg).contains(lExpression.eval(msg));
    }
}
