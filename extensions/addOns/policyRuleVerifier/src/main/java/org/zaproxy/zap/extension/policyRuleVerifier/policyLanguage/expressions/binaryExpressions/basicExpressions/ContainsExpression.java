package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions.basicExpressions;

import org.parosproxy.paros.network.HttpMessage;

public class ContainsExpression extends BasicExpression {
    private static final String CONTAINS_NAME = "CONTAINS";

    @Override
    public String getName() {
        return CONTAINS_NAME;
    }

    @Override
    public Boolean eval(HttpMessage msg) {
        String content = lExpression.eval(msg);

        return content.contains(rExpression.eval(msg));
    }
}
