package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.getters;

import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.AbstractExpression;

public class RequestGetter implements AbstractExpression<SingleMessage> {


    @Override
    public String getName() {
        return "REQ";
    }

    @Override
    public SingleMessage eval(HttpMessage msg) {
        return new SingleMessage(msg.getRequestHeader().toString(), msg.getRequestBody().toString());
    }
}
