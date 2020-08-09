package com.bksoftwarevn.itstudent.model;

public class BillHasProduct {

    private int productId;

    private int billId;

    private int quantity; // số lượng sản phẩm trong hóa đơn

    private double productPrice; // giá sp tại thời điểm mua

    public BillHasProduct() {
    }

    public BillHasProduct(int productId, int billId, int quantity, double productPrice) {
        this.productId = productId;
        this.billId = billId;
        this.quantity = quantity;
        this.productPrice = productPrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getProducPrice() {
        return productPrice;
    }

    public void setProducPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public String toString() {
        return "BillHasProduct{" +
                "productId=" + productId +
                ", billId=" + billId +
                ", quantity=" + quantity +
                ", producPrice=" + productPrice +
                '}';
    }
}
