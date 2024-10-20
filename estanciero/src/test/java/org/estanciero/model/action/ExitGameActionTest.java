package org.estanciero.model.action;

import org.estanciero.services.InformationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.estanciero.services.InformationService.YES_NO_REGEX;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class ExitGameActionTest {

    @Test
    public void executeAction() {
        ExitGameAction action = new ExitGameAction();

        InformationService mockInfoService = Mockito.mock(InformationService.class);
        Mockito.when(mockInfoService.askForStringAndValidate(Mockito.anyString(), Mockito.anyString())).thenReturn("Y");
        action.infoService = mockInfoService;

        action.executeAction();

    }

    @Test
    public void askPlayerForExit() {
        ExitGameAction action = new ExitGameAction();

        // Mock InformationService behavior to return a predefined answer
        InformationService mockInfoService = Mockito.mock(InformationService.class);
        Mockito.when(mockInfoService.askForStringAndValidate(Mockito.anyString(), Mockito.anyString()))
                .thenReturn("Y"); // Simulate user entering "Y"

        // Inject the mock service into the action
        action.infoService = mockInfoService;

        // Call the method and assert the return value
        boolean confirmedExit = action.askPlayerForExit();

        assertTrue(confirmedExit);

        // Verify interaction with the mock service (optional)
        Mockito.verify(mockInfoService).askForStringAndValidate(YES_NO_REGEX,
                "Please type Y for Yes or N for no");
    }

    @Test
    void askPlayerForSave() {
        ExitGameAction action = new ExitGameAction();

        // Mock InformationService behavior to return a predefined answer
        InformationService mockInfoService = Mockito.mock(InformationService.class);
        Mockito.when(mockInfoService.askForStringAndValidate(Mockito.anyString(), Mockito.anyString()))
                .thenReturn("N"); // Simulate user entering "N"

        // Inject the mock service into the action
        action.infoService = mockInfoService;

        // Call the method and assert the return value
        boolean wantsToSave = action.askPlayerForSave();

        assertFalse(wantsToSave);

        // Verify interaction with the mock service (optional)
        Mockito.verify(mockInfoService).askForStringAndValidate(YES_NO_REGEX,
                "Please type Y for Yes or N for no");
    }
}