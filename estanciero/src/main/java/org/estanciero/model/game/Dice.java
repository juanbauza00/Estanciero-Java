package org.estanciero.model.game;

public class Dice {

    public static int[] throwAndGetFaces() {
        int[] values = new int[2];
        values[0] = getRandomNumber(1,6);
        values[1] = getRandomNumber(1,6);
        return values;
    }

    public static int throwDice() {
        int[] faces = throwAndGetFaces();
        return (faces[0] + faces[1]);
    }

    public static int getDiceValue(int[] values) {
        return values[0]+values[1];
    }


    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static boolean areFacesEqual(int[] values) {
        return values[0] == values[1];
    }
}
