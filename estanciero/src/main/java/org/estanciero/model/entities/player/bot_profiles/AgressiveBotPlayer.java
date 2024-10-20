package org.estanciero.model.entities.player.bot_profiles;

import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.action.RepeatTurnAction;
import org.estanciero.model.action.RestAction;
import org.estanciero.model.action.SkipTurnAction;
import org.estanciero.model.action.card_action.DrawSpecialCardAction;
import org.estanciero.model.action.card_action.UseGetOutOfJailCardAction;
import org.estanciero.model.action.jail_action.PayBailAction;
import org.estanciero.model.action.money_action.PayRentAction;
import org.estanciero.model.action.money_action.PayTaxAction;
import org.estanciero.model.action.slot_action.PurchaseSlotAction;
import org.estanciero.model.action.slot_action.UpgradeProvinceAction;
import org.estanciero.model.entities.player.BotPlayer;
import org.estanciero.model.entities.player.enums.BotPlayStyle;
import org.estanciero.model.entities.slot.CompanySlot;
import org.estanciero.model.entities.slot.ProvinceSlot;
import org.estanciero.model.entities.slot.PurchasableSlot;
import org.estanciero.model.entities.slot.RailwaySlot;
import org.estanciero.model.entities.slot.enums.ProvinceName;
import java.util.ArrayList;


public class AgressiveBotPlayer extends BotPlayer implements IBotProfile {

    public AgressiveBotPlayer(String name, BotPlayStyle playStyle) {
        super(name, playStyle);
    }
    @Override
    public PlayerActionBase selectAction(ArrayList<PlayerActionBase> actions) {

        //Perfil de jugador agresivo:
        //a. Este jugador busca maximizar el retorno de inversión rápidamente,
        //incluso a costa de correr mayores riesgos. (Provincias prioritarias:
        //Tucuman, Córdoba y Buenos Aires)
        //b. Este jugador buscará comprar la serie de Ferrocarriles y Companias.
        //c. Este jugador no comprará fuera de las provincias de preferencia a no
        //ser que ya se hayan vendido a otros jugadores al menos una propiedad
        //de todas sus zonas de preferencia o el mismo haya completado sus
        //zonas; en dicho caso, comprará tantas propiedades como pueda.
        //d. Priorizará la expansión rápida y construirá mejoras cada vez que
        //pueda.
        //e. Opcionalmente: Si se implementa la compra/venta entre jugadores,
        //este perfil buscará comprar para completar sus provincias de
        //preferencia y estará dispuesto a pagar hasta el 200% del valor original
        //de la propiedad.
        PlayerActionBase selectedAction = null;

        for (PlayerActionBase action : actions) {

            if (action instanceof DrawSpecialCardAction) {
                selectedAction = action;
                return selectedAction;

            }


            if( action instanceof PayBailAction ){
                selectedAction = action;
                return selectedAction;
            }

            if(action instanceof UseGetOutOfJailCardAction)
            {
                return action;
            }
            if(action instanceof RestAction)
            {
                return  action;
            }

            if (action instanceof UpgradeProvinceAction) {
                //UpgradeProvinceAction upgradeAction = (UpgradeProvinceAction) action;
                //selectedAction = upgradeAction;
                //return selectedAction;
                return action;

            }

            if (action instanceof PurchaseSlotAction purchaseAction) {
                PurchasableSlot slot = purchaseAction.getSlot();

                if (slot instanceof ProvinceSlot &&
                        (((ProvinceSlot)slot).getProvince() == ProvinceName.TUCUMAN ||
                                ((ProvinceSlot)slot).getProvince() == ProvinceName.CORDOBA ||
                                ((ProvinceSlot)slot).getProvince() == ProvinceName.BSAS)) {
                    selectedAction = purchaseAction;
                    return selectedAction;
                }

                if (slot instanceof RailwaySlot || slot instanceof CompanySlot) {

                        selectedAction = purchaseAction;
                        return selectedAction;
                    }
                } else {
                if (action instanceof SkipTurnAction){
                    selectedAction = action;
                    return selectedAction;
                }
            }


        }

        return new SkipTurnAction();
    }
}

