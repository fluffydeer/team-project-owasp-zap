package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser;

import java.util.ArrayList;
import java.util.Collections;

public class Tokenizer {

    private ArrayList<String> parenthesis;
    private ArrayList<String> regexes = new ArrayList<>();

    public Tokenizer(ArrayList<String> parenthesis) {
        this.parenthesis = parenthesis;
    }

    /**
     * Turns the given string in to a list of string tokens.
     * The string is split on whitespaces and newlines.
     * Regexes are replaced with a reference to a list.
     * @param string: The string to be tokenized.
     */
    public ArrayList<String> tokenize(String string) {
        ArrayList<String> result = new ArrayList<>();
        string = replaceRegexes(string);
        string = string.replaceAll(",", "");
        String[] split = string.split("[\\r\\n\t ]+");
        for (String subString : split) {
            result.addAll(pealOfParenthesis(subString));
        }
        return result;
    }

    /**
     * Replaces regexes in the given string with a reference to a list.
     * The regexes are stored in the list for later use.
     * @param string: The string where regexes are replaced.
     */
    private String replaceRegexes(String string) {
        if (string.contains("MATCHES")) {
            int index = string.indexOf("MATCHES");
            String subString = string.substring(index+8);
            String regex = subString.split("\\r?\\n")[0];
            regexes.add(regex);
            return string.substring(0, index) + "MATCHES " + "$" + (regexes.size()-1) + replaceRegexes(string.substring(index+8+regex.length()));
        } else {
            return string;
        }
    }

    /**
     * Parenthesis in the given string are 'pealed of' the words.
     * The resulting list has the words and parenthesis of the original string in the same order
     * but with the parenthesis as separate objects.
     * @param string: The string to be processed.
     */
    private ArrayList<String> pealOfParenthesis(String string) {
        if (string.length() == 0) {
            return new ArrayList<>();
        }
        if (parenthesis.contains(string)) {
            return new ArrayList<>(Collections.singleton(string));
        }
        for (String parenthes : parenthesis) {
            if (string.contains(parenthes)) {
                int index = string.indexOf(parenthes);
                ArrayList<String> result =  pealOfParenthesis(string.substring(0, index));
                result.add(parenthes);
                result.addAll(pealOfParenthesis(string.substring(index+1)));
                return result;
            }
        }
        return new ArrayList<>(Collections.singletonList(string));
    }

    public String getRegex(int index) {
        return regexes.get(index);
    }

}
