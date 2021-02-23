package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser;


import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.UnaryExpressions.Rule;

import java.util.LinkedList;
import java.util.List;

public class PolicyParser {
    private static final String STATEMENT_SEPARATOR = ";";
    private static final String NAME_SEPARATOR = ":";
    private static final String POLICY_INDICATOR = "P:";
    private static final String RULE_INDICATOR = "R:";
    private RuleParser ruleParser;

    /** Create a PolicyParser */
    public PolicyParser() {
        ruleParser = new RuleParser();
    }

    /**
     * Parse the content of a file to a Policy with Rules.
     *
     * @param   content     The complete content of the policy-file.
     * @return  Policy      The Policy with corresponding name and rules.
     * @throws  ParseException  If the format of the content does not follow
     *                           the DSL-specification or the content is empty.
     */
    public Policy parse(String content) throws ParseException {
        if (! content.contains(STATEMENT_SEPARATOR)) {
            throw new ParseException("The policy-file cannot be empty");
        }

        String[] chunks = content.split(STATEMENT_SEPARATOR);

        String policyName = parsePolicyName(chunks[0]);
        //TODO: review if correct, if not then delete - removing policy name from the content
        //content = content.substring(chunks[0].length()+1);
        Policy policy = new Policy(policyName, content);

        policy.addAllRules(parseRules(chunks));
        policy.setRules();

        return policy;
    }


    /**
     * Extracts the name of the policy from the given line.
     *
     * @param   policyLine  The line that includes the name of the policy.
     * @return  String      The name of the policy.
     * @throws  ParseException  If the given line does not have
     *                              the correct format.
     */
    private String parsePolicyName(String policyLine) throws ParseException {
        if (! policyLine.startsWith(POLICY_INDICATOR)) {
            throw new ParseException("The first line of the policy description should have format: 'P:policy name;'");
        }

        return policyLine.substring(policyLine.indexOf(POLICY_INDICATOR) + POLICY_INDICATOR.length());
    }

    /**
     * Parses each rule given the chunks of the input file contents, this method.
     *
     * @param   chunks  The chunks of the input file contents.
     * @return  List    A list with the body of each rule.
     * @throws  ParseException  If a chunk is not formatted correctly
     *                              according to the DSL specification.
     */
    private List<Rule> parseRules(String[] chunks) throws ParseException {
        List<Rule> rules = new LinkedList<>();

        for(String chunk : chunks) {
            // Ignore Policy chunk (already parsed) and empty lines
            if (chunk.startsWith(POLICY_INDICATOR) || chunk.replaceAll("[\\r\\n\t ]+", "").isEmpty()) {
                continue;
            }

            if (chunk.indexOf(RULE_INDICATOR) == -1) {
                throw new ParseException("Rules should have format: 'R:rulename:rule;");
            }

            String ruleContent = chunk.substring(chunk.indexOf(RULE_INDICATOR) + RULE_INDICATOR.length());
            rules.add(parseRule(ruleContent));
        }

        return rules;
    }

    /**
     * Parses a single Rule.
     * @param ruleContent: The string to be parsed to a rule.
     * @return Rule
     */
    private Rule parseRule(String ruleContent) {
        if (! ruleContent.contains(NAME_SEPARATOR)) {
            throw new ParseException("Rules should have format: 'R:rulename:rule;");
        }

        String[] rule_parts = ruleContent.split(NAME_SEPARATOR);

        Rule newRule = ruleParser.parse(rule_parts[1]);
        newRule.setName(rule_parts[0]);
        return newRule;
    }
}
