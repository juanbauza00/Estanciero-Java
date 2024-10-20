package org.estanciero.model.entities.player;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HumanPlayerTest  {

    Player player;
    @BeforeEach
    void setUp()
    {
        player = new HumanPlayer("Pepe");
    }
    @Test
    public void testGetPlayerNameWithColor() {
        player.setName("Pepas");
        assertEquals("\u001B[34mPepas\u001B[0m", player.getPlayerNameWithColor());
    }
}