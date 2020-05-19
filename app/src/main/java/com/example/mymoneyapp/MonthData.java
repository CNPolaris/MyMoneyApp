package com.example.mymoneyapp;

public class MonthData {
    private int month;
    private float money;

    public MonthData(int month, float money) {
        this.month = month;
        this.money = money;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
