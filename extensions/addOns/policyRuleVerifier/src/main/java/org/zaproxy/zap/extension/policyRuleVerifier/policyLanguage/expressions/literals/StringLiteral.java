package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.literals;

public class StringLiteral extends LiteralExpression<String> {

    public StringLiteral(String value) {
        super(value);
    }

    @Override
    public String getName() {
        return "String Literal";
    }
}
