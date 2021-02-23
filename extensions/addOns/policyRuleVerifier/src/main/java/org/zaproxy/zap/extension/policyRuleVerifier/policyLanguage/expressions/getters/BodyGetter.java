package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.getters;

import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.UnaryExpressions.UnaryExpression;

public class BodyGetter extends UnaryExpression<SingleMessage, String> {
    @Override
    public String getName() {
        return "BODY";
    }

    @Override
    public String eval(HttpMessage msg) {
        return subExpression.eval(msg).getBody();
    }
}
