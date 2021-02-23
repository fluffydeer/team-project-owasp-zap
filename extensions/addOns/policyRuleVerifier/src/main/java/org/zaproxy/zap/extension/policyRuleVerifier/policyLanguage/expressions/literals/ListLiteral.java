package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.literals;

import java.util.List;

public class ListLiteral extends LiteralExpression<List<String>> {

    public ListLiteral(List<String> list) {
        super(list);
    }

    @Override
    public String getName() {
        return null;
    }
}
