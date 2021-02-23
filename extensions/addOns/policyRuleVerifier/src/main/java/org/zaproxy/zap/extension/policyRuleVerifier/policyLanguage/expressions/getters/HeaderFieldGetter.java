package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.getters;

import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.EvaluationException;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions.BinaryExpression;

public class HeaderFieldGetter extends BinaryExpression<String, String, String> {

    private static final String NAME = "HeaderFieldGetter";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String eval(HttpMessage msg) {
        String header = lExpression.eval(msg);
        for (String line : header.split("\\r?\\n")) {
            String fieldName = rExpression.eval(msg);
            if (line.indexOf(fieldName + ": ") == 0) {
                return line.substring((fieldName + ": ").length()).trim();
            }
        }
        throw new EvaluationException("Field not found: " + rExpression.eval(msg), this);
    }

}
