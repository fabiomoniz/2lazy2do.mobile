package com.example.a2lazy2do.BE;

public class Product {

    private String productId, productName;
    private Boolean izCompleted;

    public Product() {}

    public Product(String productId, String productName, Boolean izCompleted) {
        this.productId = productId;
        this.productName = productName;
        this.izCompleted = izCompleted;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Boolean getIzCompleted() {
        return izCompleted;
    }
}
