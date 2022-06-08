package com.udacity.controller;

import com.udacity.model.InvoiceHeader;
import com.udacity.model.InvoiceLine;
import com.udacity.model.InvoiceLinesTableModel;
import com.udacity.view.SalesInvoiceFrame;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author M
 */
public class InvoiceListSelectionListener implements ListSelectionListener{

    private SalesInvoiceFrame invoiceFrame;
    private DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    
    public InvoiceListSelectionListener(SalesInvoiceFrame invoiceFrame) {
        this.invoiceFrame = invoiceFrame;
    }
    

@Override
    public void valueChanged(ListSelectionEvent e) {
        System.out.println("");
        invoicesTableRowSelect();
    }

    private void invoicesTableRowSelect() {
        int selectedRowIndex = invoiceFrame.getInvoicesTable().getSelectedRow();
        if(selectedRowIndex >= 0) {
            InvoiceHeader row = invoiceFrame.getInvoiceHeaderTableModel().getInvoicesList().get(selectedRowIndex);
            invoiceFrame.getCustNameTF().setText(row.getCustomerName());
            invoiceFrame.getInvDateTF().setText(df.format(row.getInvoiceDate()));
            invoiceFrame.getInvoiceNumberLbl().setText("" + row.getInvoiceNum());
            invoiceFrame.getInvoiceTotalLbl().setText("" + row.getInvoiceTotal());
            ArrayList<InvoiceLine> lines = row.getLines();
            invoiceFrame.setInvoiceLinesTableModel(new InvoiceLinesTableModel(lines));
            invoiceFrame.getInvoiceItemsTable().setModel(invoiceFrame.getInvoiceLinesTableModel());
            invoiceFrame.getInvoiceLinesTableModel().fireTableDataChanged();
        }
    }
    
}
