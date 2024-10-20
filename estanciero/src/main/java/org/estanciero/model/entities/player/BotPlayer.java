package org.estanciero.model.entities.player;

import lombok.Getter;
import lombok.Setter;
import org.estanciero.model.entities.player.enums.BotPlayStyle;
import org.estanciero.services.InformationService;

@Getter
@Setter
public class BotPlayer extends Player  {

    private BotPlayStyle botPlayStyle;


    //Constructor para partida nueva
    public BotPlayer(String name, BotPlayStyle playStyle) {
        super(name);
        switch (playStyle) {
            case CONSERVATIVE:
                this.botPlayStyle = BotPlayStyle.CONSERVATIVE;
                break;
            case EQUILIBRATED:
                this.botPlayStyle = BotPlayStyle.EQUILIBRATED;
                //new EquilibratedBotPlayer(name, BotPlayStyle.EQUILIBRATED);
                break;
            case AGGRESSIVE:
                this.botPlayStyle = BotPlayStyle.AGGRESSIVE;
                break;
        }
    }

    //Constructor para partida cargada
    public BotPlayer(String name, int id, BotPlayStyle playStyle) {
        super(name, id);
        switch (playStyle) {
            case CONSERVATIVE:
                this.botPlayStyle = BotPlayStyle.CONSERVATIVE;
                break;
            case EQUILIBRATED:
                this.botPlayStyle = BotPlayStyle.EQUILIBRATED;
                //new EquilibratedBotPlayer(name, BotPlayStyle.EQUILIBRATED);
                break;
            case AGGRESSIVE:
                this.botPlayStyle = BotPlayStyle.AGGRESSIVE;
                break;
        }
    }

    // returns colored bot player name to print in console
    // human player has a different color
    public String getPlayerNameWithColor() {
        return InformationService.ANSI_PURPLE + getName() + InformationService.ANSI_RESET;
    }








}
