package org.estanciero.model.game;

import junit.framework.TestCase;
import org.estanciero.builder.EntityHandler;
import org.estanciero.model.entities.player.HumanPlayer;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.slot.Slot;
import org.estanciero.model.entities.special_cards.SpecialCard;
import org.estanciero.model.game.enums.GameDifficulty;
import org.estanciero.model.game.enums.GameMode;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GameTest extends TestCase {

    @Test
    public void testGetPlayers() {
        ArrayList<Player> mockedPlayerList = new ArrayList<>();
        mockedPlayerList.add(new HumanPlayer("humanPlayer"));

        List<Slot> slots = new ArrayList<>();
        EntityHandler mockedEntityHandler = new EntityHandler();
        ArrayList<SpecialCard> specialCards = new ArrayList<>();

        Game mockedGame = new Game(1,mockedPlayerList,slots,specialCards,specialCards, GameMode.MONEY_GOAL, GameDifficulty.EASY,mockedEntityHandler,false);

        ArrayList<Player> gameList = Game.getPlayers();

        assertEquals(mockedPlayerList, gameList);
    }

    @Test
    public void testCheckPlayerIsBankrupt() {
        ArrayList<Player> mockedPlayerList = new ArrayList<>();
        mockedPlayerList.add(new HumanPlayer("humanPlayer"));
        List<Slot> slots = new ArrayList<>();
        ArrayList<SpecialCard> specialCards = new ArrayList();
        EntityHandler mockedEntityHandler = new EntityHandler();

        Player player = mockedPlayerList.get(0);
        player.setMoney(-30);

        Game game = new Game(1,mockedPlayerList,slots,specialCards,specialCards,GameMode.MONEY_GOAL,GameDifficulty.EASY,mockedEntityHandler,false);

        game.checkPlayerIsBankrupt(player);
        assertEquals(player.isBankrupt(), true);

    }


}