package org.estanciero.services.implementation;

import org.estanciero.model.action.card_action.UseGetOutOfJailCardAction;
import org.estanciero.model.action.jail_action.GoToJailAction;
import org.estanciero.model.entities.special_cards.SpecialCard;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.game.Game;
import org.estanciero.services.interfaces.ISpecialCardService;

import java.util.List;


public class SpecialCardService implements ISpecialCardService {


    public void executeCard(SpecialCard card, Player player) {
        switch (card.getActionType()) {
            case PAY:
                pay(card, player);
                break;

            case COLLECT:
                collect(card, player);

                break;

            case MOVE:
                move(card, player);

                break;

            case GOJAIL:
                goJail(card, player);

                break;

            case OUTJAIL:
                outJail(card, player);

                break;

            case PAYFOREACH:
                payForeach(card, player);

                break;

            case COLLECTFOREACH:
                collectForeach(card, player);

                break;
        }
    }

    public void pay(SpecialCard card, Player player) {
        player.setMoney(player.getMoney() - card.getMoneyAmount());
    }

    public void collect(SpecialCard card, Player player) {
        player.setMoney(player.getMoney() + card.getMoneyAmount());
    }



    public void move(SpecialCard card, Player player) {
        player.setPosition(card.getToPosition());
    }

    public void goJail(SpecialCard card, Player player) {
        GoToJailAction action = new GoToJailAction(player);
        action.executeAction();
    }
    public void outJail(SpecialCard card, Player player) {

        List<SpecialCard> outOfJailCards = player.getOutOfJailCards();
        outOfJailCards.add(card);
        UseGetOutOfJailCardAction action = new UseGetOutOfJailCardAction(player);
        action.executeAction();
        player.setOutOfJailCards(outOfJailCards);
    }

    public static void payForeach(SpecialCard card, Player player) {
        int totalAmount = player.getUpgradeCount()* card.getMoneyAmount();
        int playerMoney = player.getMoney()-totalAmount;
        player.setMoney(playerMoney);

    }

    public static void collectForeach(SpecialCard card, Player player) {
        List<Player> otherPlayers = Game.getPlayers();

        if (otherPlayers == null || otherPlayers.isEmpty()) {
            return;
        }

        for (Player otherPlayer : otherPlayers) {
            if (otherPlayer != player) {
                player.setMoney(player.getMoney());
                int moneyToPLayer= card.getMoneyAmount();
                player.setMoney(moneyToPLayer + player.getMoney());
                otherPlayer.setMoney(otherPlayer.getMoney() -card.getMoneyAmount());
            }
        }
    }
}
