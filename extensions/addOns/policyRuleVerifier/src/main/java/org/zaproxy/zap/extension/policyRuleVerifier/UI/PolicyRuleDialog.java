package org.zaproxy.zap.extension.policyRuleVerifier.UI;

import org.zaproxy.zap.extension.policyRuleVerifier.ExtensionPolicyRuleVerifier;
import org.zaproxy.zap.view.SingleColumnTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PolicyRuleDialog extends PolicyImportDialog {

    /**
     * Initialises a new dialog with the knowledge of the ExtensionPolicyRuleVerifier object that calls it.
     * @param extension: the ExtensionPolicyRuleVerifier object that calls it
     * @param owner: owner Frame needed for the super constructor
     */
    public PolicyRuleDialog(Frame owner, ExtensionPolicyRuleVerifier extension){
        super(owner, "policyRuleVerifier.policymgr.title", new Dimension(512, 400));
        this.removeAllFields();
        this.extension = extension;
        List<JButton> buttons = new ArrayList<>();
        buttons.add(createImportButton("import jar"));
        tableModel = createParamsModel("Policy Name");
        tableModel.setLines(extension.listPolicyJARNames());
        this.addTableField(createParamsTable(tableModel), buttons);
        updateTable();
    }

    /**
     * Updates the policy names listed in the dialog.
     */
    @Override
    protected void updateTable() {
        tableModel.setLines(extension.listPolicyJARNames());
    }
}
