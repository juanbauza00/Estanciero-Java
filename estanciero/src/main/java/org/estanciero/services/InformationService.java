package org.estanciero.services;



import lombok.Data;
import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.action.SkipTurnAction;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.game.Dice;

import java.util.*;
import java.util.regex.Pattern;

@Data
public class InformationService {
    Scanner scanner;

    //colors
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String REGEX_NON_EMPTY = "(.|\\s)*\\S(.|\\s)*";
    public static final String YES_NO_REGEX = "[yYnN]";
    public static final String YES_REGEX = "[yY]";
    public InformationService() {
        scanner = new Scanner(System.in);
    }

    public void print(String message) {
        //Print generico para mostrar cosas en consola, agrega un espacio en blanco para
        //tener mas legibilidad
        System.out.println();
        System.out.println(message);
    }

    public void printWelcomeMessage() {
        System.out.println("\nWelcome to the "+ANSI_CYAN+"Estanciero" + ANSI_RESET + " CLI Game!\n");
    }

    public void printSeparator(int size) {
        String stringToPrint = ANSI_WHITE;
        for (int i = 0; i < size; i++) {
            stringToPrint += "-";
        }
        stringToPrint += ANSI_RESET;
        System.out.println(stringToPrint);
    }

    public void printHighlighted(String message, int word, String color) {
        String stringToPrint = "";
        System.out.println();
        String[] messageArr = message.split(" ");
        if (word > messageArr.length) {
            System.out.println("InformationService error: printHighlighted() specified word number is greater than message size");
        }
        else {
            messageArr[word] = color + messageArr[word] + ANSI_RESET;
        }
        stringToPrint = String.join(" ", messageArr);
        stringToPrint += ANSI_RESET;
        System.out.println(stringToPrint);

    }

    public String askForStringAndValidate(String regex, String errorMessage) {


        String message = "";
        Pattern pattern = Pattern.compile(regex);
        boolean answer = false;

        while (!answer) {
            message = scanner.next();
            if (pattern.matcher(message).matches()) {
                answer = true;

            } else {
                print(errorMessage);
            }
        }

        return message;

    }

    public int askForNumberBetweenRange(int min, int max) {
        int number = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.println("Please enter a number between " + min + " and " + max + ":");
                number = scanner.nextInt();
                if (number < min || number > max) {
                    System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
                } else {
                    validInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
        return number;
    }

    public void printPlayerDice(Player player, int[] dice) {
        int value = Dice.getDiceValue(dice);
        System.out.println(" ");
        System.out.println(player.getPlayerNameWithColor()+ " throws the dice: gets "+ANSI_GREEN + dice[0]
                + ANSI_RESET+" and "+ANSI_GREEN+
                dice[1] +ANSI_RESET +", total  " + ANSI_GREEN + value + ANSI_RESET);
    }

    public String getPlayerNameWithApostrophe(String playerName) {
        String[] playerNameArr = playerName.split("");
        String lastLetter = playerNameArr[playerNameArr.length -1];
        if (lastLetter.equals("S") || lastLetter.equals("s")) {
            return playerName + "'";
        }
        else {
            return playerName + "'s";
        }
    }

    public PlayerActionBase printActionsAndChoose(ArrayList<PlayerActionBase> actions) {
        Map<Integer,PlayerActionBase> integerActionMap = new HashMap<>();

        for (int i = 0; i < actions.size(); i++) {
            integerActionMap.put(i+1, actions.get(i));
        }


        print("In this turn you are able to: ");
        integerActionMap.forEach((number,action) -> {
            if (action.displayMessage == null) {
                print(number + ". " + "placeholder message for action" + action.getClass());
            }
            else {
                print(number + ". " + action.displayMessage);
            }

        });

        int chosenNumber = askForNumberBetweenRange(1,integerActionMap.size());

        return actions.get(chosenNumber -1);
    }






}
