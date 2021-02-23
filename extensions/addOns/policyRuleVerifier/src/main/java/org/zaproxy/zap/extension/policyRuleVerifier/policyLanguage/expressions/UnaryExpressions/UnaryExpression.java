package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.UnaryExpressions;

import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.AbstractExpression;

public abstract class UnaryExpression<ChildReturnType, ReturnType> implements AbstractExpression<ReturnType> {
    protected AbstractExpression<ChildReturnType> subExpression;

    public UnaryExpression() {

    }

    public void setSubExpression(AbstractExpression<ChildReturnType> subExpression) {
        this.subExpression = subExpression;
    }
}
