package mechanics;

public class Item {
    private String name;
    private int count;        // number of item owned
    private int max;          // max inventory space
    private int rate;         // gathering rate
    private boolean unlocked; // determines whether or not the item is available to the user
    private String req1;      // crafting requirement 1
    private String req2;      // crafting requirement 2
    private String req3;      // crafting requirement 3
    private int reqAmount1;   // crafting requirement amount 1
    private int reqAmount2;   // crafting requirement amount 2
    private int reqAmount3;   // crafting requirement amount 3
    private int sellValue;
    private int buyValue;
    private int buyMax;       // cost of upgrading max inventory space
    private int buyRate;      // cost of upgrading rate
    private int incInv;       // amount to increase max inventory by
    private int incRate;      // amount to increase rate by


    // Default Constructor
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
        this.sellValue = 0;
        this.buyValue = 0;
        this.buyMax = 0;
        this.buyRate = 0;
        this.incInv = 0;
        this.incRate = 0;
    }

    // Class Constructor
    public Item(String name, int count, int max, int rate, boolean unlocked, String req1,
                String req2, String req3, int reqAmount1, int reqAmount2, int reqAmount3,
                int sellValue, int buyValue, int buyMax, int buyRate, int incInv, int incRate) {
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
        this.sellValue = sellValue;
        this.buyValue = buyValue;
        this.buyMax = buyMax;
        this.buyRate = buyRate;
        this.incInv = incInv;
        this.incRate = incRate;
    }

    // Getters
    public String getName() { return name; }
    public int getCount() { return count; }
    public int getMax() { return max; }
    public int getRate() { return rate; }
    public String getReq1() { return req1; }
    public String getReq2() { return req2; }
    public String getReq3() { return req3; }
    public int getReqAmount1() { return reqAmount1; }
    public int getReqAmount2() { return reqAmount2; }
    public int getReqAmount3() { return reqAmount3; }
    public int getSellValue() { return sellValue; }
    public int getBuyValue() { return buyValue; }
    public int getBuyMax() { return buyMax; }
    public int getBuyRate() { return buyRate; }
    public int getIncInv() {return incInv; }
    public int getIncRate() {return incRate; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setCount(int count) { this.count = count; }
    public void setMax(int max) { this.max = max; }
    public void setRate(int rate) { this.rate = rate; }
    public void setUnlocked(boolean unlocked) { this.unlocked = unlocked; }
    public void setReq1(String req1) { this.req1 = req1; }
    public void setReq2(String req2) { this.req2 = req2; }
    public void setReq3(String req3) { this.req3 = req3; }
    public void setReqAmount1 (int reqAmount1) { this.reqAmount1 = reqAmount1; }
    public void setReqAmount2 (int reqAmount2) { this.reqAmount2 = reqAmount2; }
    public void setReqAmount3 (int reqAmount3) { this.reqAmount3 = reqAmount3; }
    public void setSellValue (int sellValue) { this.sellValue = sellValue; }
    public void setBuyValue (int buyValue) { this.buyValue = buyValue; }
    public void setBuyMax (int buyMax) { this.buyMax = buyMax; }
    public void setBuyRate (int buyRate) { this.buyRate = buyRate; }
    public void setIncInv (int incInv) {this.incInv = incInv; }
    public void setIncRate (int incRate) {this.incRate = incRate; }
    public boolean isUnlocked() { return unlocked; }

    public void increaseCount(int amount) {
        if ((count + amount) >= max) count = max;
        else if (amount < 0) throw new UnsupportedOperationException("Cannot modify count by a negative amount");
        else count += amount;
    }
    public void decreaseCount(int amount) {
        if ((count - amount) <= 0) count = 0;
        else if (amount < 0) throw new UnsupportedOperationException("Cannot modify count by a negative amount");
        else count -= amount;
    }

    public void increaseMax(int amount) {
        if (amount >= 0) max += amount;
        else if (amount < 0) throw new UnsupportedOperationException("Cannot modify max by a negative amount");
    }

    public void decreaseMax(int amount) {
        if (max - amount <= 0) max = 0;
        else if (amount < 0) throw new UnsupportedOperationException("Cannot modify max by a negative amount");
        else max -= amount;
    }
}