package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser.nodes.AbstractNode;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser.nodes.Leaf;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser.nodes.Node;

import java.util.ArrayList;
import java.util.Arrays;

public class TestParenthesisParser {
    private ParenthesisParser parenthesisParser;
    private Tokenizer tokenizer;

    private Node tokenizeToNode(String input) {
        return new Node(tokenizer.tokenize(input));
    }

    @Before
    public void init() {
        this.parenthesisParser = new ParenthesisParser();
        this.tokenizer = new Tokenizer(new ArrayList<>(Arrays.asList("(", ")")));
    }

    @Test
    public void testParseEmptyNode() {
        Node parsedNode = parenthesisParser.parseParenthesisTree(tokenizeToNode(""));

        Assert.assertEquals(parsedNode, new Node());
    }

    @Test
    public void testParseWithoutParenthesis() {
        Node tokenNode = tokenizeToNode("A AND B OR NOT C");
        Node parsedNode = parenthesisParser.parseParenthesisTree(tokenNode);

        Assert.assertEquals(tokenNode, parsedNode);
    }

    @Test
    public void testParseWithSingleParenthesis() {
        Node tokenNode = tokenizeToNode("A AND (B OR NOT C)");
        Node parsedNode = parenthesisParser.parseParenthesisTree(tokenNode);

        Node innerNode = tokenizeToNode("B OR NOT C");
        Node expectedNode = new Node(new ArrayList<>(Arrays.asList(new Leaf("A"), new Leaf("AND"), innerNode)));

        Assert.assertEquals(expectedNode, parsedNode);
    }

    @Test
    public void testParseWithMultipleUnnestedParenthesis() {
        Node tokenNode = tokenizeToNode("(A OR B) AND NOT (C OR NOT D)");
        Node parsedNode = parenthesisParser.parseParenthesisTree(tokenNode);

        Node innerNode1 = tokenizeToNode("A OR B");
        Node innerNode2 = tokenizeToNode("C OR NOT D");
        Node expectedNode = new Node(new ArrayList<>(Arrays.asList(innerNode1, new Leaf("AND"), new Leaf("NOT"), innerNode2)));

        Assert.assertEquals(expectedNode, parsedNode);
    }

    @Test
    public void testParseWithNestedParenthesis() {
        Node tokenNode = tokenizeToNode("((A OR NOT B) AND (C OR D)) AND NOT (A AND B AND (C OR (NOT G AND D)))");
        Node parsedNode = parenthesisParser.parseParenthesisTree(tokenNode);

        Node innerNode1 = tokenizeToNode("A OR NOT B");
        Node innerNode2 = tokenizeToNode("C OR D");
        Node innerNode3 = tokenizeToNode("NOT G AND D");

        Node firstPart = new Node(new ArrayList<>(Arrays.asList(innerNode1, new Leaf("AND"), innerNode2)));
        Node secondPart = new Node(new ArrayList<>(Arrays.asList(
            new Leaf("A"),
            new Leaf("AND"),
            new Leaf("B"),
            new Leaf("AND"),
            new Node(new ArrayList<>(Arrays.asList(
                new Leaf("C"),
                new Leaf("OR"),
                innerNode3
            )))
        )));

        Node expectedNode = new Node(new ArrayList<>(Arrays.asList(firstPart, new Leaf("AND"), new Leaf("NOT"), secondPart)));

        Assert.assertEquals(expectedNode, parsedNode);
    }

    @Test
    public void testParseWithUnmatchingClosingBrackets() {
        Node tokenNode = tokenizeToNode("((A OR B)");

        try {
            Node parsedNode = parenthesisParser.parseParenthesisTree(tokenNode);
            Assert.fail("Parenthesis don't match, this should fail.");
        } catch(ParseException e) {}
    }

    @Test
    public void testParseWithUnmatchingOpeningBrackets() {
        Node tokenNode = tokenizeToNode("(A OR B))");

        try {
            Node parsedNode = parenthesisParser.parseParenthesisTree(tokenNode);
            Assert.fail("Parenthesis don't match, this should fail.");
        } catch(ParseException e) {}
    }
}
