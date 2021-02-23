package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions;

public class EvaluationException extends RuntimeException {

    public EvaluationException(String message, AbstractExpression expression) {
        super("Evaluation exception in " + expression.getName() + ":\n" + message);
    }
}
