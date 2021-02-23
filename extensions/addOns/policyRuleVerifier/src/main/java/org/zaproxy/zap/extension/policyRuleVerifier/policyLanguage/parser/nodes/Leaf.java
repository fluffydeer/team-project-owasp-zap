package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser.nodes;

public class Leaf implements AbstractNode {

    private String token;

    public Leaf(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return token;
    }

    @Override
    public boolean equals(Object other) {
        if (! (other instanceof Leaf)) {
            return false;
        }
        return token.equals(((Leaf) other).token);
    }

    @Override
    public int totalSize() {
        return 1;
    }
}
