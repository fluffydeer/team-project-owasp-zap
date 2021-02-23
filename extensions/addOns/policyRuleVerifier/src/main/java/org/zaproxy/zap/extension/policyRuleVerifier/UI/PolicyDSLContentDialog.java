package org.zaproxy.zap.extension.policyRuleVerifier.UI;

import org.zaproxy.zap.view.SingleColumnTableModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class creates objects that show the contents of the DSL Policy file.
 */
public class PolicyDSLContentDialog extends PolicyDialog {

    /**
     * Initializes a new dialog that shows the content of a policy.
     * @param owner: owner Frame needed for the super constructor
     * @param content: the contents of the policy text file.
     */
    public PolicyDSLContentDialog(Frame owner, String content) {
        super(owner, "policyRuleVerifier.policyDSL.content.title", new Dimension(512, 400));
        this.removeAllFields();
        SingleColumnTableModel tableModel = createParamsModel("Text file contents");
        tableModel.setLines(Arrays.asList(content.split("\r?\n")));
        this.addTableField(createParamsTable(tableModel), new ArrayList<>());
    }
}
