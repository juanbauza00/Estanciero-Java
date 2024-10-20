package org.estanciero.model.action;

import org.estanciero.services.InformationService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.estanciero.services.InformationService.YES_NO_REGEX;
import static org.estanciero.services.InformationService.YES_REGEX;

public class ExitGameAction extends PlayerActionBase{

    InformationService infoService;
    public ExitGameAction() {
        this.infoService = new InformationService();
        this.displayMessage = "Exit game";
        this.onExecuteMessage = "Exiting game...";
    }

    public void executeAction() {}

    public boolean askPlayerForExit() {
        infoService.print("Are you sure you want to exit? (Y/N)");
        String answer = infoService.askForStringAndValidate(YES_NO_REGEX,"Please type Y for Yes or N for no");

        if (answer.matches(YES_REGEX)) {
            return true;
        }
        return false;
    }

    public boolean askPlayerForSave() {
        infoService.print("Do you want to save the current game? (Y/N)");
        String shouldSaveAnswer = infoService.askForStringAndValidate(YES_NO_REGEX,"Please type Y for Yes or N for no");
        if (shouldSaveAnswer.matches(YES_REGEX)) {
            return true;
        }
        else return false;
    }

}
