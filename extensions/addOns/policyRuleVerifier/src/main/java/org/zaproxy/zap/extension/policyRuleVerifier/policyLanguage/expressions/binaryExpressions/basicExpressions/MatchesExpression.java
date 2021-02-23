package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions.basicExpressions;

import org.parosproxy.paros.network.HttpMessage;

import java.util.regex.Pattern;

public class MatchesExpression extends BasicExpression {
    private static final String MATCHES_NAME = "MATCHES";

    @Override
    public String getName() {
        return MATCHES_NAME;
    }

    @Override
    public Boolean eval(HttpMessage msg) {
        String content = lExpression.eval(msg);
        return Pattern.compile(rExpression.eval(msg)).matcher(content).find();
    }
}
