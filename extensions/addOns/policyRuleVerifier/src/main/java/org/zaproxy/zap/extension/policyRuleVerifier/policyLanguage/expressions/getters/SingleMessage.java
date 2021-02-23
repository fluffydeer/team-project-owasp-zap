package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.getters;

public class SingleMessage {

    private String header;

    private String body;

    public SingleMessage(String header, String body) {
        this.header = header;
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public String getHeader() {
        return header;
    }
}
