package interfacelayer;

import entity.Offer;
import entity.Request;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RequestTableModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();

    private List<Request> requests;

    public RequestTableModel(List<Request> requests) {
        this.requests = requests;
    }

    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }

    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    public int getColumnCount() {
        return 7;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "ID";
            //case 1:
            //    return "Performer ID";
            case 1:
                return "Price";
            case 2:
                return "Period";
            case 3:
                return "Description";
            case 4:
                return "Social media";
            case 5:
                return "Status";
            case 6:
                return "Offers";
        }
        return "";
    }

    public int getRowCount() {
        return requests.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Request request = requests.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return request.getID();
            //case 1:
            //    return request.getClientID();
            case 1:
                return request.getPrice();
            case 2:
                return request.getPeriod();
            case 3:
                return request.getDescription();
            case 4:
                return request.getSocialMedia();
            case 5:
                return  request.getStatus();
            case 6:
                return request.getOffersIds();
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