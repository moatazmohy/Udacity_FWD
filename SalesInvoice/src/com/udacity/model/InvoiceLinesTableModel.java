package com.udacity.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author M
 */
public class InvoiceLinesTableModel extends AbstractTableModel{

    private List<InvoiceLine> invoicesLines;
    private DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    
    public InvoiceLinesTableModel(List<InvoiceLine> invoicesLines) {
        this.invoicesLines = invoicesLines;
    }

    public List<InvoiceLine> getInvoiceLines() {
        return invoicesLines;
    }

    
    @Override
    public int getRowCount() {
        return invoicesLines.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch(columnIndex) {
            case 0:
                return "Item Name";
            case 1:
                return "Item Price";
            case 2:
                return "Count";
            case 3:
                return "Line Total";
            default:
                return "";
        }
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0:
                return String.class;
            case 1:
                return Double.class;
            case 2:
                return Integer.class;
            case 3:
                return Double.class;
            default:
                return Object.class;
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceLine row = invoicesLines.get(rowIndex);
        
        switch(columnIndex) {
            case 0:
                return row.getItemName();
            case 1:
                return row.getItemPrice();
            case 2:
                return row.getItemCount();
            case 3:
                return row.getLineTotal();
            default:
                return Object.class;
        }
    }  
    
}
