package org.estanciero.model.action.money_action;

import junit.framework.TestCase;
import org.estanciero.model.entities.player.HumanPlayer;
import org.estanciero.model.entities.slot.ProvinceSlot;
import org.estanciero.model.entities.slot.Slot;
import org.estanciero.model.entities.slot.enums.ProvinceName;
import org.estanciero.model.game.Game;
import org.estanciero.model.game.enums.GameDifficulty;
import org.estanciero.model.game.enums.GameMode;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PayRentActionTest extends TestCase {



    @Test
    public void testPayRentProvinceSlot() {
        HumanPlayer owner = new HumanPlayer("owner");
        HumanPlayer playerWhoPays = new HumanPlayer("playerWhoPays");
        ProvinceSlot province = new ProvinceSlot();
        province.setProvince(ProvinceName.MENDOZA);
        province.setOwner(owner);
        int[] rentValue = new int[]{100,200,300,400};
        province.setRentValue(rentValue);

        //instanciar game (necesario porque PayRentAction llama a Game.doesPlayerHaveFullProvince())
        Game mockedGame = new Game(1,new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>(), GameMode.MONEY_GOAL, GameDifficulty.EASY,null,false);


        //Al no haber slots en el juego (Game inicializado con una lista vacia) para verificar si el dueño tiene
        //todas las provincias con ese nombre, el metodo
        //retorna true siempre, utilizamos esto para simular que el jugador dueño tiene todas las provincias con ese nombre
        //por lo tanto, el jugador que pasa por el slot tiene que pagar el doble
        //como el slot no tiene ninguna mejora, el rentPrice sera igual al primer item del arr
        //por lo tanto rentPrice[0] = 100
        //100 precio renta, multiplicado por 2 porque el dueño "tiene todas las provincias", multiplicado por 5 que es el
        //valor de los dados = 1000 precio final de renta
        playerWhoPays.setMoney(2000);
        PayRentAction action = new PayRentAction(province,playerWhoPays,province.getRentValue());
        int diceValue = 5;
        action.setDiceValueOptional(Optional.of(5));
        action.executeAction();
        assertEquals(1000,playerWhoPays.getMoney());


    }
}