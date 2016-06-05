package interfacelayer;

import entity.Offer;
import entity.Request;
import servicelayer.ServiceLayer;
import servicelayer.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Victor on 25.05.2016.
 */
public class CreateOrder extends JFrame {

    ServiceLayer serviceLayer = new ServiceLayer();
    private JPanel rootPanel;
    private JTextField keywordsField;
    private JLabel socialmediaLabel;
    private JTextField socialmediaField;
    private JLabel priceLabel;
    private JLabel periodLable;
    private JTextField priceField;
    private JTextField periodField;
    private JButton requestButton;
    //private JTable offersTable;
    //private JButton createOrderButton;


    public CreateOrder() {
        super("Create order");
        setContentPane(rootPanel);

        pack();
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
        requestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createRequest();
            }
        });
        /*createOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createOrder();
            }
        });*/
    }

    private void createRequest() {
        if (keywordsField.getText().isEmpty()
                || priceField.getText().isEmpty()
                || socialmediaField.getText().isEmpty()
                || periodField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Empty fields");
            return;
        }

        serviceLayer.createRequest(Double.parseDouble(priceField.getText()),
                Integer.parseInt(periodField.getText()), socialmediaField.getText(), keywordsField.getText());

        /*Map<Integer, List<Offer>> offers = serviceLayer.findOffers(request);

        List<Offer> offersList = new ArrayList<>();
        for (List<Offer> var : offers.values()) {
            offersList.addAll(var);
        }

        offersTable.setModel(new OfferTableModel(offersList));*/
    }

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
        final JLabel label1 = new JLabel();
        label1.setText("Description");
        rootPanel.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        keywordsField = new JTextField();
        rootPanel.add(keywordsField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        socialmediaLabel = new JLabel();
        socialmediaLabel.setText("Social media");
        rootPanel.add(socialmediaLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        socialmediaField = new JTextField();
        rootPanel.add(socialmediaField, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        priceLabel = new JLabel();
        priceLabel.setText("Price (max.)");
        rootPanel.add(priceLabel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        periodLable = new JLabel();
        periodLable.setText("Period (min.)");
        rootPanel.add(periodLable, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        priceField = new JTextField();
        rootPanel.add(priceField, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        periodField = new JTextField();
        rootPanel.add(periodField, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        requestButton = new JButton();
        requestButton.setText("Request");
        rootPanel.add(requestButton, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

    /*private void createOrder() {
        int offerID = Integer.parseInt(
                offersTable.getValueAt(offersTable.getSelectedRow(), 0).toString());
        serviceLayer.createOrder(offerID);
        this.setVisible(false);
        dispose();
    }*/

}
