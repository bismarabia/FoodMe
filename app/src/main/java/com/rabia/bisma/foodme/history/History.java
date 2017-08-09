package com.rabia.bisma.foodme.history;



public class History {
    String purchaseName, purchaseQutantity, purchaseTotalPrice, purchaseDate;

    public History(String name, String quantity, String totalPrice, String date) {
        this.purchaseName = name;
        this.purchaseQutantity = quantity;
        this.purchaseTotalPrice = totalPrice;
        this.purchaseDate = date;
    }

    public String getPurchaseName() {
        return purchaseName;
    }
}
