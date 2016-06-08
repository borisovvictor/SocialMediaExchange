package interfacelayer;

import datalayer.DataGateway;
import entity.Agency;
import entity.Client;
import entity.Performer;
import logiclayer.ExchangeService;
import servicelayer.Util;

import javax.swing.JOptionPane;

public class AuthFrame extends javax.swing.JFrame {

    public ExchangeService exchangeService = new ExchangeService();

    public AuthFrame() {
        initComponents();
    }


    private void initComponents() {

        usernameField = new javax.swing.JTextField();
        passwordField = new javax.swing.JTextField();
        usernameLabel = new javax.swing.JLabel();
        signinButton = new javax.swing.JButton();
        passwordLabel = new javax.swing.JLabel();
        signupButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Auth");
        setBounds(new java.awt.Rectangle(300, 100, 100, 100));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        usernameField.setText("");
        passwordField.setText("");
        passwordField.setToolTipText("");
        usernameLabel.setText("Username:");
        passwordLabel.setText("Password:");
        signinButton.setText("Sign in");
        signupButton.setText("Sign up");

        signinButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signinButtonActionPerformed(evt);
            }
        });

        signupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signupButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(signinButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(usernameLabel)
                                                        .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(passwordLabel))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(signupButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(usernameLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(passwordLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(signinButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(signupButton)
                                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }

    private void signinButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if(usernameField.getText().isEmpty() || passwordField.getText().isEmpty()){
            JOptionPane.showMessageDialog(rootPane, "Wrong username or password");
        }

        Object result = exchangeService.auth(usernameField.getText(), passwordField.getText());
        if(result instanceof Client){
            UserFrame userFrame = new UserFrame((Client)result);
            userFrame.setVisible(true);
            this.setVisible(false);
        }else if(result instanceof Performer){
            UserFrame userFrame = new UserFrame((Performer) result);
            userFrame.setVisible(true);
            this.setVisible(false);
        }else if(result instanceof Agency){
            UserFrame userFrame = new UserFrame((Agency) result);
            userFrame.setVisible(true);
            this.setVisible(false);
        }else if(result instanceof Util.Result){
            if(((Util.Result)result) == Util.Result.USER_NOT_EXIST){
                JOptionPane.showMessageDialog(rootPane, "User not exist");
            }else{

            }
        }else{
            System.out.println("Internal error");
        }

    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        DataGateway.close();
    }

    private void signupButtonActionPerformed(java.awt.event.ActionEvent evt) {
        CreateUser frame = new CreateUser();
        frame.setVisible(true);
    }

    private javax.swing.JButton signinButton;
    private javax.swing.JButton signupButton;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JTextField passwordField;
    private javax.swing.JTextField usernameField;
}
