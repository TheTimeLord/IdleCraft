package com.example.idlecraft;

public class Item {
    private String name;
    private int count; // number of item owned
    private int max;
    private int rate; // automation rate based on workers
    private boolean unlocked; // determines whether or not the item is available to the user

    public Item() {
        this.name = "Default";
        this.count = 0;
        this.max = 0;
        this.rate = 0;
        this.unlocked = true;
    }

    public Item(String name, int count, int max, int rate, boolean unlocked) {
        this.name = name;
        this.count = count;
        this.max = max;
        this.rate = rate;
        this.unlocked = unlocked;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public int getMax() {
        return max;
    }

    public int getRate() {
        return rate;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setMax(int max) { this.max = max; }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public void increaseCount(int amount) {
        if ((count + amount) >= max) {
            count = max;
        }
        else {
            count += amount;
        }
    }

    public void decreaseCount(int amount) {
        if ((count - amount) <= 0){
            count = 0;
        }
        else {
            count -= amount;
        }
    }

    public void increaseMax(int amount) {
        max += amount;
    }

    public void decreaseMax(int amount) {
        max -= amount;
    }
}
