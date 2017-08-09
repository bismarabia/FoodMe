package com.rabia.bisma.foodme.cart;

public class OrderDetails {

    boolean m_checked, k_checked;
    String drink;

    public OrderDetails(boolean m_checked, boolean k_checked, String drink) {
        this.m_checked = m_checked;
        this.k_checked = k_checked;
        this.drink = drink;
    }

    public boolean isM_checked() {
        return m_checked;
    }

    public boolean isK_checked() {
        return k_checked;
    }

    public String getDrink() {
        return drink;
    }
}
