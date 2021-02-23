package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestTokenizer {
    private Tokenizer tokenizer;

    @Before
    public void init() {
        this.tokenizer = new Tokenizer(new ArrayList<>(Arrays.asList("(", ")")));
    }

    @Test
    public void testTokenizeEmptyString() {
        List<String> tokens = tokenizer.tokenize("");

        Assert.assertEquals(tokens, new ArrayList<String>());
    }

    @Test
    public void testTokenizeSpaces() {
        List<String> tokens = tokenizer.tokenize("     ");

        Assert.assertEquals(tokens, new ArrayList<String>());
    }

    @Test
    public void testTokenizeSeperateParenthesis() {
        List<String> tokens = tokenizer.tokenize("( ( )");

        Assert.assertEquals(tokens, new ArrayList<String>(Arrays.asList("(", "(", ")")));
    }

    @Test
    public void testTokenizerAttachedSingleLevelParenthesis() {
        List<String> tokens = tokenizer.tokenize("A AND (B OR C)");

        Assert.assertEquals(tokens, new ArrayList<String>(Arrays.asList(
                "A", "AND", "(", "B", "OR", "C", ")"
        )));
    }

    @Test
    public void testTokenizerBlendedMultiLevelParentheses() {
        List<String> tokens = tokenizer.tokenize("( A AND (B OR ((NOT C ) OR D) ))");

        Assert.assertEquals(tokens, new ArrayList<String>(Arrays.asList(
                "(", "A", "AND", "(", "B", "OR", "(", "(", "NOT", "C", ")", "OR", "D", ")", ")", ")"
        )));
    }
}
