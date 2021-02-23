package org.zaproxy.zap.extension.policyRuleVerifier.UI;

import org.parosproxy.paros.Constant;
import org.parosproxy.paros.view.View;
import org.zaproxy.zap.extension.policyRuleVerifier.ExtensionPolicyRuleVerifier;
import org.zaproxy.zap.view.SingleColumnTableModel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;

public abstract class PolicyImportDialog extends PolicyDialog {

    protected ExtensionPolicyRuleVerifier extension;
    protected SingleColumnTableModel tableModel;

    /**
     * Initializes a new dialog to import a policy.
     * @param owner: owner Frame needed for the super constructor
     * @param titleLabel: title of the dialog, displayed at the top
     * @param dim: dimensions of the dialog
     */
    public PolicyImportDialog(Frame owner, String titleLabel, Dimension dim) {
        super(owner, titleLabel, dim);
    }

    /**
     * The import button for the DSL text file. It initialises the policy once clicked.
     * @String name: The text displayed on the button.
     */
    protected JButton createImportButton(String name) {
        JButton importButton = new JButton(name);
        importButton.addActionListener(
                e -> {
                    JFileChooser chooser = new JFileChooser(Constant.getZapHome());
                    File file = null;
                    int rc = chooser.showOpenDialog(View.getSingleton().getMainFrame());
                    if (rc == JFileChooser.APPROVE_OPTION) {
                        file = chooser.getSelectedFile();
                        if (file == null) {
                            return;
                        }
                        try {
                            extension.initPolicy(file);
                        } catch (MalformedURLException ex) {
                            ex.printStackTrace();
                        }
                        this.updateTable();
                    }
                });
        return importButton;
    }

    /**
     * Updates the policy names in the dialog.
     */
    protected abstract void updateTable();
}
