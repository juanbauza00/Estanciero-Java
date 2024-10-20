package org.estanciero.services;


import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.entities.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InformationServiceTest {

    private InformationService informationService;
    private Player mockPlayer;
    private PlayerActionBase mockAction;
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";

    @BeforeEach
    public void setUp() {
        informationService = new InformationService();
        mockPlayer = Mockito.mock(Player.class);
        mockAction = Mockito.mock(PlayerActionBase.class);
    }

    @Test
    public void printWelcomeMessageShouldPrintCorrectMessage() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        informationService.printWelcomeMessage();
        String expectedOutput = "Welcome to the "+ANSI_CYAN+"Estanciero" + ANSI_RESET + " CLI Game!";
        assertEquals(expectedOutput.length(), outContent.toString().trim().length());
        System.setOut(System.out);
    }
    @Test
    public void getPlayerNameWithApostropheShouldReturnWithLetter() {
        String expected = "Mike's";
        String actual = informationService.getPlayerNameWithApostrophe("Mike");
        assertEquals(expected, actual);
    }

    @Test
    public void getPlayerNameWithApostropheShouldReturnWithoutLetter() {
        String expected = "Jamie's";
        String actual = informationService.getPlayerNameWithApostrophe("Jamie");
        assertEquals(expected, actual);
    }

    @Test
    public void printActionsAndChooseShouldReturnChosenAction() {
        // Arrange
        ArrayList<PlayerActionBase> actions = new ArrayList<>(Arrays.asList(mockAction));
        mockAction.displayMessage = "Action 1 - Skip Turn";
        mockAction.onExecuteMessage = "Skipping turn...";

        // Mock the scanner input
        Scanner scanner = mock(Scanner.class);
        when(scanner.nextInt()).thenReturn(1);
        informationService.setScanner(scanner); // You might need to create a setter for scanner in InformationService

        // Act
        PlayerActionBase chosenAction = informationService.printActionsAndChoose(actions);

        // Assert
        assertEquals(mockAction, chosenAction);
    }

    @Test
    public void printPlayerDiceShouldPrintCorrectMessage() {
        when(mockPlayer.getPlayerNameWithColor()).thenReturn("Player");
        int[] dice = {1, 2};
        informationService.printPlayerDice(mockPlayer, dice);
        verify(mockPlayer, times(1)).getPlayerNameWithColor();
    }
}