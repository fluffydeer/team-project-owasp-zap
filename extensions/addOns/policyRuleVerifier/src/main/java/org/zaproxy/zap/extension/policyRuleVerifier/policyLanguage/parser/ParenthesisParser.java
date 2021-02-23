package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser;

import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser.nodes.AbstractNode;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser.nodes.Leaf;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser.nodes.Node;

import java.util.ArrayList;
import java.util.Arrays;

public class ParenthesisParser {

    private Tokenizer tokenizer;

    public ParenthesisParser() {
        tokenizer = new Tokenizer(new ArrayList<>(Arrays.asList("(", ")", "[", "]")));
    }

    /**
     * Parses the given string to a hierarchy as indicated by the parenthesis in the string.
     * @param string: The string to be parsed.
     */
    public Node parseString(String string) {
        Node tokens = new Node(tokenizer.tokenize(string));
        return parseParenthesisTree(tokens);
    }

    /**
     * The input node with a flat structure is turned in to a tree as indicated by the parenthesis.
     * The output doesn't contain any parenthesis anymore.
     * Lists (indicated by [...] are turned in to single tokens.
     * @param tokens: The flat node to be turned in to a tree.
     * @throws ParseException if the parenthesis are not correct.
     */
    public Node parseParenthesisTree(Node tokens) {
        Node parsed = new Node();
        int index = 0;
        while (index < tokens.size()) {
            AbstractNode token = tokens.get(index);
            if (token.toString().equals("(")) {
                index += 1+ createSubNode(tokens.subList(index+1, tokens.size()), parsed, "(", ")");
            } else if (token.toString().contains("[")) {
                index += 1+ createSubNode(tokens.subList(index+1, tokens.size()), parsed, "[", "]");
                parsed.set(parsed.size()-1, new Leaf(parsed.get(parsed.size()-1).toString()));
            } else if (token.toString().equals(")")) {
                throw new ParseException("Parenthesis don't match");
            } else {
                parsed.add(token);
            }
            index++;
        }
        return parsed;
    }

    /**
     * This method adds a new layer to the parsed tree.
     * @param tokens: The tokens left to be parsed.
     * @param parsed: The tree parsed up until now.
     * @param open: The string indicating an opening parenthesis/bracket.
     * @param close: The string indication an closing parenthesis/bracket.
     * @throws ParseException if the parenthesis are not correct.
     */
    private int createSubNode(Node tokens, Node parsed, String open, String close) {
        int opened = 1;
        int subIndex = 0;
        for (AbstractNode subToken : tokens) {
            if (subToken.toString().equals(open)) {
                opened++;
            }
            if (subToken.toString().equals(close)) {
                if (opened == 1) {
                    parsed.add(parseParenthesisTree(new Node(tokens.subList(0, subIndex))));
                    return subIndex;
                } else {
                    opened--;
                }
            }
            subIndex++;
        }
        throw new ParseException("Parenthesis are wrong");
    }

    public Tokenizer getTokenizer() {
        return tokenizer;
    }
}
