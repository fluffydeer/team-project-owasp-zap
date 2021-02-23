package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser.nodes;

import java.util.ArrayList;
import java.util.List;

public class Node extends ArrayList<AbstractNode> implements AbstractNode {

    public Node() {
        super();
    }

    public Node(List<AbstractNode> list) {
        super();
        addAll(list);
    }

    public Node(ArrayList<String> tokens) {
        super();
        for (String token : tokens) {
            add(new Leaf(token));
        }
    }

    @Override
    public int totalSize() {
        int total = 0;
        for (AbstractNode node : this) {
            total += node.totalSize();
        }
        return total;
    }

    @Override
    public Node subList(int start, int end) {
        return new Node(super.subList(start, end));
    }

    @Override
    public String toString() {
        String result = "[";
        for (AbstractNode node : this) {
            result += node.toString();
            result += ",";
        }
        result = result.substring(0, result.length()-1);
        result += "]";
        return result;
    }

}
