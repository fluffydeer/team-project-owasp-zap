package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.literals;

import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.AbstractExpression;

public abstract class LiteralExpression<T> implements AbstractExpression<T> {

    private T value;

    public LiteralExpression(T value) {
        this.value = value;
    }

    @Override
    public T eval(HttpMessage msg) {
        return value;
    }
}
