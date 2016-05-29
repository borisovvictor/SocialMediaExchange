package interfacelayer;

import entity.Agency;
import entity.Client;
import entity.Performer;
import servicelayer.ServiceLayer;
import servicelayer.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Victor on 25.05.2016.
 */
public class CreateUser extends JFrame {

    ServiceLayer serviceLayer = new ServiceLayer();

    private JPanel rootPanel;
    private JComboBox userType;
    private JLabel userTypeLabel;
    private JLabel nameLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel phoneLabel;
    private JButton createButton;
    private JTextField nameField;
    private JTextField usernameField;
    private JTextField phoneField;
    private JTextField agencyField;
    private JPasswordField passwordField;
    private JLabel agencyLabel;

    public CreateUser() {
        super("Create user");

        setContentPane(rootPanel);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

        updateFields();
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUser();
            }
        });
        userType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFields();
            }
        });
    }

    private void updateFields() {
        String userTypeStr = String.valueOf(userType.getSelectedItem());
        if (userTypeStr.contains("Client")) {
            phoneLabel.setVisible(true);
            phoneField.setVisible(true);
            agencyField.setVisible(false);
            agencyLabel.setVisible(false);
        } else if (userTypeStr.contains("Performer")) {
            phoneLabel.setVisible(true);
            phoneField.setVisible(true);
            agencyField.setVisible(true);
            agencyLabel.setVisible(true);
        } else if (userTypeStr.contains("Agency")) {
            phoneLabel.setVisible(false);
            phoneField.setVisible(false);
            agencyField.setVisible(false);
            agencyLabel.setVisible(false);
        }
    }

    private void createUser() {
        if (usernameField.getText().isEmpty()
                || passwordField.getText().isEmpty()
                || nameField.getText().isEmpty()
                || (agencyField.isVisible() && agencyField.getText().isEmpty())) {
            JOptionPane.showMessageDialog(rootPane, "Empty fields");
            return;
        }

        Util.Result result = Util.Result.SUCCEED;

        String userTypeStr = String.valueOf(userType.getSelectedItem());
        if (userTypeStr.contains("Client")) {
            result = serviceLayer.createNewClient(nameField.getText(), usernameField.getText(),
                    passwordField.getText(), phoneField.getText());
        } else if (userTypeStr.contains("Performer")) {
            result = serviceLayer.createNewPerformer(nameField.getText(), usernameField.getText(),
                    passwordField.getText(), phoneField.getText(), Integer.parseInt(agencyField.getText()));
        } else if (userTypeStr.contains("Agency")) {
            result = serviceLayer.createNewAgency(nameField.getText(), usernameField.getText(),
                    passwordField.getText());
        }

        String msg = "";
        if (result == Util.Result.INVALID_PARAMS)
            msg = "Invalid params";
        else if (result == Util.Result.USER_ALREADY_EXIST)
            msg = "User already exist";
        else if (result == Util.Result.INTERNAL_ERROR)
            msg = "Internal error";
        else if (result == Util.Result.SUCCEED)
            msg = "User created";
        else
            msg = "Unknown error";

        JOptionPane.showMessageDialog(rootPane, msg);

        if (result == Util.Result.SUCCEED) {
            this.setVisible(false);
            dispose();
        }
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
        rootPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(7, 2, new Insets(0, 0, 0, 0), -1, -1));
        userTypeLabel = new JLabel();
        userTypeLabel.setText("Type");
        rootPanel.add(userTypeLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        userType = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Client");
        defaultComboBoxModel1.addElement("Performer");
        defaultComboBoxModel1.addElement("Agency");
        userType.setModel(defaultComboBoxModel1);
        rootPanel.add(userType, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nameLabel = new JLabel();
        nameLabel.setText("Name");
        rootPanel.add(nameLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        usernameLabel = new JLabel();
        usernameLabel.setText("Username");
        rootPanel.add(usernameLabel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passwordLabel = new JLabel();
        passwordLabel.setText("Password");
        rootPanel.add(passwordLabel, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        phoneLabel = new JLabel();
        phoneLabel.setText("Phone");
        rootPanel.add(phoneLabel, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        agencyLabel = new JLabel();
        agencyLabel.setText("Agency");
        rootPanel.add(agencyLabel, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        createButton = new JButton();
        createButton.setText("Create");
        rootPanel.add(createButton, new com.intellij.uiDesigner.core.GridConstraints(6, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nameField = new JTextField();
        rootPanel.add(nameField, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        usernameField = new JTextField();
        rootPanel.add(usernameField, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        phoneField = new JTextField();
        phoneField.setText("");
        rootPanel.add(phoneField, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        agencyField = new JTextField();
        agencyField.setText("");
        rootPanel.add(agencyField, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        passwordField = new JPasswordField();
        rootPanel.add(passwordField, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}
