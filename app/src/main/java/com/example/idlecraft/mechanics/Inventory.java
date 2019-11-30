package com.example.idlecraft.mechanics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Inventory {
    private List<Item> items;
    private int money;

    // Class Constructor
    public Inventory() {
        this.items = new ArrayList<>();
        this.money = 0;

        // Create Gatherable Items in the Inventory
        Item sticks = new Item("sticks", 0, 10, 1, true, null, null, null, 0, 0, 0, 1, 5, 1, 1, 1, 1);
        Item rocks = new Item ("rocks",  0, 10, 1, true, null, null, null, 0, 0, 0, 1, 5, 1, 1, 1, 1);
        Item hide = new Item  ("hide",   0, 10, 1, true, null, null, null, 0, 0, 0, 2, 6, 1, 1, 1, 1);
        Item clay = new Item  ("clay",   0, 10, 1, true, null, null, null, 0, 0, 0, 2, 8, 1, 1, 1, 1);
        Item metal = new Item ("metal",  0, 10, 1, true, null, null, null, 0, 0, 0, 3, 10, 1, 1, 1, 1);
        Item oil = new Item   ("oil",    0, 10, 1, true, null, null, null, 0, 0, 0, 3, 10, 1, 1, 1, 1);
        Item paper = new Item ("paper",  0, 10, 1, true, null, null, null, 0, 0, 0, 1, 6, 1, 1, 1, 1);

        // Create Craftable Items in the Inventory
        Item spear = new Item ("spear",  0, 10, 1, true, "sticks", "rocks", null,     2,  1, 0, 5, 15, 1, 1, 1, 1);
        Item sword = new Item ("sword",  0, 10, 1, true, "sticks", "metal", null,     1,  3, 0, 8, 25, 1, 1, 1, 1);
        Item brick = new Item ("brick",  0, 10, 1, true, "clay",   null,    null,     5,  0, 0, 5, 20, 1, 1, 1, 1);
        Item house = new Item ("house",  0, 10, 1, true, "sticks", "brick", "hide",   10,  30, 8, 200, 1000, 1, 1, 1, 1);
        Item castle = new Item("castle", 0, 10, 1, true, "sticks", "rocks", "metal", 30, 60, 10, 3, 6, 1, 1, 1, 1);
        Item lamp = new Item  ("lamp",   0, 10, 1, true, "metal",  "oil",   null,     1,  1, 0, 3, 6, 1, 1, 1, 1);
        Item book = new Item  ("book",   0, 10, 1, true, "paper",  "hide",    null,     5,  2, 0, 3, 6, 1, 1, 1, 1);

        this.addItem(sticks);
        this.addItem(rocks);
        this.addItem(hide);
        this.addItem(clay);
        this.addItem(metal);
        this.addItem(oil);
        this.addItem(paper);
        this.addItem(spear);
        this.addItem(sword);
        this.addItem(brick);
        this.addItem(house);
        this.addItem(castle);
        this.addItem(lamp);
        this.addItem(book);
    }

    // Item accessors and mutators
    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }
    public void addItem(Item item) { this.items.add(item); }

    // Money accessors and mutators
    public int getMoney() { return money; }
    public void setMoney(int money) { this.money = money; }
    public void increaseMoney(int amount) { money += amount; }
    public void decreaseMoney(int amount) { money -= amount; }

    // getItemByName: Returns a given item in the player's inventory by searching for its name.
    public Item getItemByName(String name) {
        if (name == null) return null;
        Iterator<Item> i = this.items.iterator();
        while (i.hasNext()) {
            Item item = i.next();
            if (item.getName() == name) return item;
        }
        return null;
    }

    // howManyCanCraft: Determines how many of any item one can craft
    public int howManyCanCraft(Item item) {
        Item req1 = this.getItemByName(item.getReq1());
        Item req2 = this.getItemByName(item.getReq2());
        Item req3 = this.getItemByName(item.getReq3());

        if (req1 != null) {
            int numCraftableItems = req1.getCount() / item.getReqAmount1();

            if (req2 != null) {
                int num = req2.getCount() / item.getReqAmount2();
                if (num < numCraftableItems) numCraftableItems = num;
            }
            if (req3 != null) {
                int num = req3.getCount() / item.getReqAmount3();
                if (num < numCraftableItems) numCraftableItems = num;
            }
            return numCraftableItems;
        }
        return 0;
    }

    // isCraftable: Determines whether the given amount of items is craftable
    public boolean isCraftable(Item targetItem, int amount) {
        if (amount == 0) return false;
        Item req1 = this.getItemByName(targetItem.getReq1());
        Item req2 = this.getItemByName(targetItem.getReq2());
        Item req3 = this.getItemByName(targetItem.getReq3());

        if (req1 == null) return true;
        if (req1.getCount() < (targetItem.getReqAmount1() * amount)) return false;
        if (req2 == null) return true;
        if (req2.getCount() < (targetItem.getReqAmount2() * amount)) return false;
        if (req3 == null) return true;
        if (req3.getCount() < (targetItem.getReqAmount3() * amount)) return false;
        return true;
    }

    // craftItem: Crafts the given "amount" of "targetItem"s. It does so by subtracting the required
    // items from the player's inventory, and adding the number of crafted items to the player's
    // inventory.
    public void craftItem(Item targetItem, int amount) {
        if (this.isCraftable(targetItem, amount)) {
            Item req1 = this.getItemByName(targetItem.getReq1());
            Item req2 = this.getItemByName(targetItem.getReq2());
            Item req3 = this.getItemByName(targetItem.getReq3());

            if (req1 != null) req1.decreaseCount(amount * targetItem.getReqAmount1());
            if (req2 != null) req2.decreaseCount(amount * targetItem.getReqAmount2());
            if (req3 != null) req3.decreaseCount(amount * targetItem.getReqAmount3());

            targetItem.increaseCount(amount);
        }
    }
}