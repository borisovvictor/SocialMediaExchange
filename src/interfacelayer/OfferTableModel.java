package interfacelayer;

import entity.Offer;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OfferTableModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();

    private List<Offer> offers;

    public OfferTableModel(List<Offer> offers) {
        this.offers = offers;
    }

    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }

    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    public int getColumnCount() {
        return 6;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "ID";
            case 1:
                return "Performer ID";
            case 2:
                return "Price";
            case 3:
                return "Period";
            case 4:
                return "Description";
            case 5:
                return "Social media";
        }
        return "";
    }

    public int getRowCount() {
        return offers.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Offer offer = offers.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return offer.getID();
            case 1:
                return offer.getPerformerID();
            case 2:
                return offer.getPrice();
            case 3:
                return offer.getPeriod();
            case 4:
                return offer.getDescription();
            case 5:
                return offer.getSocialMedia();
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