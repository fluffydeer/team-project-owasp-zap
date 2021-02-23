package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions;

import org.parosproxy.paros.network.HttpMessage;

public class FalseExpression implements AbstractExpression<Boolean> {
    @Override
    public String getName() {
        return "FALSE";
    }

    @Override
    public Boolean eval(HttpMessage msg) {
        return false;
    }
}
