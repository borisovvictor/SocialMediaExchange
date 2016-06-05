package interfacelayer;

import entity.*;
import servicelayer.ServiceLayer;
import servicelayer.Util;
import sun.misc.Perf;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * Created by Victor on 25.05.2016.
 */
public class UserFrame extends JFrame {

    ServiceLayer serviceLayer = new ServiceLayer();
    /*Client m_client;
    Performer m_performer;
    Agency m_agency;*/
    Object m_user;

    public void init() {
        setContentPane(rootPanel);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

        refreshOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshOrders();
            }
        });

        refreshOffersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshOffers();
            }
        });

        addOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int requestID = Integer.parseInt(
                        requestsTable.getValueAt(requestsTable.getSelectedRow(), 0).toString());
                int offerID = Integer.parseInt(
                        offersTable.getValueAt(offersTable.getSelectedRow(), 0).toString());
                serviceLayer.createOrder(offerID);
                serviceLayer.removeRequest(requestID);
                refreshData();
            }
        });

        payForOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int orderID = Integer.parseInt(
                        ordersTable.getValueAt(ordersTable.getSelectedRow(), 0).toString());
                serviceLayer.payForOrder(orderID);
                refreshOrders();
            }
        });

        payToPerformerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int orderID = Integer.parseInt(
                        ordersTable.getValueAt(ordersTable.getSelectedRow(), 0).toString());
                serviceLayer.performOrderPayment(orderID);
                refreshOrders();
            }
        });

        approveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int orderID = Integer.parseInt(
                        ordersTable.getValueAt(ordersTable.getSelectedRow(), 0).toString());
                serviceLayer.approveOrderConditions(orderID);
                refreshOrders();
            }
        });

        rejectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int orderID = Integer.parseInt(
                        ordersTable.getValueAt(ordersTable.getSelectedRow(), 0).toString());
                serviceLayer.rejectOrderConditions(orderID);
                refreshOrders();
            }
        });

        performButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int orderID = Integer.parseInt(
                        ordersTable.getValueAt(ordersTable.getSelectedRow(), 0).toString());
                serviceLayer.performOrder(orderID);
                refreshOrders();
            }
        });

        confirmOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int orderID = Integer.parseInt(
                        ordersTable.getValueAt(ordersTable.getSelectedRow(), 0).toString());
                serviceLayer.confirmOrder(orderID);
                refreshOrders();
            }
        });

        confirmPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int orderID = Integer.parseInt(
                        ordersTable.getValueAt(ordersTable.getSelectedRow(), 0).toString());
                serviceLayer.confirmOrderPayment(orderID);
                refreshOrders();
            }
        });

        deleteOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int orderID = Integer.parseInt(
                        ordersTable.getValueAt(ordersTable.getSelectedRow(), 0).toString());
                serviceLayer.removeOrder(orderID);
                refreshOrders();
            }
        });

        addOfferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateOfferFrame frame = new CreateOfferFrame(CreateOfferFrame.FrameType.OFFER_TYPE);
                frame.setVisible(true);
                refreshOffers();
            }
        });

        deleteOfferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int offerID = Integer.parseInt(
                        offersTable.getValueAt(offersTable.getSelectedRow(), 0).toString());
                serviceLayer.removeOffer(offerID);
                refreshOffers();
            }
        });

        setConditionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int orderID = Integer.parseInt(
                        ordersTable.getValueAt(ordersTable.getSelectedRow(), 0).toString());
                serviceLayer.setConditions(orderID, conditionText.getText());
                conditionText.setText("");
                refreshOrders();
            }
        });

        refreshRequestsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshRequests();
            }
        });

        addRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateOfferFrame frame = new CreateOfferFrame(CreateOfferFrame.FrameType.REQUEST_TYPE);
                frame.setVisible(true);
                refreshOffers();
            }
        });
        removeRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int requestID = Integer.parseInt(
                        requestsTable.getValueAt(requestsTable.getSelectedRow(), 0).toString());
                serviceLayer.removeRequest(requestID);
                refreshRequests();
            }
        });
        addOfferToRequestButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int requestID = Integer.parseInt(
                        requestsTable.getValueAt(requestsTable.getSelectedRow(), 0).toString());
                int offerID = Integer.parseInt(
                        offersTable.getValueAt(offersTable.getSelectedRow(), 0).toString());
                serviceLayer.addOfferToRequest(requestID, offerID);
                refreshRequests();
            }
        });

        requestsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                refreshOffers();
            }
        });

        findOffersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findOffers();
            }
        });

        refreshData();
    }

    public UserFrame(Client client) {
        super("Client panel");
        this.m_user = client;
        init();

        userNameValue.setText(client.getUsername());
        nameValue.setText(client.getName());
        phoneValue.setText(client.getPhoneNumber());

        //offersTableLabel.setVisible(false);
        //offersTable.setVisible(false);
        addOfferButton.setVisible(false);
        //refreshOffersButton.setVisible(false);
        //offerToolPanel.setVisible(false);
        deleteOfferButton.setVisible(false);
        payToPerformerButton.setVisible(false);
        approveButton.setVisible(false);
        rejectButton.setVisible(false);
        performButton.setVisible(false);
        confirmPaymentButton.setVisible(false);
        deleteOfferButton.setVisible(false);
        addOfferToRequestButton1.setVisible(false);
        findOffersPanel.setVisible(false);
    }

    public UserFrame(Performer performer) {
        super("Performer panel");
        this.m_user = performer;
        init();

        userNameValue.setText(performer.getUsername());
        nameValue.setText(performer.getName());
        phoneValue.setText(performer.getPhoneNumber());

        addOrderButton.setVisible(false);
        deleteOrderButton.setVisible(false);
        payForOrderButton.setVisible(false);
        payToPerformerButton.setVisible(false);
        setConditionsButton.setVisible(false);
        conditionText.setVisible(false);
        conditionsPanel.setVisible(false);
        confirmOrderButton.setVisible(false);
        confirmPaymentButton.setVisible(false);
        //offersTable.setVisible(false);
        //offersTableLabel.setVisible(false);
        //offerToolPanel.setVisible(false);
        findOffersPanel.setVisible(false);
        //removeRequestButton.setVisible(false);
        requestsTable.setVisible(false);
        requestsLabel.setVisible(false);
        requestsToolpanel.setVisible(false);
    }

    public UserFrame(Agency agency) {
        super("Agency panel");
        this.m_user = agency;
        init();

        userNameValue.setText(agency.getUsername());
        nameValue.setText(agency.getName());

        phoneLabel.setVisible(false);
        phoneValue.setVisible(false);
        //offersTableLabel.setVisible(false);
        addOrderButton.setVisible(false);
        deleteOrderButton.setVisible(false);
        //offersTable.setVisible(false);
        addOfferButton.setVisible(false);
        //refreshOffersButton.setVisible(false);
        //offerToolPanel.setVisible(false);
        addRequestButton.setVisible(false);
        removeRequestButton.setVisible(false);
        payForOrderButton.setVisible(false);
        setConditionsButton.setVisible(false);
        conditionText.setVisible(false);
        conditionsPanel.setVisible(false);
        approveButton.setVisible(false);
        rejectButton.setVisible(false);
        performButton.setVisible(false);
        confirmOrderButton.setVisible(false);
        deleteOfferButton.setVisible(false);
        addOfferButton.setVisible(false);
        removeRequestButton.setVisible(false);
    }

    private void refreshOrders() {
        List<Order> orders = new ArrayList<>();
        // test
        //orders.add(new Order(1, 2, "123"));
        //orders.add(new Order(1, 2, "123"));
        orders = serviceLayer.getOrdersByCurrentUser();
        ordersTable.setModel(new OrderTableModel(orders));
    }

    private void refreshOffers() {
        if (m_user instanceof Performer) {
            List<Offer> offers = new ArrayList<>();
            // test
            //offers.add(new Offer(150.1, 7, "decr", "insta"));
            //offers.add(new Offer(110.1, 5, "decr1", "fb"));
            offers = serviceLayer.getOffersByCurrentUser();
            offersTable.setModel(new OfferTableModel(offers));
        } else if (m_user instanceof Client) {
            List<Offer> offers = new ArrayList<>();
            if (requestsTable.getSelectedRow() >= 0) {
                int requestID = Integer.parseInt(
                        requestsTable.getValueAt(requestsTable.getSelectedRow(), 0).toString());
                offers = serviceLayer.getOffersByRequestId(requestID);
            }
            offersTable.setModel(new OfferTableModel(offers));
        }
    }


    private void refreshRequests() {
        if (m_user instanceof Agency || m_user instanceof Client) {
            List<Request> requests = new ArrayList<>();
            // test
            //offers.add(new Offer(150.1, 7, "decr", "insta"));
            //offers.add(new Offer(110.1, 5, "decr1", "fb"));
            requests = serviceLayer.getRequestsByCurrentUser();
            requestsTable.setModel(new RequestTableModel(requests));
        }
    }

    private void refreshData() {
        refreshOrders();
        refreshRequests();
        refreshOffers();
    }

    private void findOffers() {
        if (keyWordsField.getText().isEmpty()
                || priceField.getText().isEmpty()
                || socialMediaField.getText().isEmpty()
                || periodField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Empty fields");
            return;
        }


        Map<Integer, List<Offer>> offers = serviceLayer.findOffers(Double.parseDouble(priceField.getText()),
                Integer.parseInt(periodField.getText()), socialMediaField.getText(), keyWordsField.getText());

        List<Offer> offersList = new ArrayList<>();
        for (List<Offer> var : offers.values()) {
            offersList.addAll(var);
        }

        offersTable.setModel(new OfferTableModel(offersList));
    }

    private JLabel nameLabel;
    private JLabel userNameLabel;
    private JLabel userNameValue;
    private JLabel nameValue;
    private JLabel phoneLabel;
    private JLabel phoneValue;
    private JLabel ordersTableLabel;
    private JLabel offersTableLabel;
    private JTable ordersTable;
    private JButton addOrderButton;
    private JButton deleteOrderButton;
    private JButton refreshOrdersButton;
    private JTable offersTable;
    private JButton addOfferButton;
    private JButton refreshOffersButton;
    private JPanel orderToolPanel;
    private JPanel offerToolPanel;
    private JButton payForOrderButton;
    private JButton payToPerformerButton;
    private JButton setConditionsButton;
    private JTextField conditionText;
    private JPanel conditionsPanel;
    private JButton approveButton;
    private JButton rejectButton;
    private JButton performButton;
    private JButton confirmOrderButton;
    private JButton confirmPaymentButton;
    private JButton deleteOfferButton;
    private JPanel rootPanel;
    private JButton refreshRequestsButton;
    private JLabel requestsLabel;
    private JTable requestsTable;
    private JButton addRequestButton;
    private JButton removeRequestButton;
    private JButton addOfferToRequestButton1;
    private JPanel requestsToolpanel;
    private JPanel findOffersPanel;
    private JTextField keyWordsField;
    private JButton findOffersButton;
    private JLabel keyWordsLabel;
    private JLabel priceLabel;
    private JLabel periodLabel;
    private JLabel socialMediaLabel;
    private JTextField priceField;
    private JTextField periodField;
    private JTextField socialMediaField;

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
        rootPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(14, 3, new Insets(0, 0, 0, 0), -1, -1));
        userNameLabel = new JLabel();
        userNameLabel.setText("Username");
        rootPanel.add(userNameLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        rootPanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        userNameValue = new JLabel();
        userNameValue.setText("");
        rootPanel.add(userNameValue, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nameLabel = new JLabel();
        nameLabel.setText("Name");
        rootPanel.add(nameLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nameValue = new JLabel();
        nameValue.setText("");
        rootPanel.add(nameValue, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        phoneLabel = new JLabel();
        phoneLabel.setText("Phone");
        rootPanel.add(phoneLabel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        phoneValue = new JLabel();
        phoneValue.setText("");
        rootPanel.add(phoneValue, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ordersTableLabel = new JLabel();
        ordersTableLabel.setText("Orders");
        rootPanel.add(ordersTableLabel, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        offersTableLabel = new JLabel();
        offersTableLabel.setText("Offers");
        rootPanel.add(offersTableLabel, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ordersTable = new JTable();
        rootPanel.add(ordersTable, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        orderToolPanel = new JPanel();
        orderToolPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 10, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(orderToolPanel, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        orderToolPanel.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(0, 9, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        deleteOrderButton = new JButton();
        deleteOrderButton.setText("-");
        orderToolPanel.add(deleteOrderButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        payForOrderButton = new JButton();
        payForOrderButton.setText("Pay For");
        orderToolPanel.add(payForOrderButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        payToPerformerButton = new JButton();
        payToPerformerButton.setText("Pay To");
        orderToolPanel.add(payToPerformerButton, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        approveButton = new JButton();
        approveButton.setText("Approve");
        orderToolPanel.add(approveButton, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        rejectButton = new JButton();
        rejectButton.setText("Reject");
        orderToolPanel.add(rejectButton, new com.intellij.uiDesigner.core.GridConstraints(0, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        performButton = new JButton();
        performButton.setText("Perform");
        orderToolPanel.add(performButton, new com.intellij.uiDesigner.core.GridConstraints(0, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        confirmOrderButton = new JButton();
        confirmOrderButton.setText("Confirm Order");
        orderToolPanel.add(confirmOrderButton, new com.intellij.uiDesigner.core.GridConstraints(0, 7, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        confirmPaymentButton = new JButton();
        confirmPaymentButton.setText("Confirm Payment");
        orderToolPanel.add(confirmPaymentButton, new com.intellij.uiDesigner.core.GridConstraints(0, 8, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        offersTable = new JTable();
        rootPanel.add(offersTable, new com.intellij.uiDesigner.core.GridConstraints(9, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        offerToolPanel = new JPanel();
        offerToolPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(offerToolPanel, new com.intellij.uiDesigner.core.GridConstraints(10, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addOfferButton = new JButton();
        addOfferButton.setText("+");
        offerToolPanel.add(addOfferButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        offerToolPanel.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        deleteOfferButton = new JButton();
        deleteOfferButton.setText("-");
        offerToolPanel.add(deleteOfferButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addOrderButton = new JButton();
        addOrderButton.setText("Create order");
        offerToolPanel.add(addOrderButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        refreshOrdersButton = new JButton();
        refreshOrdersButton.setText("Refresh");
        rootPanel.add(refreshOrdersButton, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        refreshOffersButton = new JButton();
        refreshOffersButton.setText("Refresh");
        rootPanel.add(refreshOffersButton, new com.intellij.uiDesigner.core.GridConstraints(8, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        conditionsPanel = new JPanel();
        conditionsPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(conditionsPanel, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        setConditionsButton = new JButton();
        setConditionsButton.setText("Set Conditions");
        conditionsPanel.add(setConditionsButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        conditionText = new JTextField();
        conditionsPanel.add(conditionText, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        requestsToolpanel = new JPanel();
        requestsToolpanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(requestsToolpanel, new com.intellij.uiDesigner.core.GridConstraints(13, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addRequestButton = new JButton();
        addRequestButton.setText("+");
        requestsToolpanel.add(addRequestButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        requestsToolpanel.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        removeRequestButton = new JButton();
        removeRequestButton.setText("-");
        requestsToolpanel.add(removeRequestButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addOfferToRequestButton1 = new JButton();
        addOfferToRequestButton1.setText("Add offer");
        requestsToolpanel.add(addOfferToRequestButton1, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        requestsLabel = new JLabel();
        requestsLabel.setText("Requests");
        rootPanel.add(requestsLabel, new com.intellij.uiDesigner.core.GridConstraints(11, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        refreshRequestsButton = new JButton();
        refreshRequestsButton.setText("Refresh");
        rootPanel.add(refreshRequestsButton, new com.intellij.uiDesigner.core.GridConstraints(11, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        requestsTable = new JTable();
        rootPanel.add(requestsTable, new com.intellij.uiDesigner.core.GridConstraints(12, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        findOffersPanel = new JPanel();
        findOffersPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 3, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(findOffersPanel, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        keyWordsLabel = new JLabel();
        keyWordsLabel.setText("Key words");
        findOffersPanel.add(keyWordsLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer5 = new com.intellij.uiDesigner.core.Spacer();
        findOffersPanel.add(spacer5, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        keyWordsField = new JTextField();
        findOffersPanel.add(keyWordsField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        priceLabel = new JLabel();
        priceLabel.setText("Price (max)");
        findOffersPanel.add(priceLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        periodLabel = new JLabel();
        periodLabel.setText("Period (min)");
        findOffersPanel.add(periodLabel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        socialMediaLabel = new JLabel();
        socialMediaLabel.setText("Social media");
        findOffersPanel.add(socialMediaLabel, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer6 = new com.intellij.uiDesigner.core.Spacer();
        findOffersPanel.add(spacer6, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        findOffersButton = new JButton();
        findOffersButton.setText("Find offers");
        findOffersPanel.add(findOffersButton, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        priceField = new JTextField();
        findOffersPanel.add(priceField, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        periodField = new JTextField();
        findOffersPanel.add(periodField, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        socialMediaField = new JTextField();
        findOffersPanel.add(socialMediaField, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}
