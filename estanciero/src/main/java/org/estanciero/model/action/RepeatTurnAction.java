package org.estanciero.model.action;
import org.estanciero.model.game.Dice;

public class RepeatTurnAction extends PlayerActionBase {

    public RepeatTurnAction(){
        this.isAutomatic = true;
    }

    @Override
    public void executeAction() {

    }
}
