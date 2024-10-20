package org.estanciero.model.game;

import org.estanciero.builder.EntityHandler;
import org.estanciero.dtos.GameDto;
import org.estanciero.model.entities.player.BotPlayer;
import org.estanciero.model.entities.player.HumanPlayer;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.player.enums.BotPlayStyle;
import org.estanciero.model.game.enums.GameDifficulty;
import org.estanciero.model.game.enums.GameMode;
import org.estanciero.services.InformationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GameInitializerTest {

    @Mock
    InformationService mockInfoService;

    @InjectMocks
    GameInitializer gameInitializer;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


    }
    @Test
    void askPlayerForDifficulty() {
        when(mockInfoService.askForStringAndValidate("[1-3]", "Must choose a value between 1 and 3")).thenReturn("2");

        gameInitializer = new GameInitializer(null, null);
        gameInitializer.setInfoService(mockInfoService);

        GameDifficulty result = gameInitializer.askPlayerForDifficulty();

        assertEquals(GameDifficulty.MEDIUM, result);
    }

    @Test
    void askPlayerForGameMode() {
        // para lastplayer
        when(mockInfoService.askForStringAndValidate("[1-2]", "You must choose a valid game mode")).thenReturn("2");
        GameInitializer gameInitializer = new GameInitializer(null, null);
        gameInitializer.setInfoService(mockInfoService);
        assertEquals(GameMode.LASTPLAYER, gameInitializer.askPlayerForGameMode());

        // para money_goal
        when(mockInfoService.askForStringAndValidate("[1-2]", "You must choose a valid game mode")).thenReturn("1");
        gameInitializer = new GameInitializer(null, null);
        gameInitializer.setInfoService(mockInfoService);
        assertEquals(GameMode.MONEY_GOAL, gameInitializer.askPlayerForGameMode());

    }

    @Test
    void askPlayerForMoneyGoal() {
        when(mockInfoService.askForNumberBetweenRange(40000,200000)).thenReturn(50000);

        GameInitializer gameInitializer = new GameInitializer(null, null);
        gameInitializer.setInfoService(mockInfoService);

        assertEquals(50000, gameInitializer.askPlayerForMoneyGoal());


    }


    @Test
    void testCreateNewHumanPlayer() {
        InformationService mockInfoService = Mockito.mock(InformationService.class);

        when(mockInfoService.askForStringAndValidate(InformationService.REGEX_NON_EMPTY,"Name field can't be left empty")).thenReturn("TestPlayer");


        GameInitializer gameInitializer = new GameInitializer(null, null);
        gameInitializer.setInfoService(mockInfoService);

        Player player = gameInitializer.createNewHumanPlayer();

        // aca se verifica que se crea un HumanPlayer con el nombre correcto
        assertTrue(player instanceof HumanPlayer);
        assertEquals("TestPlayer", player.getName());
    }

}