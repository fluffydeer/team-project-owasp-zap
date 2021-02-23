package org.zaproxy.zap.extension.policyRuleVerifier.scanner;

import org.parosproxy.paros.core.proxy.ProxyListener;
import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.extension.policyRuleVerifier.ExtensionPolicyRuleVerifier;
import org.zaproxy.zap.extension.policyRuleVerifier.policies.AbstractPolicy;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser.Policy;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PolicyRuleScanner implements ProxyListener {

    private List<AbstractPolicy> policiesJAR = new ArrayList<>();
    private List<Policy> policiesDSL = new ArrayList<>();
    private ExtensionPolicyRuleVerifier extension;

    public PolicyRuleScanner(ExtensionPolicyRuleVerifier extension){
        this.extension = extension;
    }

    /**
     * Initialize new policies in a jar file.
     * @param jarPath: url where to find the jar file.
     */
    public void initializeNewJARPolicy(File jarPath) throws MalformedURLException {
        List<AbstractPolicy> newPolicies = PolicyJarLoader.getPoliciesFromJar(jarPath);
        policiesJAR.addAll(newPolicies);
        for (AbstractPolicy policy : newPolicies) {
            policy.setRules();
        }
    }

    /**
     * Initialize new policies in a txt file written in a domain specific language (DSL)
     * @param file
     */
    public void initializeNewDSLPolicy(File file){
        Policy newPolicy = PolicyDSLLoader.getPolicyFromDSL(file);
        policiesDSL.add(newPolicy);
    }

    /**
     * A getter for DSL and JAR policies.
     * @return: a list of all active policies.
     */
    public ArrayList<AbstractPolicy> getAllPolicies () {
        ArrayList<AbstractPolicy> allPolicies = new ArrayList<>();
        allPolicies.addAll(policiesJAR);
        allPolicies.addAll(policiesDSL);
        return allPolicies;
    }


    /**
     * Intercepts the HttpMessage when a request is made to the server.
     * We always send it through (so we return True), but we read the message and scan it.
     * @param msg: the intercepted message.
     * @return: True if the message can be passed through to the server, False if we interfere.
     */
    @Override
    public boolean onHttpRequestSend(HttpMessage msg) {
        for (AbstractPolicy policy : getAllPolicies()) {
            handleUnSatisfiedRules(policy.checkRequestRules(msg), policy, msg);
        }
        return true;
    }

    /**
     * Intercepts the HttpMessage when a response returns from the server.
     * We always send it through (so we return True), but we read the message and scan it.
     * @param msg: the intercepted message.
     * @return: True if the message can be passed through to the user, False if we interfere.
     */
    @Override
    public boolean onHttpResponseReceive(HttpMessage msg) {
        for (AbstractPolicy policy : getAllPolicies()) {
            handleUnSatisfiedRules(policy.checkResponseRules(msg), policy, msg);
        }
        return true;
    }

    /**
     * Handles the unsatisfied rules that are reported by the scanner.
     * @param violatedRules: The violated rules.
     * @param policy: The policy associated with the violated rules.
     * @param msg: The HttpMessage that violated the rules.
     */
    private void handleUnSatisfiedRules(List<String> violatedRules, AbstractPolicy policy, HttpMessage msg) {
        for (String rule: violatedRules){
            extension.raiseRule(policy.getName(), rule, msg.getRequestHeader().getHostName());
        }
    }

    /**
     * Gets the order of when this listener should be notified.
     */
    @Override
    public int getArrangeableListenerOrder() {
        return 0;
    }

    /**
     * Returns the policies that are currently known to the scanner.
     */
    public List<AbstractPolicy> getPoliciesJAR() {
        return policiesJAR;
    }


    /**
     * Returns the policies that are currently known to the scanner.
     */
    public List<Policy> getPoliciesDSL() {
        return policiesDSL;
    }

    /**
     * Returns all the names of the policies currently known to the scanner.
     */
    public List<String> getPolicyJARNames() {
        List<String> policyNames = new ArrayList<>();
        for (AbstractPolicy policy: policiesJAR){
            policyNames.add(policy.getName());
        }
        return policyNames;
    }

    /**
     * A getter function for names and contents of loaded policies
     * @return  HashMap of policies containing their name and content
     */

    public HashMap<String, String> getPolicyDSLNames() {
        HashMap<String, String> DSLPolicies = new HashMap<>();
        for (Policy policy: policiesDSL) {
            DSLPolicies.put(policy.getName(), policy.getPolicyContent());
        }
        return DSLPolicies;
    }

}
