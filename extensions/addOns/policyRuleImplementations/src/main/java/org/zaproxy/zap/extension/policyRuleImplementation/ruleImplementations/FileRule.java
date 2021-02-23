package org.zaproxy.zap.extension.policyRuleImplementation.ruleImplementations;

import org.apache.log4j.Logger;
import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.extension.policyRuleVerifier.policies.RuleInterface;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that loads a file and checks whether the contents of this file are found anywhere in a HttpMessage
 */
public abstract class FileRule implements RuleInterface {

    private static final Logger logger = Logger.getLogger(ContainsKeyWordRule.class);

    /**
     * The checklist of strings that we will flag.
     */
    List<String> checklist;

    public FileRule(String fileName) {
        checklist = loadFileList(fileName);
    }

    /**
     * Load the elements of the file into the checklist.
     * @param fileName: the file that is loaded and read.
     */
    private List<String> loadFileList(String fileName) {
        List<String> fileList = new ArrayList<>();
        InputStream in = getClass().getResourceAsStream(fileName);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = reader.readLine()) != null) {
                fileList.add(line);
            }
        } catch (Exception e) {
            logger.error("Error on opening/reading domains.txt file. Error: " + e.getMessage(), e);
        }
        return fileList;
    }

    /**
     * Checks whether the string contains an element of the checklist.
     * @param string
     * @return
     */
    boolean isPresentInList(String string){
        for(String check: checklist){
            if(string.contains(check)){
                return true;
            }
        }
        return false;
    }

    @Override
    public abstract boolean responseSatisfiesRule(HttpMessage msg);

    @Override
    public abstract boolean requestSatisfiesRule(HttpMessage msg);

    @Override
    public abstract String getName();

}
