package com.udacity.controller;

import com.udacity.view.InvoiceHeaderDialog;
import com.udacity.view.InvoiceLineDialog;
import com.udacity.view.SalesInvoiceFrame;
import com.udacity.model.InvoiceHeader;
import com.udacity.model.InvoiceHeaderTableModel;
import com.udacity.model.InvoiceLine;
import com.udacity.model.InvoiceLinesTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author M
 */
public class InvoiceListener implements ActionListener{
    private SalesInvoiceFrame invoiceFrame;
    private DateFormat df = new SimpleDateFormat("dd-MM-yyyy");


    public InvoiceListener(SalesInvoiceFrame invoiceFrame) {
        this.invoiceFrame = invoiceFrame;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        switch(e.getActionCommand()){
            case "CreateNewInvoice" :
                displayNewInvoiceDialog();
                break;
            case "DeleteInvoice" :
                deleteInvoice();
                break;
            case "CreateNewLine" :
                displayNewLineDialog();
                break;
            case "DeleteLine" :
                deleteLine();
                break;
            case "LoadFile" :
                loadFile();
                break;
            case "SaveFile" :
                saveData();
                break;
            case "createInvCancel":
                createInvCancel();
                break;
            case"createInvOk":
                createInvOk();
                break;
            case "createLineOk":
                createLineOk();
                break;
            case "createLineCancel":
                createLineCancel();
                break;
        }
    }
    
    private void loadFile() {
        JOptionPane.showMessageDialog(invoiceFrame, "Please select header file", "Attention" , JOptionPane.WARNING_MESSAGE);
        JFileChooser openFile = new JFileChooser();
        int result = openFile.showOpenDialog(invoiceFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File headerFile = openFile.getSelectedFile();
            
            try {
                FileReader headerFr = new FileReader(headerFile);
                BufferedReader headerBr = new BufferedReader(headerFr);
                String headerLine = null;
                
                while((headerLine = headerBr.readLine()) != null) {
                    String[] headerParts = headerLine.split(",");
                    String invNumStr = headerParts[0];
                    String invDateStr = headerParts[1];
                    String customerName = headerParts[2];
                    
                    int invNum = Integer.parseInt(invNumStr);
                    Date invDate = df.parse(invDateStr);
                    
                    InvoiceHeader inv = new InvoiceHeader(invNum, customerName, invDate);
                    invoiceFrame.getInvoicesList().add(inv);
                }
                
                            
            JOptionPane.showMessageDialog(invoiceFrame, "Please select lines file", "Attention" , JOptionPane.WARNING_MESSAGE);
            result = openFile.showOpenDialog(invoiceFrame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File linesFile = openFile.getSelectedFile();
                BufferedReader linesBr = new BufferedReader(new FileReader(linesFile));
                String linesLine = null;
                while((linesLine = linesBr.readLine()) != null) {
                    String[] lineParts = linesLine.split(",");
                    String invNumStr = lineParts[0];
                    String itemName = lineParts[1];
                    String itemPriceStr = lineParts[2];
                    String itemCountStr = lineParts[3];
                    
                    int invNum = Integer.parseInt(invNumStr);
                    double itemPrice = Double.parseDouble(itemPriceStr);
                    int itemCount = Integer.parseInt(itemCountStr);
                    InvoiceHeader header = findInvoiceByNum(invNum);
                    InvoiceLine invLine = new InvoiceLine(itemName, itemPrice, itemCount, header);
                    header.getLines().add(invLine);

                }
                invoiceFrame.setInvoiceHeaderTableModel(new InvoiceHeaderTableModel(invoiceFrame.getInvoicesList()));
                invoiceFrame.getInvoicesTable().setModel(invoiceFrame.getInvoiceHeaderTableModel());
                invoiceFrame.getInvoicesTable().validate();
                
            }
                System.out.println("com.udacity.project1.SalesInvoiceFrame.loadFile()");
            
            } catch(Exception exception) {
                exception.printStackTrace();
            }
        }
        displayInvoices();
    }
    
    private void saveData() {
        String headers = "";
        String lines = "";
        for(InvoiceHeader header : invoiceFrame.getInvoicesList()) {
            headers += header.getDataCsv();
            headers += "\n";
            for(InvoiceLine line : header.getLines()) {
                lines += line.getDataCsv();
                lines += "\n";
            }
        }
        JOptionPane.showMessageDialog(invoiceFrame, "Please select file for header data", "Attention" , JOptionPane.WARNING_MESSAGE);
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(invoiceFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File hFile = fileChooser.getSelectedFile();
            try {
            FileWriter hFW = new FileWriter(hFile);
            hFW.write(headers);
            hFW.flush();
            hFW.close();
            JOptionPane.showMessageDialog(invoiceFrame, "Please select file for lines data", "Attention" , JOptionPane.WARNING_MESSAGE);
            result = fileChooser.showSaveDialog(invoiceFrame);
            if(result == JFileChooser.APPROVE_OPTION) {
                File lFile = fileChooser.getSelectedFile();
                FileWriter lFW = new FileWriter(lFile);
                lFW.write(lines);
                lFW.flush();
                lFW.close();
            }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(invoiceFrame, "Error" + exception.getMessage(), "Error" , JOptionPane.ERROR_MESSAGE);
            }
        }
        JOptionPane.showMessageDialog(invoiceFrame, "Please select file for lines data", "Attention" , JOptionPane.WARNING_MESSAGE);
    }
    
