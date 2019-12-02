package tests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import mechanics.Item;

import static org.junit.Assert.*;

public class ItemTest {

    private Item testItem = new Item("slingshot", 5, 20, 1, true, null, null, null, 0, 0, 0, 5, 10, 1, 1, 1, 1);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void increaseCountUnderMax() {
        int input = 5;
        int expected = 10;
        testItem.increaseCount(input);
        int output = testItem.getCount();
        assertEquals(expected, output);
    }

    @Test
    public void increaseCountOverMax() {
        int input = 30;
        int expected = 20;
        testItem.increaseCount(input);
        int output = testItem.getCount();
        assertEquals(expected, output);
    }

    @Test
    public void increaseCountTwice() {
        int input1 = 5;
        int input2 = 5;
        int expected = 15;
        testItem.increaseCount(input1);
        testItem.increaseCount(input2);
        int output = testItem.getCount();
        assertEquals(expected, output);
    }

    @Test
    public void increaseCountByNegativeAmount() {
        int input = -5;
        exception.expect(UnsupportedOperationException.class);
        exception.expectMessage("Cannot modify count by a negative amount");
        testItem.increaseCount(input);
    }

    @Test
    public void decreaseCountUnderZero() {
        int input = 20;
        int expected = 0;
        testItem.decreaseCount(input);
        int output = testItem.getCount();
        assertEquals(expected, output);
    }

    @Test
    public void decreaseCountOverZero() {
        int input = 3;
        int expected = 2;
        testItem.decreaseCount(input);
        int output = testItem.getCount();
        assertEquals(expected, output);
    }

    @Test
    public void decreaseCountTwice() {
        int input1 = 3;
        int input2 = 1;
        int expected = 1;
        testItem.decreaseCount(input1);
        testItem.decreaseCount(input2);
        int output = testItem.getCount();
        assertEquals(expected, output);
    }

    @Test
    public void decreaseCountByNegativeAmount() {
        int input = -5;
        exception.expect(UnsupportedOperationException.class);
        exception.expectMessage("Cannot modify count by a negative amount");
        testItem.decreaseCount(input);
    }

    @Test
    public void increaseAndDecreaseCount() {
        int input1 = 10;
        int input2 = 5;
        int expected = 10;
        testItem.increaseCount(input1);
        testItem.decreaseCount(input2);
        int output = testItem.getCount();
        assertEquals(expected, output);
    }

    @Test
    public void increaseMax() {
        int input = 5;
        int expected = 25;
        testItem.increaseMax(input);
        int output = testItem.getMax();
        assertEquals(expected, output);
    }

    @Test
    public void increaseMaxByNegativeAmount() {
        int input = -5;
        exception.expect(UnsupportedOperationException.class);
        exception.expectMessage("Cannot modify max by a negative amount");
        testItem.increaseMax(input);
    }

    @Test
    public void decreaseMaxOverZero() {
        int input = 5;
        int expected = 15;
        testItem.decreaseMax(input);
        int output = testItem.getMax();
        assertEquals(expected, output);
    }

    @Test
    public void decreaseMaxUnderZero() {
        int input = 25;
        int expected = 0;
        testItem.decreaseMax(input);
        int output = testItem.getMax();
        assertEquals(expected, output);
    }

    @Test
    public void decreaseMaxByNegativeAmount() {
        int input = -5;
        exception.expect(UnsupportedOperationException.class);
        exception.expectMessage("Cannot modify max by a negative amount");
        testItem.decreaseMax(input);
    }
}