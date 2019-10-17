package com.example.idlecraft;

public class Item {
    private String name;
    private int owned; // number of item owned
    private int max;
    private int rate; // automation rate based on workers
    private boolean unlocked; // determines whether or not the item is available to the user

    public Item() {
        this.name = "Default";
        this.owned = 0;
        this.max = 0;
        this.rate = 0;
        this.unlocked = true;
    }

    public Item(String name, int owned, int max, int rate, boolean unlocked) {
        this.name = name;
        this.owned = owned;
        this.max = max;
        this.rate = rate;
        this.unlocked = unlocked;
    }

    public String getName() {
        return name;
    }

    public int getOwned() {
        return owned;
    }

    public int getMax() {
        return owned;
    }

    public int getWorkers() {
        return owned;
    }

    public boolean getUnlocked() {
        return unlocked;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwned(int owned) {
        this.owned = owned;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setWorkers(int workers) {
        this.rate = workers;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }
}
