package com.example.idlecraft.mechanics;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemTest {

    private Item slingshot = new Item("slingshot", 5, 20, 1, true, null, null, null, 0, 0, 0, 5, 10);

    @Test
    public void increaseCountUnderMax() {
        int input = 5;
        int expected = 10;
        slingshot.increaseCount(input);
        int output = slingshot.getCount();
        assertEquals(expected, output);
    }

    @Test
    public void increaseCountToMax() {
        int input = 15;
        int expected = 20;
        slingshot.increaseCount(input);
        int output = slingshot.getCount();
        assertEquals(expected, output);
    }

    @Test
    public void increaseCountOverMax() {
        int input = 30;
        int expected = 20;
        slingshot.increaseCount(input);
        int output = slingshot.getCount();
        assertEquals(expected, output);
    }

    @Test
    public void increaseCountTwice() {
        int input1 = 5;
        int input2 = 5;
        int expected = 15;
        slingshot.increaseCount(input1);
        slingshot.increaseCount(input2);
        int output = slingshot.getCount();
        assertEquals(expected, output);
    }

    @Test
    public void decreaseCountUnderZero() {
        int input = 20;
        int expected = 0;
        slingshot.decreaseCount(input);
        int output = slingshot.getCount();
        assertEquals(expected, output);
    }

    @Test
    public void decreaseCountOverZero() {
        int input = 3;
        int expected = 2;
        slingshot.decreaseCount(input);
        int output = slingshot.getCount();
        assertEquals(expected, output);
    }

    @Test
    public void decreaseCountTwice() {
        int input1 = 3;
        int input2 = 1;
        int expected = 1;
        slingshot.decreaseCount(input1);
        slingshot.decreaseCount(input2);
        int output = slingshot.getCount();
        assertEquals(expected, output);
    }

    @Test
    public void increaseAndDecreaseCount() {
        int input1 = 10;
        int input2 = 5;
        int expected = 10;
        slingshot.increaseCount(input1);
        slingshot.decreaseCount(input2);
        int output = slingshot.getCount();
        assertEquals(expected, output);
    }

    @Test
    public void increaseMax() {
        int input = 5;
        int expected = 25;
        slingshot.increaseMax(input);
        int output = slingshot.getMax();
        assertEquals(expected, output);
    }

    @Test
    public void decreaseMax() {
        int input = 5;
        int expected = 15;
        slingshot.decreaseMax(input);
        int output = slingshot.getMax();
        assertEquals(expected, output);
    }
}