package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.getters;

import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.AbstractExpression;

public class ResponseGetter implements AbstractExpression<SingleMessage> {


    @Override
    public String getName() {
        return "RES";
    }

    @Override
    public SingleMessage eval(HttpMessage msg) {
        return new SingleMessage(msg.getResponseHeader().toString(), msg.getResponseBody().toString());
    }
}
