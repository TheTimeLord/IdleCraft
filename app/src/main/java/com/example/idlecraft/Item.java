package com.example.idlecraft;

public class Item {
    private String name;
    private int count; // number of item owned
    private int max;
    private int rate; // automation rate based on workers
    private boolean unlocked; // determines whether or not the item is available to the user
    private String req1; // crafting requirement
    private String req2;
    private String req3;
    private int reqAmount1;
    private int reqAmount2;
    private int reqAmount3;


    public Item() {
        this.name = "Default";
        this.count = 0;
        this.max = 0;
        this.rate = 0;
        this.unlocked = true;
        this.req1 = null;
        this.req2 = null;
        this.req3 = null;
        this.reqAmount1 = 0;
        this.reqAmount2 = 0;
        this.reqAmount3 = 0;
    }

    public Item(String name, int count, int max, int rate, boolean unlocked, String req1,
                String req2, String req3, int reqAmount1, int reqAmount2, int reqAmount3) {
        this.name = name;
        this.count = count;
        this.max = max;
        this.rate = rate;
        this.unlocked = unlocked;
        this.req1 = req1;
        this.req2 = req2;
        this.req3 = req3;
        this.reqAmount1 = reqAmount1;
        this.reqAmount2 = reqAmount2;
        this.reqAmount3 = reqAmount3;
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

    public String getReq1() { return req1; }

    public String getReq2() { return req2; }

    public String getReq3() { return req3; }

    public int getReqAmount1() { return reqAmount1; }

    public int getReqAmount2() { return reqAmount2; }

    public int getReqAmount3() { return reqAmount3; }

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

    public void setReq1(String req1) { this.req1 = req1; }

    public void setReq2(String req2) { this.req2 = req2; }

    public void setReq3(String req3) { this.req3 = req3; }

    public void setReqAmount1 (int reqAmount1) { this.reqAmount1 = reqAmount1; }

    public void setReqAmount2 (int reqAmount2) { this.reqAmount2 = reqAmount2; }

    public void setReqAmount3 (int reqAmount3) { this.reqAmount3 = reqAmount3; }



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
