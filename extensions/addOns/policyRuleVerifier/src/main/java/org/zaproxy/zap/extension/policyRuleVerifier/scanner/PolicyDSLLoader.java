package org.zaproxy.zap.extension.policyRuleVerifier.scanner;

import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser.Policy;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser.PolicyParser;

import java.io.*;

public class PolicyDSLLoader {

    private static PolicyParser policyParser = new PolicyParser();

    /**Constructor*/
    public PolicyDSLLoader(){}

    /**
     * @param   file    File to read the content from.
     * @return  Policy  New policy created from the content of the provided file.
     */
    public static Policy getPolicyFromDSL(File file){
        return getPolicy(readFile(file));
    }


    /**
     * Extracts content of a file with the DSL specification.
     *
     * @param   file    File to read the content from.
     * @return  String  Content of the file.
     */
    public static String readFile(File file){
        String fileContent = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine()) != null) {
                fileContent += line + "\r\n";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return fileContent;
    }

    /**
     * Parses a policy from the provided string.
     *
     * @param   policyToParse   String containing policy with rules to be parsed.
     * @return  Policy          New policy created as defined in the input.
     */
    private static Policy getPolicy(String policyToParse){
        return policyParser.parse(policyToParse);
    }
}
