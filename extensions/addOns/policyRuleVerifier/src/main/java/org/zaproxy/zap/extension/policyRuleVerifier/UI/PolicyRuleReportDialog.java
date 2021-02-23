package org.zaproxy.zap.extension.policyRuleVerifier.UI;

import org.zaproxy.zap.view.SingleColumnTableModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PolicyRuleReportDialog extends PolicyDialog {

    /**
     * Initialises a new dialog with the knowledge of the report.
     * @param report: the report that needs to be shown in the dialog
     * @param owner: owner Frame needed for the super constructor
     */
    public PolicyRuleReportDialog(Frame owner, List<String> report) {
        super(owner, "policyRuleVerifier.policyReport.title", new Dimension(512, 400));
        this.removeAllFields();
        SingleColumnTableModel tableModel = createParamsModel("Policy Rules Reported");
        tableModel.setLines(report);
        this.addTableField(createParamsTable(tableModel), new ArrayList<>());
    }
}
