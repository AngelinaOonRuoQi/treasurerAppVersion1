package com.example.treasurerappversion1.model;

public class Student {
    private String name;
    private String paidStatus;

    public void setName(String name) {
        this.name = name;
    }

    public String getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(String paidStatus) {
        this.paidStatus = paidStatus;
    }


    public Student() {

    }

    public String getName() {
        return name;
    }
}
