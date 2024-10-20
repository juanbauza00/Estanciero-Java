package org.estanciero.model.entities.player;

import junit.framework.TestCase;
import org.estanciero.model.entities.player.enums.BotPlayStyle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BotPlayerTest  {


    BotPlayer botPlayer;
    @BeforeEach
    void setUp()
    {
         botPlayer = new BotPlayer("Pepe",BotPlayStyle.AGGRESSIVE);
    }
    @Test
    public void testGetPlayerNameWithColor() {
        botPlayer.setName("Pepas");
        assertEquals("\u001B[35mPepas\u001B[0m", botPlayer.getPlayerNameWithColor());
    }

    @Test
    public void testGetBotPlayStyle() {
        BotPlayer botPlayer = new BotPlayer("Pepe", BotPlayStyle.AGGRESSIVE);
        BotPlayStyle botPlayStyleExpected = BotPlayStyle.AGGRESSIVE;
        BotPlayStyle botPlayStyleActual = botPlayer.getBotPlayStyle();
        Assertions.assertEquals(botPlayStyleExpected,botPlayStyleActual);
    }

    @Test
    public void testSetBotPlayStyle() {
        BotPlayStyle botPlayStyleExpected = BotPlayStyle.CONSERVATIVE;
        botPlayer.setBotPlayStyle(BotPlayStyle.CONSERVATIVE);
        BotPlayStyle botPlayStyleActual = botPlayer.getBotPlayStyle();
        Assertions.assertEquals(botPlayStyleExpected,botPlayStyleActual);
    }


}