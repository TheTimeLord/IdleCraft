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
            if (inventory.getItems().get(i).getName().equals(craft_item.getName())) { // If item exists, update it
                inventory.getItems().get(i).increaseCount(amount);

                for (int j = 0; j < inventory.getItems().size(); j++) {
                    if (inventory.getItems().get(j).getReq1().equals(inventory.getItems().get(i).getReq1())) {
                        int multiplier = inventory.getItems().get(i).getReqAmount1();
                        inventory.getItems().get(j).decreaseCount(amount * multiplier);
                    }
                    else if (inventory.getItems().get(j).getReq2().equals(inventory.getItems().get(i).getReq2())) {
                        int multiplier = inventory.getItems().get(i).getReqAmount2();
                        inventory.getItems().get(j).decreaseCount(amount * multiplier);
                    }
                    else if (inventory.getItems().get(j).getReq3().equals(inventory.getItems().get(i).getReq3())) {
                        int multiplier = inventory.getItems().get(i).getReqAmount3();
                        inventory.getItems().get(j).decreaseCount(amount * multiplier);
                    }
                }
                item_exists = true;
                break;
            }
        }
        if (!item_exists) { // If item doesn't exist in inventory create it and update it
            craft_item.increaseCount(amount);
            inventory.getItems().add(craft_item);

        }
        return inventory;
    }
}