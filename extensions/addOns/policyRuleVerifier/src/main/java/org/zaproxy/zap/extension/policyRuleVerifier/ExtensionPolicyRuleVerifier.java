package org.zaproxy.zap.extension.policyRuleVerifier;

import org.parosproxy.paros.Constant;
import org.parosproxy.paros.extension.ExtensionAdaptor;
import org.parosproxy.paros.extension.ExtensionHook;
import org.zaproxy.zap.extension.policyRuleVerifier.UI.PolicyRuleDSLDialog;
import org.zaproxy.zap.extension.policyRuleVerifier.UI.PolicyRuleDialog;
import org.zaproxy.zap.extension.policyRuleVerifier.UI.PolicyRuleReportDialog;
import org.zaproxy.zap.extension.policyRuleVerifier.reporting.PolicyRuleReport;
import org.zaproxy.zap.extension.policyRuleVerifier.scanner.PolicyRuleScanner;
import org.zaproxy.zap.view.ZapMenuItem;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;


public class ExtensionPolicyRuleVerifier extends ExtensionAdaptor {

    private PolicyRuleScanner scanner;
    private PolicyRuleReport report;

    public ExtensionPolicyRuleVerifier() {
        this.scanner = new PolicyRuleScanner(this);
        this.report = new PolicyRuleReport();
    }

    /**
     * Get the description of this extension from the properties file.
     */
    @Override
    public String getDescription() {
        return Constant.messages.getString("policyRuleVerifier.desc");
    }

    /**
     * Hook this extension into zaproxy and create the needed menu items.
     * @param extensionHook: The ExtensionHook object that will hook our extension into zaproxy
     */
    @Override
    public void hook(ExtensionHook extensionHook) {
        super.hook(extensionHook);
        extensionHook.addProxyListener(scanner);
        if (getView() != null) {
            extensionHook.getHookMenu().addAnalyseMenuItem(getMenuItemPolicyRule());
            extensionHook.getHookMenu().addAnalyseMenuItem(getMenuItemPolicyRuleReport());
            extensionHook.getHookMenu().addAnalyseMenuItem(getMenuItemPolicyDSL());
        }
    }

    /**
     * Returns the name of our extension.
     */
    @Override
    public String getName() {
        return "Policy Rule Verifier";
    }

    /**
     * Returns the author of this extension.
     */
    @Override
    public String getAuthor() {
        return "OSS-Group03";
    }

    /**
     * Returns the report associated with this Policy Rule Verifier.
     */
    public PolicyRuleReport getReport() { return this.report; }

    /**
     * Returns the scanner associated with this Policy Rule Verifier.
     */
    public PolicyRuleScanner getScanner() {
        return this.scanner;
    }

    /**
     * Creates a new menu item in Zaproxy for the policies to be loaded.
     */
    private ZapMenuItem getMenuItemPolicyRule() {
        ZapMenuItem menuItemPolicy = new ZapMenuItem("policyRuleVerifier.menu.analyse.pscanPolicy", null);
        menuItemPolicy.addActionListener(e -> this.showPolicyRuleDialog());
        return menuItemPolicy;
    }

    /**
     * Creates a new menu item in Zaproxy for the report.
     */
    private ZapMenuItem getMenuItemPolicyRuleReport() {
        ZapMenuItem menuItemReport = new ZapMenuItem("policyRuleVerifier.menu.analyse.pscanPolicyReport", null);
        menuItemReport.addActionListener(e -> this.showReportDialog());
        return menuItemReport;
    }

    /**
     * Creates a new menu item in Zaproxy for the report.
     */
    private ZapMenuItem getMenuItemPolicyDSL() {
        ZapMenuItem menuItemDSL = new ZapMenuItem("policyRuleVerifier.menu.analyse.policyDSL", null);
        menuItemDSL.addActionListener(e -> this.showPolicyRuleDSLDialog());
        return menuItemDSL;
    }

    /**
     * Initialises a new policy loading dialog for jar files.
     */
    public void showPolicyRuleDialog() {
        PolicyRuleDialog policyRuleDialog = new PolicyRuleDialog(this.getView().getMainFrame(), this);
        policyRuleDialog.setVisible(true);
    }

    /**
     * Initialises a new policy loading dialog for DSL files
     */
    public void showPolicyRuleDSLDialog() {
        PolicyRuleDSLDialog policyRuleDSLDialog = new PolicyRuleDSLDialog(this.getView().getMainFrame(), this);
        policyRuleDSLDialog.setVisible(true);
    }

    /**
     * Initialises a new report dialog.
     */
    public void showReportDialog() {
        PolicyRuleReportDialog policyRuleReportDialog = new PolicyRuleReportDialog(this.getView().getMainFrame(), report.getReportedRules());
        policyRuleReportDialog.setVisible(true);
    }

    /**
     * Method that passes the user request to load a new policy to the scanner.
     * @param file: the file where the policy that needs to be loaded can be found.
     */
    public void initPolicy(File file) throws MalformedURLException {
        if (file.toString().endsWith(".jar")) {
            scanner.initializeNewJARPolicy(file);
        }
        else if (file.toString().endsWith(".txt")) {
            scanner.initializeNewDSLPolicy(file);
        }
    }

    /**
     * List all the policy names that are currently loaded.
     */
    public List<String> listPolicyJARNames(){
        return scanner.getPolicyJARNames();
    }

    /**
     * List all the policy names that are currently loaded.
     */
    public HashMap<String, String> listPolicyDSLNames() {
        return scanner.getPolicyDSLNames();
    }

    /**
     * Raise a violated rule to the report.
     * @param policy: Policy name associated with the violated rule
     * @param rule: The violated rule name
     * @param host: The host at which the rule was violated.
     */
    public void raiseRule(String policy, String rule, String host) {
        report.addReportedRule(policy, rule, host);
    }
}
