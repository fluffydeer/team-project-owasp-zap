package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser;

import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.UnaryExpressions.NotExpression;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions.AndExpression;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions.OrExpression;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions.basicExpressions.ContainsExpression;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions.basicExpressions.InExpression;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions.basicExpressions.IsExpression;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions.basicExpressions.MatchesExpression;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.getters.*;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.literals.ListLiteral;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.literals.StringLiteral;

import java.util.List;

public class ExpressionFactory {

    public StringLiteral createStringLiteral(String string) {
        return new StringLiteral(string);
    }

    public ListLiteral createListLiteral(List list) {
        return new ListLiteral(list);
    }

    public RequestGetter createRequestGetter() {
        return new RequestGetter();
    }

    public ResponseGetter createResponseGetter() {
        return new ResponseGetter();
    }

    public HeaderGetter createHeaderGetter() {
        return new HeaderGetter();
    }

    public BodyGetter createBodyGetter() {
        return new BodyGetter();
    }

    public HeaderFieldGetter createHeaderFieldGetter() {
        return new HeaderFieldGetter();
    }

    public MatchesExpression createMatchesExpression() {
        return new MatchesExpression();
    }

    public InExpression createInExpression() {
        return new InExpression();
    }

    public IsExpression createIsExpression() {
        return new IsExpression();
    }

    public ContainsExpression createContainsExpression() {
        return new ContainsExpression();
    }

    public NotExpression createNotExpression() {
        return new NotExpression();
    }

    public AndExpression createAndExpression() {
        return new AndExpression();
    }

    public OrExpression createOrExpression() {
        return new OrExpression();
    }
}
