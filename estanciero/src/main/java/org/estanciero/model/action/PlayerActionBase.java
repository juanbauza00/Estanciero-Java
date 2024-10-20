package org.estanciero.model.action;

import org.estanciero.model.entities.special_cards.SpecialCard;

import java.util.Optional;

public abstract class PlayerActionBase {

    //Clase base de la que heredan todas las acciones del jugador

    public String displayMessage;
    public String onExecuteMessage;

    public boolean isAutomatic;
    //metodo que heredan todos los hijos de PlayerActionBase, cada accion lo implementa con una llamda a su metodo
    //particular
    //de esta forma podemos llamar executeAction() en cualquier clase que herede de PlayerActionBase sin necesitar
    //saber cual es el nombre del metodo de la accion.
    public abstract void executeAction();


}