    private InvoiceHeader findInvoiceByNum(int invNum) {
        InvoiceHeader header = null;
        for(InvoiceHeader inv : invoiceFrame.getInvoicesList()) {
            if(invNum == inv.getInvoiceNum()) {
                header = inv;
                break;
            }
        }
        
        return header;
    }

    

    private void displayNewInvoiceDialog() {
        invoiceFrame.setHeaderDialog(new InvoiceHeaderDialog(invoiceFrame));
        invoiceFrame.getHeaderDialog().setVisible(true);
        
    }

    private void createInvCancel() {
        invoiceFrame.getHeaderDialog().setVisible(false);
        invoiceFrame.getHeaderDialog().dispose();
        invoiceFrame.setHeaderDialog(null);
    }

    private void createInvOk() {
        String customerName = invoiceFrame.getHeaderDialog().getCustomerNameField().getText();
        String invDateStr = invoiceFrame.getHeaderDialog().getInvoiceDateField().getText();
        invoiceFrame.getHeaderDialog().setVisible(false);
        invoiceFrame.getHeaderDialog().dispose();
        invoiceFrame.setHeaderDialog(null);
        try {
            Date invDate = df.parse(invDateStr);
            int invNum = getNextInvoiceNum();
            InvoiceHeader invoiceHeader = new InvoiceHeader(invNum, customerName, invDate);
            invoiceFrame.getInvoicesList().add(invoiceHeader);
            invoiceFrame.getInvoiceHeaderTableModel().fireTableDataChanged();
        } catch (ParseException exception) {
            exception.printStackTrace();  
        }
        displayInvoices();
    }
    
    private int getNextInvoiceNum() {
        int max= 0;
        for(InvoiceHeader header : invoiceFrame.getInvoicesList()) {
            if (header.getInvoiceNum() > max) {
                max = header.getInvoiceNum();
            }
        }
        return max+ 1;
    }

    private void displayNewLineDialog() {
        invoiceFrame.setInvoiceLineDialog(new InvoiceLineDialog(invoiceFrame));
        invoiceFrame.getInvoiceLineDialog().setVisible(true);
    }

    private void createLineOk() {
        String itemName = invoiceFrame.getInvoiceLineDialog().getItemNameField().getText();
        String ItemCountStr = invoiceFrame.getInvoiceLineDialog().getItemCountField().getText();
        String itemPriceStr = invoiceFrame.getInvoiceLineDialog().getItemPriceField().getText();
        invoiceFrame.getInvoiceLineDialog().setVisible(false);
        invoiceFrame.getInvoiceLineDialog().dispose();
        invoiceFrame.setInvoiceLineDialog(null);
        int itemCount = Integer.parseInt(ItemCountStr);
        double itemPrice = Double.parseDouble(itemPriceStr);
        int headerIndex = invoiceFrame.getInvoicesTable().getSelectedRow();
        InvoiceHeader invoice = invoiceFrame.getInvoiceHeaderTableModel().getInvoicesList().get(headerIndex);
        InvoiceLine invoiceLine = new InvoiceLine(itemName, itemPrice, itemCount, invoice);
        invoice.addInvoiceLine(invoiceLine);
        invoiceFrame.getInvoiceLinesTableModel().fireTableDataChanged();
        invoiceFrame.getInvoiceHeaderTableModel().fireTableDataChanged();
        invoiceFrame.getInvoiceTotalLbl().setText("" + invoice.getInvoiceTotal());
        displayInvoices();
    }

    private void createLineCancel() {
        invoiceFrame.getInvoiceLineDialog().setVisible(false);
        invoiceFrame.getInvoiceLineDialog().dispose();
        invoiceFrame.setInvoiceLineDialog(null);
    }

    private void deleteInvoice() {
        int invIndex = invoiceFrame.getInvoicesTable().getSelectedRow();
        InvoiceHeader header = invoiceFrame.getInvoiceHeaderTableModel().getInvoicesList().get(invIndex);
        invoiceFrame.getInvoiceHeaderTableModel().getInvoicesList().remove(invIndex);
        invoiceFrame.getInvoiceHeaderTableModel().fireTableDataChanged();
        invoiceFrame.setInvoiceLinesTableModel(new InvoiceLinesTableModel(new ArrayList<InvoiceLine>()));
        invoiceFrame.getInvoiceItemsTable().setModel(invoiceFrame.getInvoiceLinesTableModel());
        invoiceFrame.getInvoiceLinesTableModel().fireTableDataChanged();
        invoiceFrame.getCustNameTF().setText("");
        invoiceFrame.getInvDateTF().setText("");
        invoiceFrame.getInvoiceNumberLbl().setText("");
        invoiceFrame.getInvoiceTotalLbl().setText("");
        displayInvoices();
    }

    private void deleteLine() {
        int lineIndex = invoiceFrame.getInvoiceItemsTable().getSelectedRow();
        InvoiceLine line = invoiceFrame.getInvoiceLinesTableModel().getInvoiceLines().get(lineIndex);
        invoiceFrame.getInvoiceLinesTableModel().getInvoiceLines().remove(lineIndex);
        invoiceFrame.getInvoiceLinesTableModel().fireTableDataChanged();
        invoiceFrame.getInvoiceHeaderTableModel().fireTableDataChanged();
        invoiceFrame.getInvoiceTotalLbl().setText("" + line.getHeader().getInvoiceTotal());
        displayInvoices();
    }
    
    private void displayInvoices() {
        System.out.println("*****************");
        for(InvoiceHeader header : invoiceFrame.getInvoicesList()) {
            System.out.println(header);
        }
        System.out.println("*****************");
    }
    
}
