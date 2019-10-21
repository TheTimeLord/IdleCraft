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

    public Inventory craftItem(Inventory inventory, Item craft_item, int amount) {
        boolean item_exists = false;
        for (int i = 0; i < inventory.getItems().size(); i++) { // Searches if the object to be crafted already exists in inventory to avoid duplicates
            if (inventory.getItems().get(i) == craft_item) { // If item exists, update it
                inventory.getItems().get(i).increaseCount(amount);
                item_exists = true;
                break;
            }
        }
        if (item_exists == false) { // If item doesn't exist in inventory create it and update it
            craft_item.increaseCount(amount);
            inventory.getItems().add(craft_item);

        }
        return inventory;
    }
}

