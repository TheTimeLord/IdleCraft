package tests;

import org.junit.Test;
import java.util.List;

import mechanics.Inventory;
import mechanics.Item;

import static org.junit.Assert.*;

public class InventoryTest {

    private Inventory testInventory = new Inventory();
    private Item testItem1 = new Item("slingshot", 5, 20, 1, true, "rubberBand", "pebble", null, 5, 10, 0, 5, 10, 1, 1, 1, 1);
    private Item testItem2 = new Item("rubberBand", 12, 20, 1, true, null, null, null, 0, 0, 0, 5, 10, 1, 1, 1, 1);
    private Item testItem3 = new Item("pebble", 20, 20, 1, true, null, null, null, 0, 0, 0, 5, 10, 1, 1, 1, 1);
    private Item testItem4 = new Item("superSlingshot", 0, 20, 1, true, "slingshot", null, null, 1000, 0, 0, 5, 10, 1, 1, 1, 1);
    private Item testItem5 = new Item("dirt", 20, 20, 1, true, "slingshot", null, null, 1000, 0, 0, 5, 10, 1, 1, 1, 1);

    @Test
    public void getItems() {
        String[] expected = {"sticks", "rocks", "hide", "clay", "metal", "oil", "paper", "spear",
                "sword", "brick", "house", "castle", "lamp", "book"};
        List<Item> inventoryItems = testInventory.getItems();
        String[] output = {inventoryItems.get(0).getName(), inventoryItems.get(1).getName(),
                inventoryItems.get(2).getName(), inventoryItems.get(3).getName(),
                inventoryItems.get(4).getName(), inventoryItems.get(5).getName(),
                inventoryItems.get(6).getName(), inventoryItems.get(7).getName(),
                inventoryItems.get(8).getName(), inventoryItems.get(9).getName(),
                inventoryItems.get(10).getName(), inventoryItems.get(11).getName(),
                inventoryItems.get(12).getName(), inventoryItems.get(13).getName()};
        assertArrayEquals(expected, output);
    }

    @Test
    public void addItem() {
        String expected = "slingshot";
        testInventory.addItem(testItem1);
        List<Item> inventoryItems = testInventory.getItems();
        String output = inventoryItems.get(inventoryItems.size()-1).getName();
        assertEquals(expected, output);
    }

    @Test
    public void getItemByNameWhereNameIsNull() {
        String input = null;
        Item output = testInventory.getItemByName(input);
        assertNull(output);
    }

    @Test
    public void getItemByNameWhereNameDoesNotExist() {
        String input = "hummus";
        Item output = testInventory.getItemByName(input);
        assertNull(output);
    }

    @Test
    public void getItemByNameWhereNameExists() {
        String input = "sword";
        String expected = "sword";
        String output = testInventory.getItemByName(input).getName();
        assertEquals(expected, output);
    }

    @Test
    public void howManyCanCraftWhereFirstReqIsNull() {
        Item input = testInventory.getItemByName("sticks");
        int expected = 0;
        int output = testInventory.howManyCanCraft(input);
        assertEquals(expected, output);
    }

    @Test
    public void howManyCanCraftWhereEnoughResources() {
        testInventory.addItem(testItem1);
        testInventory.addItem(testItem2);
        testInventory.addItem(testItem3);

        Item input = testItem1;
        int expected = 2;
        int output = testInventory.howManyCanCraft(input);
        assertEquals(expected, output);
    }

    @Test
    public void howManyCanCraftWhereNotEnoughResources() {
        Item input = testInventory.getItemByName("sword");
        int expected = 0;
        int output = testInventory.howManyCanCraft(input);
        assertEquals(expected, output);
    }

    @Test
    public void isCraftableWhereAmountIsZero() {
        testInventory.addItem(testItem1);

        Item input1 = testItem1;
        int input2 = 0;
        boolean expected = false;
        boolean output = testInventory.isCraftable(input1, input2);
        assertEquals(expected, output);
    }

    @Test
    public void isCraftableWhereItemDoesNotExistInInventory() {
        Item input1 = testItem5;
        int input2 = 1;
        boolean expected = true;
        boolean output = testInventory.isCraftable(input1, input2);
        assertEquals(expected, output);
    }

    @Test
    public void isCraftableWhereNotEnoughResources() {
        testInventory.addItem(testItem1);
        testInventory.addItem(testItem2);
        testInventory.addItem(testItem3);

        Item input1 = testItem1;
        int input2 = 50;
        boolean expected = false;
        boolean output = testInventory.isCraftable(input1, input2);
        assertEquals(expected, output);
    }

    @Test
    public void isCraftableWhereEnoughResources() {
        testInventory.addItem(testItem1);
        testInventory.addItem(testItem2);
        testInventory.addItem(testItem3);

        Item input1 = testItem1;
        int input2 = 1;
        boolean expected = true;
        boolean output = testInventory.isCraftable(input1, input2);
        assertEquals(expected, output);
    }

    @Test
    public void craftItemWhereItemDoesNotExistInInventory() {
        // Error should not be thrown
        Item input1 = testItem5;
        int input2 = 1;
        testInventory.craftItem(input1, input2);
    }

    @Test
    public void craftItemWhereItemDoesExistInInventoryAndNotEnoughResources() {
        testInventory.addItem(testItem1);
        testInventory.addItem(testItem4);

        Item input1 = testItem4;
        int input2 = 1;
        int expected = 0;
        testInventory.craftItem(input1, input2);
        int output = testInventory.getItemByName("superSlingshot").getCount();
        assertEquals(expected, output);
    }

    @Test
    public void craftItemWhereItemDoesExistInInventoryAndEnoughResources() {
        Inventory testInv = new Inventory();
        testInv.addItem(testItem1);
        testInv.addItem(testItem2);
        testInv.addItem(testItem3);

        Item input1 = testItem1;
        int input2 = 2;
        int expected = 7;
        testInv.craftItem(input1, input2);
        int output = testInv.getItemByName("slingshot").getCount();
        assertEquals(expected, output);
    }
}