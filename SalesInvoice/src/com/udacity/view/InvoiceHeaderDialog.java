package com.udacity.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author M
 */
public class InvoiceHeaderDialog extends JDialog{
    private JTextField customerNameField;
    private JTextField InvoiceDateField;
    private JLabel customerNameLbl;
    private JLabel invoiceDateLbl;
    private JButton okBtn;
    private JButton cancelBtn;

    public JTextField getCustomerNameField() {
        return customerNameField;
    }

    public JTextField getInvoiceDateField() {
        return InvoiceDateField;
    }

    public InvoiceHeaderDialog(SalesInvoiceFrame invoiceFrame) {
        customerNameLbl = new JLabel("Customer Name:");
        customerNameField = new JTextField(20);
        invoiceDateLbl = new JLabel("Invoice Date:");
        InvoiceDateField = new JTextField(20);
        okBtn = new JButton("Ok");
        cancelBtn = new JButton("Cancel");
        okBtn.setActionCommand("createInvOk");
        cancelBtn.setActionCommand("createInvCancel");
        
        okBtn.addActionListener(invoiceFrame.getListener());
        cancelBtn.addActionListener(invoiceFrame.getListener());
        
        setLayout(new GridLayout(3, 2));
        
        add(invoiceDateLbl);
        add(InvoiceDateField);
        add(customerNameLbl);
        add(customerNameField);
        add(okBtn);
        add(cancelBtn);
        pack();
    }
    
    
}
