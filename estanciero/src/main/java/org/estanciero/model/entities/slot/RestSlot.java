package org.estanciero.model.entities.slot;

import lombok.NoArgsConstructor;
import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.action.RepeatTurnAction;
import org.estanciero.model.action.RestAction;
import org.estanciero.model.entities.player.BotPlayer;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.player.bot_profiles.AgressiveBotPlayer;
import org.estanciero.model.entities.player.bot_profiles.ConservativeBotPlayer;
import org.estanciero.model.entities.player.bot_profiles.EquilibratedBotPlayer;
import org.estanciero.model.game.Dice;
import org.estanciero.services.interfaces.ISlotValidator;
import org.estanciero.services.InformationService;

import java.util.ArrayList;
import java.util.Objects;

@NoArgsConstructor
public class RestSlot extends Slot implements ISlotValidator {

    InformationService informationService= new InformationService();


    public RestSlot(int position) {
        super(position);
    }



    @Override
    public ArrayList<PlayerActionBase> validateActionSlot(Player playerWhoCasted) {
        ArrayList<PlayerActionBase> playerActionBases = new ArrayList<>();
        if(playerWhoCasted.getRestCount()<2)
        {
            if(playerWhoCasted instanceof EquilibratedBotPlayer || playerWhoCasted instanceof ConservativeBotPlayer || playerWhoCasted instanceof AgressiveBotPlayer botPlayer)
            {
                if(Dice.getRandomNumber(1,2)==1)
                {
                    int[] values = Dice.throwAndGetFaces();
                    if(Dice.areFacesEqual(values))
                    {
                        playerActionBases.add(new RestAction(this, playerWhoCasted));
                        return playerActionBases;
                    }
                    else {
                        playerActionBases.add(new RepeatTurnAction());
                        return  playerActionBases;
                    }
                }
                else {
                    playerActionBases.add(new RepeatTurnAction());
                    return  playerActionBases;
                }


            }
            informationService.print("Do you want to rest? (y/n)");
            if(Objects.equals(informationService.askForStringAndValidate(InformationService.YES_NO_REGEX, "Should enter(y/n)"), InformationService.YES_REGEX))
            {
                int[] values = Dice.throwAndGetFaces();
                if(Dice.areFacesEqual(values))
                {
                    informationService.print("I throw equal dice! He can't rest");
                    playerActionBases.add(new RestAction(this,playerWhoCasted));
                    return  playerActionBases;
                }
                else {
                    informationService.print("Dice combination valid!, you can rest");
                    playerActionBases.add(new RepeatTurnAction());

                }
                if(playerWhoCasted.getRestCount()>1)
                {
                    playerWhoCasted.setRestCount(0);
                }
            }
        }
        else
        {
            //tirar dados
            playerActionBases.add(new RepeatTurnAction());
        }
        return playerActionBases;
    }


}
