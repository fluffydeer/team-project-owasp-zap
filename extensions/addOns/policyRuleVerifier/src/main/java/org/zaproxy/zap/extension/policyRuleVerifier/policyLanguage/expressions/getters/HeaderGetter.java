package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.getters;

import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.AbstractExpression;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.UnaryExpressions.UnaryExpression;

public class HeaderGetter extends UnaryExpression<SingleMessage, String> {

    public HeaderGetter(){}

    public HeaderGetter(AbstractExpression<SingleMessage> subExpression) {
        this.subExpression = subExpression;
    }

    @Override
    public String getName() {
        return "HEAD";
    }

    @Override
    public String eval(HttpMessage msg) {
        return subExpression.eval(msg).getHeader();
    }
}
