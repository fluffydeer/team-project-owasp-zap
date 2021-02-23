package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions;

import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.AbstractExpression;

public abstract class BinaryExpression<LeftReturnType, RightReturnType, R> implements AbstractExpression<R> {
    protected AbstractExpression<LeftReturnType> lExpression;
    protected AbstractExpression<RightReturnType> rExpression;

    public BinaryExpression() {
    }

    public void setlExpression(AbstractExpression<LeftReturnType> lExpression) {
        this.lExpression = lExpression;
    }

    public void setrExpression(AbstractExpression<RightReturnType> rExpression) {
        this.rExpression = rExpression;
    }
}
