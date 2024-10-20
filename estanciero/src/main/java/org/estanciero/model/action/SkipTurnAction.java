package org.estanciero.model.action;

import org.estanciero.services.InformationService;

public class SkipTurnAction extends PlayerActionBase {

    InformationService infoService = new InformationService();
    public SkipTurnAction() {
        this.displayMessage = "Skip Turn";
        this.onExecuteMessage = "Skipping turn...";
        this.isAutomatic = false;
    }

    @Override
    public void executeAction() {
    }
}
