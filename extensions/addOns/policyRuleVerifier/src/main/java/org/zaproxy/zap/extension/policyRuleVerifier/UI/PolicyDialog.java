package org.zaproxy.zap.extension.policyRuleVerifier.UI;

import org.zaproxy.zap.view.SingleColumnTableModel;
import org.zaproxy.zap.view.StandardFieldsDialog;

import javax.swing.*;
import java.awt.*;

public abstract class PolicyDialog extends StandardFieldsDialog {

    /**
     * Initializes a new dialog for the Policy Rule Verifier.
     * @param owner: owner Frame needed for the super constructor
     * @param titleLabel: title of the dialog, displayed at the top
     * @param dim: dimensions of the dialog
     */
    public PolicyDialog(Frame owner, String titleLabel, Dimension dim) {
        super(owner, titleLabel, dim);
    }

    /**
     * The SingleColumnTableModel object needed for the JTable shown in the dialog.
     */
    protected final SingleColumnTableModel createParamsModel(String columnName) {
        SingleColumnTableModel paramsModel = new SingleColumnTableModel(columnName);
        paramsModel.setEditable(false);
        return paramsModel;
    }

    /**
     * The JTable object needed for the layout of the dialog.
     */
    protected JTable createParamsTable(SingleColumnTableModel tableModel) {
        JTable paramsTable = new JTable();
        paramsTable.setModel(tableModel);
        return paramsTable;
    }

    /**
     * A dialog in zaproxy always contains a "save" button.
     * We don't perform any action.
     */
    @Override
    public void save() {

    }

    /**
     * Method that checks certain things when the dialog save button is pressed.
     * We return null because our save button doesn't perform any action.
     * @return String containing an error message.
     */
    @Override
    public String validateFields() {
        return null;
    }
}
