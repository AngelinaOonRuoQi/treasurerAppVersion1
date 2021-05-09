package com.example.treasurerappversion1.model;

public class Course {
    private String id;
    private String name;
    private double price;
    private double quantity;
    private double total;

    public Course(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    private double getTotal(){
        return this.price * this.quantity;
    }
}