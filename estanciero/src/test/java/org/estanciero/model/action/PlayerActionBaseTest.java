package org.estanciero.model.action;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PlayerActionBaseTest {
    @Mock
    private PlayerActionBase playerActionBase;

    @Test
    public void testExecuteAction() {
        playerActionBase.executeAction();

        verify(playerActionBase).executeAction();
    }
}