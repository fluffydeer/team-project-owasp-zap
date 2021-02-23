package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser;

import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.AbstractExpression;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.UnaryExpressions.NotExpression;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.UnaryExpressions.Rule;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions.AndExpression;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions.BinaryExpression;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions.OrExpression;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions.basicExpressions.ContainsExpression;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions.basicExpressions.InExpression;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions.basicExpressions.IsExpression;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.binaryExpressions.basicExpressions.MatchesExpression;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.getters.BodyGetter;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.getters.HeaderFieldGetter;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.getters.HeaderGetter;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.literals.StringLiteral;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser.nodes.Leaf;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser.nodes.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RuleParser {

    private ParenthesisParser parenthesisParser;
    private ExpressionFactory expressionFactory;

    private final ArrayList<String> expressionStrings = new ArrayList<>(Arrays.asList("OR", "AND", "NOT"));
    private final ArrayList<String> basicExpressionStrings = new ArrayList<>(Arrays.asList("IS", "IN", "MATCHES", "CONTAINS"));

    public RuleParser() {
        parenthesisParser = new ParenthesisParser();
        expressionFactory = new ExpressionFactory();
    }

    /**
     * Parses a string in to a rule.
     * @param string: The string to be parsed.
     */
    public Rule parse(String string) {
        Node parenthesisTree = parenthesisParser.parseString(string);
        Rule rule = new Rule("Name");
        rule.setSubExpression(createExpressionTree(parenthesisTree));
        return rule;
    }

    /**
     * Build an expression tree from a parenthesis tree.
     * @param parenthesisTree: The parenthesis tree to be parsed.
     */
    private AbstractExpression createExpressionTree(Node parenthesisTree) {
        parenthesisTree = unpackParenthesisTreeIfOnNodeOfSizeOfOne(parenthesisTree);
        try {
            return parseFirstInList(expressionStrings, parenthesisTree);
        } catch (ParseException e) {
            return parseFirstInList(basicExpressionStrings, parenthesisTree);
        }
    }

    /**
     * Unpacks a Node if the given parenthesisTree is of size one and the next level of the tree is also a node.
     * @param parenthesisTree: The parenthesis tree to be unpacked.
     */
    private Node unpackParenthesisTreeIfOnNodeOfSizeOfOne(Node parenthesisTree) {
        if (parenthesisTree.size() == 1 && parenthesisTree.get(0) instanceof Node) {
            return (Node) parenthesisTree.get(0);
        }
        return parenthesisTree;
    }

    /**
     * Goes over the list of given tokens looking for one that occurs in the parenthesisTree.
     * When a token from the list matches with a token in the parenthesisTree
     * an expression tree is returned with the token as root expression.
     * @param list: The list of tokens to match against.
     * @param parenthesisTree: The parenthesis tree to be parsed.
     * @throws ParseException if none of the tokens in the list can be matched against a leaf in the parenthesis tree.
     */
    private AbstractExpression parseFirstInList(List<String> list, Node parenthesisTree) {
        for (String expressionToken : list) {
            Leaf token = new Leaf(expressionToken);
            if (parenthesisTree.contains(token)) {
                return parseExpression(expressionToken, parenthesisTree);
            }
        }
        throw new ParseException("Given parenthesis tree doesn't contain any of the tokens in the list.");
    }

    /**
     * Parses a token string to an expression.
     * @param token: The token string to be parsed.
     * @throws ParseException if the token is not recognized.
     */
    private AbstractExpression parseExpression(String token, Node parenthesisTree) {
        switch (token) {
            case "NOT"  : return parseNotExpression(token, parenthesisTree);
            case "OR"   : return parseOrExpression(token, parenthesisTree);
            case "AND"  : return parseAndExpression(token, parenthesisTree);
            case "REQ"  : return expressionFactory.createRequestGetter();
            case "RES"  : return expressionFactory.createResponseGetter();
            case "HEAD" : return parseHeaderGetter(token, parenthesisTree);
            case "BODY" : return parseBodyGetter(token, parenthesisTree);
            case "IN"   : return parseInExpression(token, parenthesisTree);
            case "IS"   : return parseIsExpression(token, parenthesisTree);
            case "MATCHES" : return parseMatchesExpression(token, parenthesisTree);
            case "CONTAINS": return parseContainsExpression(token, parenthesisTree);

            default: throw new ParseException("Token not recognized: " + token);
        }
    }

    /**
     * Creates an expressionTree tree from the given parenthesis tree with a NotExpression as root.
     * @param token: The NOT token
     * @param parenthesisTree: The parenthesis tree to be parsed
     */
    private NotExpression parseNotExpression(String token, Node parenthesisTree) {
        int index = parenthesisTree.indexOf(new Leaf(token));
        NotExpression expression = expressionFactory.createNotExpression();
        expression.setSubExpression(createExpressionTree(parenthesisTree.subList(index+1, parenthesisTree.size())));
        return expression;
    }

    /**
     * Creates an expressionTree tree from the given parenthesis tree with an AndExpression as root.
     * @param token: The AND token
     * @param parenthesisTree: The parenthesis tree to be parsed
     */
    private AndExpression parseAndExpression(String token, Node parenthesisTree) {
        AndExpression expression = expressionFactory.createAndExpression();
        setSubExpressionsBinaryExpression(token, parenthesisTree, expression);
        return expression;
    }

    /**
     * Creates an expressionTree tree from the given parenthesis tree with an OrExpression as root.
     * @param token: The OR token
     * @param parenthesisTree: The parenthesis tree to be parsed
     */
    private OrExpression parseOrExpression(String token, Node parenthesisTree) {
        OrExpression expression = expressionFactory.createOrExpression();
        setSubExpressionsBinaryExpression(token, parenthesisTree, expression);
        return expression;
    }

    /**
     * Sets the left and right subExpression of a BinaryExpression to an expression tree parsed from the tokens the left and right respectively.
     * @param token: The token of the BinaryExpression.
     * @param parenthesisTree: The parenthesis tree to be parsed.
     * @param expression: The BinaryExpression.
     */
    private void setSubExpressionsBinaryExpression(String token, Node parenthesisTree, BinaryExpression expression) {
        int index = parenthesisTree.indexOf(new Leaf(token));
        expression.setlExpression(createExpressionTree(parenthesisTree.subList(0, index)));
        expression.setrExpression(createExpressionTree(parenthesisTree.subList(index+1, parenthesisTree.size())));
    }

    /**
     * Creates an expressionTree tree from the given parenthesis tree with a MatchesExpression as root.
     * @param token: The MATCHES token
     * @param parenthesisTree: The parenthesis tree to be parsed
     */
    private MatchesExpression parseMatchesExpression(String token, Node parenthesisTree) {
        int index = parenthesisTree.indexOf(new Leaf(token));
        MatchesExpression expression = expressionFactory.createMatchesExpression();
        expression.setrExpression(new StringLiteral(getRegex(parseRegexIdentifier(parenthesisTree.get(index+1).toString()))));
        expression.setlExpression(parseStringGetter(index, parenthesisTree));
        return expression;
    }

    /**
     * Parses a regex identifier string to an int.
     * The identifier string is of the form: $int, with int an integer number greater than or equal to 0.
     * @param string: The string to be parsed.
     */
    private int parseRegexIdentifier(String string) {
        return Integer.parseInt(string.substring(1));
    }

    /**
     * Creates an expressionTree tree from the given parenthesis tree with an InExpression as root.
     * @param token: The IN token
     * @param parenthesisTree: The parenthesis tree to be parsed
     */
    private InExpression parseInExpression(String token, Node parenthesisTree) {
        int index = parenthesisTree.indexOf(new Leaf(token));
        InExpression expression = expressionFactory.createInExpression();
        List list = parseList(parenthesisTree.get(index+1).toString());
        expression.setrExpression(expressionFactory.createListLiteral(list));
        expression.setlExpression(parseStringGetter(index, parenthesisTree));
        return expression;
    }

    /**
     * Parses a list from a string.
     * @param string: The string to be parsed.
     */
    private List<String> parseList(String string) {
        String[] split = string.replaceAll("\\[?\\]? ?", "").split(",");
        return Arrays.asList(split);
    }

    /**
     * Creates an expressionTree tree from the given parenthesis tree with an IsExpression as root.
     * @param token: The IS token
     * @param parenthesisTree: The parenthesis tree to be parsed
     */
    private IsExpression parseIsExpression(String token, Node parenthesisTree) {
        int index = parenthesisTree.indexOf(new Leaf(token));
        IsExpression expression = expressionFactory.createIsExpression();
        setChildrenBasicExpression(index, parenthesisTree, expression);
        return expression;
    }

    /**
     * Creates an expressionTree tree from the given parenthesis tree with a ContainsExpression as root.
     * @param token: The CONTAINS token
     * @param parenthesisTree: The parenthesis tree to be parsed
     */
    private ContainsExpression parseContainsExpression(String token, Node parenthesisTree) {
        int index = parenthesisTree.indexOf(new Leaf(token));
        ContainsExpression expression = expressionFactory.createContainsExpression();
        setChildrenBasicExpression(index, parenthesisTree, expression);
        return expression;
    }

    /**
     * Sets the subexpressions of a BasicExpression.
     * The left side is a StringGetter parsed from the tokens to the left of the given index.
     * The right side is a StringLiteral parsed from the token to the right of the given index.
     * @param index: The index of the BasicExpression
     * @param parenthesisTree: The parenthesis tree to be parsed.
     * @param expression: The BasicExpression
     */
    private void setChildrenBasicExpression(int index, Node parenthesisTree, BinaryExpression expression) {
        expression.setrExpression(expressionFactory.createStringLiteral(parenthesisTree.get(index+1).toString()));
        expression.setlExpression(parseStringGetter(index, parenthesisTree));
    }

    /**
     * Creates a the string getter used by a basic expression.
     * @param index: The index of the BasicExpression.
     * @param parenthesisTree: The parenthesis tree to be parsed.
     */
    private AbstractExpression<String> parseStringGetter(int index, Node parenthesisTree) {
        try {
            return parseExpression(parenthesisTree.get(index-1).toString(), parenthesisTree);
        } catch (ParseException e) {
            return parseHeaderFieldGetter(index-1, parenthesisTree);
        }
    }

    /**
     * Creates a HeaderFieldGetter with its HeaderGetter.
     * @param index: The index of the headerField name.
     * @param parenthesisTree: The parenthesis tree to be parsed.
     */
    private HeaderFieldGetter parseHeaderFieldGetter(int index, Node parenthesisTree) {
        HeaderFieldGetter expression = expressionFactory.createHeaderFieldGetter();
        expression.setrExpression(expressionFactory.createStringLiteral(parenthesisTree.get(index).toString()));
        expression.setlExpression(parseExpression(parenthesisTree.get(index-1).toString(), parenthesisTree));
        return expression;
    }

    /**
     * Creates a HeaderGetter with its SingleMessage getter.
     * @param token: The HEAD token.
     * @param parenthesisTree: The parenthesis tree to be parsed.
     */
    private HeaderGetter parseHeaderGetter(String token, Node parenthesisTree) {
        int index = parenthesisTree.indexOf(new Leaf(token));
        HeaderGetter expression = expressionFactory.createHeaderGetter();
        expression.setSubExpression(parseExpression(parenthesisTree.get(index-1).toString(), parenthesisTree));
        return expression;
    }

    /**
     * Creates a BodyGetter with its SingleMessage getter.
     * @param token: The BODY token.
     * @param parenthesisTree: The parenthesis tree to be parsed.
     */
    private BodyGetter parseBodyGetter(String token, Node parenthesisTree) {
        int index = parenthesisTree.indexOf(new Leaf(token));
        BodyGetter expression = expressionFactory.createBodyGetter();
        expression.setSubExpression(parseExpression(parenthesisTree.get(index-1).toString(), parenthesisTree));
        return expression;
    }

    /**
     * Gets a regex string for a given id.
     * @param id: The id of the regex.
     */
    private String getRegex(int id) {
        return parenthesisParser.getTokenizer().getRegex(id);
    }
}
