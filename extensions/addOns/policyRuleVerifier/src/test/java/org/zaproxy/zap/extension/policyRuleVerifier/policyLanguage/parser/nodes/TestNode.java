package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser.nodes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestNode {

    private Node node;

    @Before
    public void SetUp() {
        AbstractNode leaf1 = new Leaf("MATCHES");
        AbstractNode leaf2 = new Leaf("IS");
        AbstractNode leaf3 = new Leaf("CONTAINS");
        List<AbstractNode> nodes = new ArrayList<>();
        List<AbstractNode> nodes2 = new ArrayList<>();
        nodes.add(leaf1);
        nodes.add(leaf2);
        Node node2 = new Node(nodes);
        nodes2.add(leaf3);
        nodes2.add(node2);
        node = new Node(nodes2);
    }

    @Test
    public void testTotalSize() {
        Assert.assertEquals(3, node.totalSize());
    }

    @Test
    public void testSublist() {
        Node test = node.subList(0, 1);
        Assert.assertEquals("CONTAINS", test.get(0).toString());
    }

    @Test
    public void testNodeWithTokens() {
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList("CONTAINS", "MATCHES", "IS", "BODY"));
        Node tokenNode = new Node(tokens);
        Assert.assertEquals(4, tokenNode.totalSize());
    }

    @Test
    public void testToString() {
        Assert. assertEquals("[CONTAINS,[MATCHES,IS]]", node.toString());
    }

}
