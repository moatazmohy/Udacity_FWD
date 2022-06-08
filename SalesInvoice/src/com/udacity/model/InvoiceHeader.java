package com.udacity.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InvoiceHeader {
    private int invoiceNum;
    private String customerName;
    private Date invoiceDate;
    private ArrayList<InvoiceLine> lines;
    private DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

    public InvoiceHeader(int invoiceNum, String customerName, Date invoiceDate) {
        this.invoiceNum = invoiceNum;
        this.customerName = customerName;
        this.invoiceDate = invoiceDate;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public int getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(int invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public ArrayList<InvoiceLine> getLines() {
        if (lines == null) {
            lines = new ArrayList<>();
        }
        return lines;
    }

    public void setLines(ArrayList<InvoiceLine> lines) {
        this.lines = lines;
    }
    
    public double getInvoiceTotal() {
        double total = 0.0;
        for (InvoiceLine line : getLines()) {
            total += line.getLineTotal();
        }
        return total;
    }
    
    public void addInvoiceLine(InvoiceLine line) {
        getLines().add(line);
    }

    @Override
    public String toString() {
        String str = "InvoiceHeader{" + "invoiceNum=" + invoiceNum + ", customerName=" + customerName + ", invoiceDate=" + invoiceDate + '}';
        for(InvoiceLine line : getLines()) {
            str += "\n\t" + line;
        }
        return str;
    }

      public String getDataCsv() {
        return ""+ getInvoiceNum() + "," +df.format(getInvoiceDate())+ "," +getCustomerName();
    }

    
}
