package com.example.idlecraft;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items;
    private int money;

    public Inventory() {
        this.items = new ArrayList<>();
        this.money = 0;
    }

    public List<Item> getItems() {
        return items;
    }

    public int getMoney() {
        return money;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void increaseMoney(int amount) {
        money += amount;
    }

    public void decreaseMoney(int amount) {
        money -= amount;
    }
}

