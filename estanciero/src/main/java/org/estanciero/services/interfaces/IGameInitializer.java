package org.estanciero.services.interfaces;

import org.estanciero.model.entities.player.Player;
import org.estanciero.model.game.enums.GameDifficulty;
import org.estanciero.model.game.enums.GameMode;

import java.util.ArrayList;

public interface IGameInitializer {
    public GameDifficulty askPlayerForDifficulty();
    public GameMode askPlayerForGameMode();
    public int askPlayerForMoneyGoal();
    public void createPlayers();
    public Player createNewHumanPlayer();
}
