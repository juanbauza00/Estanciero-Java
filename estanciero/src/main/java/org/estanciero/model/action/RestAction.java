package org.estanciero.model.action;


import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.slot.RestSlot;

public class RestAction extends PlayerActionBase {

    //la posible accion de restSlot debe  añadirse al array de availableActions en el momento de caer,
    //es decir que en el primer momento que el jugador cae se le da la opcion de descansar, y en el caso de aceptar caso tirar los dados.
    //las validaciones no son responsabilidad de las clases action, las mismas solo ejecutan la accion.

    private Player playerRest;
    private final RestSlot restSlot;

    public RestAction(RestSlot restSlot,Player playerRest) {
        this.restSlot = restSlot;
        this.playerRest=playerRest;
        this.isAutomatic = true;
    }

    @Override
    public void executeAction() {

        restAction();
    }

    private void restAction() {
        playerRest.setRestCount(playerRest.getRestCount()+1);
        playerRest.setPosition(restSlot.getPosition());

    }
}
