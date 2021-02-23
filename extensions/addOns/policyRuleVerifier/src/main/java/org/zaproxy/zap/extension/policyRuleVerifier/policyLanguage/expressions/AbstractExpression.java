package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions;

import org.parosproxy.paros.network.HttpMessage;

public interface AbstractExpression<ReturnType> {
    String getName();
    ReturnType eval(HttpMessage msg);
}
