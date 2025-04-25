package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        String[] tokens = deliveryTime.split(":");

        this.id = id;
        this.deliveryTime = (Integer.parseInt(tokens[0])*60)+Integer.parseInt(tokens[1]);

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
