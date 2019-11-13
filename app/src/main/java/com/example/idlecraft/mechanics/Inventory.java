package com.example.idlecraft.mechanics;

import java.util.ArrayList;
import java.util.Iterator;
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

    public Item getItemByName(String name) {
        if (name == null) {
            return null;
        }
        Iterator<Item> i = this.items.iterator();
        while (i.hasNext()) {
            Item item = i.next();
            if (item.getName() == name) {
                return item;
            }
        }
        return null;
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

    public boolean isCraftable(String itemName, int amount) {
        Item targetItem = this.getItemByName(itemName);
        Item req1 = this.getItemByName(targetItem.getReq1());
        Item req2 = this.getItemByName(targetItem.getReq2());
        Item req3 = this.getItemByName(targetItem.getReq3());

        // requirements should be sequential, so if there is no reqN there should be no more reqs
        if (req1 == null) {
            return true;
        }
        if (req1.getCount() < (targetItem.getReqAmount1() * amount)) {
            return false;
        }
        if (req2 == null) {
            return true;
        }
        if (req2.getCount() < (targetItem.getReqAmount2() * amount)) {
            return false;
        }
        if (req3 == null) {
            return true;
        }
        if (req3.getCount() < (targetItem.getReqAmount3() * amount)) {
            return false;
        }
        return true;
    }

    public void craftItem(String itemName, int amount) {
        if (this.isCraftable(itemName, amount)) {
            Item targetItem = this.getItemByName(itemName);
            Item req1 = this.getItemByName(targetItem.getReq1());
            Item req2 = this.getItemByName(targetItem.getReq2());
            Item req3 = this.getItemByName(targetItem.getReq3());
            if (req1 != null) {
                req1.decreaseCount(amount * targetItem.getReqAmount1());
            }
            if (req2 != null) {
                req2.decreaseCount(amount * targetItem.getReqAmount2());
            }
            if (req3 != null) {
                req3.decreaseCount(amount * targetItem.getReqAmount3());
            }
            targetItem.increaseCount(amount);
        }
    }
}
