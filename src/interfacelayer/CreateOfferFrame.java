package interfacelayer;

import servicelayer.ServiceLayer;
import servicelayer.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;

/**
 * Created by Victor on 25.05.2016.
 */
public class CreateOfferFrame extends JFrame {

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPanel = new JPanel();
        rootPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
        descriptionLabel = new JLabel();
        descriptionLabel.setText("Description");
        rootPanel.add(descriptionLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        descriptionField = new JTextField();
        rootPanel.add(descriptionField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        priceLabel = new JLabel();
        priceLabel.setText("Price");
        rootPanel.add(priceLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        periodLabel = new JLabel();
        periodLabel.setText("Period");
        rootPanel.add(periodLabel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        socialMediaLabel = new JLabel();
        socialMediaLabel.setText("Social media");
        rootPanel.add(socialMediaLabel, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        priceField = new JTextField();
        rootPanel.add(priceField, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        periodField = new JTextField();
        rootPanel.add(periodField, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        socialMediaField = new JTextField();
        rootPanel.add(socialMediaField, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        createButton = new JButton();
        createButton.setText("Create");
        rootPanel.add(createButton, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

    enum FrameType {
        OFFER_TYPE,
        REQUEST_TYPE
    }

    FrameType frameType;

    ServiceLayer serviceLayer = new ServiceLayer();

    public CreateOfferFrame(FrameType frameType) {
        super("Create...");
        this.frameType = frameType;
        setContentPane(rootPanel);
        pack();
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createOffer();
            }
        });
    }

    private void createOffer() {
        if (descriptionField.getText().isEmpty()
                || priceField.getText().isEmpty()
                || socialMediaField.getText().isEmpty()
                || periodField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Empty fields");
            return;
        }

        Util.Result result = Util.Result.FAILED;

        if (frameType == FrameType.OFFER_TYPE)
            result = serviceLayer.createOffer(Double.parseDouble(priceField.getText()),
                    Integer.parseInt(periodField.getText()), descriptionField.getText(), socialMediaField.getText());
        else
            result = serviceLayer.createRequest(Double.parseDouble(priceField.getText()),
                    Integer.parseInt(periodField.getText()), socialMediaField.getText(), descriptionField.getText());

        String msg = "";
        if (result == Util.Result.INVALID_PARAMS)
            msg = "Invalid params";
        else if (result == Util.Result.USER_NOT_EXIST)
            msg = "User not exist";
        else if (result == Util.Result.INTERNAL_ERROR)
            msg = "Internal error";
        else if (result == Util.Result.SUCCEED)
            msg = "Created";

        JOptionPane.showMessageDialog(rootPane, msg);

        if (result == Util.Result.SUCCEED) {
            this.setVisible(false);
            dispose();
        }
    }

    private JLabel descriptionLabel;
    private JTextField descriptionField;
    private JPanel rootPanel;
    private JLabel periodLabel;
    private JLabel socialMediaLabel;
    private JTextField priceField;
    private JTextField periodField;
    private JTextField socialMediaField;
    private JButton createButton;
    private JLabel priceLabel;


}
