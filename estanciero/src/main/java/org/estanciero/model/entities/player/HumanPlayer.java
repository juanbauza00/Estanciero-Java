package org.estanciero.model.entities.player;

import lombok.Getter;
import lombok.Setter;
import org.estanciero.services.InformationService;

@Getter
@Setter
public class HumanPlayer extends Player {

    //Constructor para partida nueva
   public HumanPlayer(String name) {
       super(name);
   }

   //Constructor para partida cargada
   public HumanPlayer(String name, int id) {
       super(name, id);
   }


   //returns colored player name to print in console
    //bot player has a different color
   public String getPlayerNameWithColor() {
       return InformationService.ANSI_BLUE + super.getName() + InformationService.ANSI_RESET;
   }
}
