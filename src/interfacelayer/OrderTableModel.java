package interfacelayer;

import entity.Order;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderTableModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();

    private List<Order> orders;

    public OrderTableModel(List<Order> orders) {
        this.orders = orders;
    }

    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }

    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    public int getColumnCount() {
        return 5;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "ID";
            case 1:
                return "Client ID";
            case 2:
                return "Offer ID";
            case 3:
                return "Status";
            case 4:
                return "Conditions";
        }
        return "";
    }

    public int getRowCount() {
        return orders.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Order order = orders.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return order.getID();
            case 1:
                return order.getClientID();
            case 2:
                return order.getOfferID();
            case 3:
                return order.getOrderStatus().toString();
            case 4:
                return order.getSpecialConditions();
        }
        return "";
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void removeTableModelListener(TableModelListener listener) {
        listeners.remove(listener);
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {

    }

}