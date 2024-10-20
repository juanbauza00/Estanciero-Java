package org.estanciero.model.entities.slot;

import junit.framework.TestCase;
import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.action.RepeatTurnAction;
import org.estanciero.model.action.RestAction;
import org.estanciero.model.entities.player.BotPlayer;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.player.bot_profiles.ConservativeBotPlayer;
import org.estanciero.model.entities.player.enums.BotPlayStyle;
import org.estanciero.model.game.Dice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RestSlotTest  {



    Player playerBot;
    RestSlot restSlot;

    @BeforeEach
    void setUp()
    {
        playerBot = new ConservativeBotPlayer("Pepe", BotPlayStyle.CONSERVATIVE);
        restSlot = new RestSlot(1);
    }
    @Test
    void testValidateActionSlot() {
        ArrayList<PlayerActionBase> actualActions = restSlot.validateActionSlot(playerBot);
        boolean containsRepeatTurnAction = actualActions.stream()
                .anyMatch(action -> action instanceof RepeatTurnAction);
        boolean containsRestAction = actualActions.stream()
                .anyMatch(action -> action instanceof RestAction);
        assertTrue(containsRepeatTurnAction || containsRestAction,
                "el array de acciones si o si devuelve una accion de repeatTurn o de rest");

    }


}