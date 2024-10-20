package org.estanciero.model.action;


import org.estanciero.builder.EntityHandler;
import org.estanciero.dtos.GameDto;

public class SaveGameAction extends PlayerActionBase {


    public SaveGameAction() {
        this.displayMessage = "Save game";
        this.onExecuteMessage = "Saving game...";
    }


    @Override
    public void executeAction() {

    }

    public void saveGame(GameDto gameDto, EntityHandler entityHandler) {
        entityHandler.saveGame(gameDto);
    }
}
