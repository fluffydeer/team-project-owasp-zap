package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions;

import org.parosproxy.paros.network.HttpMessage;

public class TrueExpression implements AbstractExpression<Boolean> {


    @Override
    public String getName() {
        return "TRUE";
    }

    @Override
    public Boolean eval(HttpMessage msg) {
        return true;
    }
}
