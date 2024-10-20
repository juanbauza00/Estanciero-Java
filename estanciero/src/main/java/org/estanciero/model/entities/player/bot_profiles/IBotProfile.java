package org.estanciero.model.entities.player.bot_profiles;

import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.entities.player.BotPlayer;

import java.util.ArrayList;

public interface IBotProfile  {

    PlayerActionBase selectAction( ArrayList<PlayerActionBase> actions);


}
