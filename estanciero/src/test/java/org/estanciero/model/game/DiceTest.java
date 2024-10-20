package org.estanciero.model.game;

import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DiceTest{





    @Test
    public void testThrowAndGetFaces() {
        int[] values = Dice.throwAndGetFaces();
        Assertions.assertNotNull(values);
    }

    @Test
    public void testThrowDice() {
        Integer totalValue = Dice.throwDice();
        Assertions.assertNotNull(totalValue);
    }
   @Test
    public void testGetDiceValue() {
       int[] values = Dice.throwAndGetFaces();
       Integer totalValue =Dice.getDiceValue(values);
       Assertions.assertNotNull(totalValue);
    }
   @Test
    public void testGetRandomNumber() {
        Integer value = Dice.getRandomNumber(1,3);
        Assertions.assertNotNull(value);
    }
    @Test
    public void testAreFacesEqual() {
        int[] values = {2,2};
        boolean resultExpected = true;
        boolean resultActual = Dice.areFacesEqual(values);
        Assertions.assertEquals(resultExpected,resultActual);
    }
}