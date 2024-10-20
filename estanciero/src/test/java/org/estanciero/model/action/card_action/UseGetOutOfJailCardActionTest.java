package org.estanciero.model.action.card_action;

import org.estanciero.builder.EntityHandler;
import org.estanciero.model.action.card_action.UseGetOutOfJailCardAction;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.slot.Slot;
import org.estanciero.model.entities.special_cards.SpecialCard;
import org.estanciero.model.entities.special_cards.SpecialCardType;
import org.estanciero.model.game.Game;
import org.estanciero.model.game.enums.GameDifficulty;
import org.estanciero.model.game.enums.GameMode;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class UseGetOutOfJailCardActionTest {

    //@Test
//    public void testExecuteAction() {
//        Player mockPlayer = Mockito.mock(Player.class);
//        SpecialCard mockCard = Mockito.mock(SpecialCard.class);
//
//        when(mockPlayer.getOutOfJailCards()).thenReturn(new ArrayList<>(Arrays.asList(mockCard)));
//        when(mockCard.getCardType()).thenReturn(SpecialCardType.DESTINY);
//
//        UseGetOutOfJailCardAction action = new UseGetOutOfJailCardAction(mockPlayer);
//
//        int moneyGoal = 10000;
//        ArrayList<Player> players = new ArrayList<>();
//        List<Slot> slots = new ArrayList<>();
//        ArrayList<SpecialCard> luckCardDeck = new ArrayList<>();
//        GameMode mode = GameMode.MONEY_GOAL;
//
//        GameDifficulty difficulty = GameDifficulty.EASY;
//        EntityHandler entityHandler = new EntityHandler();
//
//
//        Game game = new Game(moneyGoal, players, slots, luckCardDeck, new ArrayList<>(), mode, difficulty, entityHandler,false);
//
//
//        action.executeAction();
//
//        // ve si se llamaron los métodos esperados
//        verify(mockPlayer, times(1)).setInJail(false);
//        //se llama dos veces. Una vez para obtener la tarjeta de "Salir de la cárcel"
//        // y la segunda vez para eliminar esa tarjeta de la lista de tarjetas del jugador.
//        verify(mockPlayer, times(2)).getOutOfJailCards();
//    }
}
