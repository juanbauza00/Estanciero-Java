package org.estanciero.model.action.jail_action;

import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.action.card_action.UseGetOutOfJailCardAction;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.game.Dice;
import org.estanciero.services.InformationService;

public class GoToJailAction extends PlayerActionBase {


    private Player playerWhoCasted;
    private final int JAIL_POSITION = 14;
    private InformationService infoService;

    public GoToJailAction(Player playerWhoCasted) {
        this.playerWhoCasted = playerWhoCasted;
        this.isAutomatic = true;
        this.infoService = new InformationService();
    }
    @Override
    public void executeAction() {
        sendPlayerToJail();
    }


    public void sendPlayerToJail(){

        //verificar si el jugador tiene cartas de salir de la carcel
        if (playerWhoCasted.getOutOfJailCards().size() > 0) {
            infoService.print(playerWhoCasted.getPlayerNameWithColor() + " has a Get Out Of Jail card, they use it to get out of jail");
            UseGetOutOfJailCardAction outOfJailAction = new UseGetOutOfJailCardAction(playerWhoCasted);
            outOfJailAction.executeAction();
            return;
        }

        //lanzar dados para evitar ir a la carcel
        infoService.print(playerWhoCasted.getPlayerNameWithColor() + " will throw the dice for a chance to avoid going to jail");
        int[] diceRolls = Dice.throwAndGetFaces();
        infoService.print(playerWhoCasted.getPlayerNameWithColor() + " rolls " + diceRolls[0] + "and " + diceRolls[1]);
        if (Dice.areFacesEqual(diceRolls)) {
            infoService.print(playerWhoCasted.getPlayerNameWithColor() + " rolled double, doesn't have to go to jail");
            playerWhoCasted.setInJail(false);


        }
        else {
            infoService.print(playerWhoCasted.getPlayerNameWithColor() + "did not get double, has to pay a $1000 fine");
            if (playerWhoCasted.getMoney() < 1000) {
                infoService.print(playerWhoCasted.getPlayerNameWithColor() + "doesn't have enough money. They can still pay the fine on their next turn");
                playerWhoCasted.setInJail(true);
                playerWhoCasted.setPosition(JAIL_POSITION);
            }
            else {
                PayBailAction payBail = new PayBailAction(playerWhoCasted);
                payBail.executeAction();
            }

        }






    }
}
