package com.example.idlecraft.mechanics;

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

    public void addItem(Item item) { this.items.add(item); }

    public void setMoney(int money) {
        this.money = money;
    }

    public void increaseMoney(int amount) {
        money += amount;
    }

    public void decreaseMoney(int amount) {
        money -= amount;
    }

    public Inventory craftItem(Item craft_item, int amount) {
        boolean item_exists = false;
        for (int i = 0; i < this.items.size(); i++) { // Searches if the object to be crafted already exists in inventory to avoid duplicates
            if (this.items.get(i).getName().equals(craft_item.getName())) { // If item exists, update it
                this.items.get(i).increaseCount(amount);

                for (int j = 0; j < this.items.size(); j++) {
                    if (this.items.get(j).getReq1().equals(this.items.get(i).getReq1())) {
                        int multiplier = this.items.get(i).getReqAmount1();
                        this.items.get(j).decreaseCount(amount * multiplier);
                    }
                    else if (this.items.get(j).getReq2().equals(this.items.get(i).getReq2())) {
                        int multiplier = this.items.get(i).getReqAmount2();
                        this.items.get(j).decreaseCount(amount * multiplier);
                    }
                    else if (this.items.get(j).getReq3().equals(this.items.get(i).getReq3())) {
                        int multiplier = this.items.get(i).getReqAmount3();
                        this.items.get(j).decreaseCount(amount * multiplier);
                    }
                }
                item_exists = true;
                break;
            }
        }
        if (!item_exists) { // If item doesn't exist in inventory create it and update it
            craft_item.increaseCount(amount);
            this.items.add(craft_item);

        }
        return this;
    }
}