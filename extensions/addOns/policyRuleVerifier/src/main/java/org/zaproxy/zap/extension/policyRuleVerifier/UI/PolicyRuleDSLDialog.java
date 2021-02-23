package org.zaproxy.zap.extension.policyRuleVerifier.UI;

import org.zaproxy.zap.extension.policyRuleVerifier.ExtensionPolicyRuleVerifier;
import org.zaproxy.zap.view.SingleColumnTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class PolicyRuleDSLDialog extends PolicyImportDialog {

    /**
     * Initialises a new dialog with the knowledge of the ExtensionPolicyRuleVerifier object that calls it.
     * @param extension: the ExtensionPolicyRuleVerifier that calls this dialog
     * @param owner: owner Frame needed for the super constructor
     */
    public PolicyRuleDSLDialog(Frame owner, ExtensionPolicyRuleVerifier extension){
        super(owner, "policyRuleVerifier.policyDSL.title", new Dimension(512, 400));
        this.removeAllFields();
        this.extension = extension;
        List<JButton> buttons = new ArrayList<>();
        buttons.add(createImportButton("import DSL"));
        tableModel = createParamsModel("Policy Name");
        this.addTableField(createParamsTable(tableModel, owner), buttons);
        updateTable();
    }

    /**
     * The JTable object needed for the layout of the dialog.
     */
    protected JTable createParamsTable(SingleColumnTableModel model, Frame owner) {
            JTable paramsTable = new JTable();
            paramsTable.setModel(model);
            paramsTable.addMouseListener(
                    new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            if (e.getClickCount() >= 2) {
                                int row = paramsTable.rowAtPoint(e.getPoint());
                                if (row >= 0) {
                                    String name = (String) model.getValueAt(row, 0);
                                    if (name != null) {
                                            String content = extension.listPolicyDSLNames().get(name);
                                            showPolicyContentDialog(content, owner);
                                    }
                                }
                            }
                        }
                    });
        return paramsTable;
    }

    /**
     * Initialises a new PolicyContentDialog.
     * @param content: the contents of the policy we want to show
     */
    public void showPolicyContentDialog(String content, Frame owner) {
        PolicyDSLContentDialog dialog = new PolicyDSLContentDialog(owner, content);
        dialog.setVisible(true);
    }

    /**
     * Updates the DSL policy names in the dialog.
     */
    @Override
    protected void updateTable() {
        tableModel.setLines(new ArrayList<>(extension.listPolicyDSLNames().keySet()));
    }
}
