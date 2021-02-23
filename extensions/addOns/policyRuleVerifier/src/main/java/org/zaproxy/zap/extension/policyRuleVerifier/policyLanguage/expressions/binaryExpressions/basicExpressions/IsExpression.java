package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions.basicExpressions;

import org.parosproxy.paros.network.HttpMessage;

public class IsExpression extends BasicExpression {
    private static final String IS_NAME = "IS";

    @Override
    public String getName() {
        return IS_NAME;
    }

    @Override
    public Boolean eval(HttpMessage msg) {
        String content = lExpression.eval(msg);

        return content.equals(rExpression.eval(msg));
    }
}
