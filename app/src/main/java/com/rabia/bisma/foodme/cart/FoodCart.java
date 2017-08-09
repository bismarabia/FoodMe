package com.rabia.bisma.foodme.cart;


public class FoodCart {

    String name, price, quantity;
    private int picId;
    OrderDetails orderDetails;

    public FoodCart(String name, String price, String quantity, OrderDetails orderDetails) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.orderDetails = orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    double getPriceTimesQuantity() {
        return Double.parseDouble(price.substring(0, price.length() - 2))
                * Double.parseDouble(quantity);
    }

    void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    String getQuantity() {
        return quantity;
    }
}
