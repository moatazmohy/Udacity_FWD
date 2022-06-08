package com.udacity.model;


public class InvoiceLine {
    private String itemName;
    private double itemPrice;
    private int itemCount;
    private InvoiceHeader header;

    public InvoiceLine(String itemName, double itemPrice, int itemCount, InvoiceHeader header) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCount = itemCount;
        this.header = header;
    }
    
    
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public InvoiceHeader getHeader() {
        return header;
    }

    public void setHeader(InvoiceHeader header) {
        this.header = header;
    }
    
    public double getLineTotal() {
        return itemPrice * itemCount;
    }

    @Override
    public String toString() {
        return "InvoiceLine{" + "itemName=" + itemName + ", itemPrice=" + itemPrice + ", itemCount=" + itemCount + '}';
    }
    
    public String getDataCsv() {
        return ""+ getHeader().getInvoiceNum() + "," +getItemName() + "," +getItemPrice() + "," +getItemCount();
    }
}
