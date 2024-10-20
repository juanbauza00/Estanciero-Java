package org.estanciero.model.entities.player.bot_profiles;

import jakarta.validation.valueextraction.Unwrapping;
import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.action.SkipTurnAction;
import org.estanciero.model.action.card_action.DrawSpecialCardAction;
import org.estanciero.model.action.jail_action.PayBailAction;
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

public class ConservativeBotPlayer  extends BotPlayer implements IBotProfile {


    public ConservativeBotPlayer(String name, BotPlayStyle playStyle) {
        super(name, playStyle);
    }
    @Override
    public PlayerActionBase selectAction(ArrayList<PlayerActionBase> actions) {

        //Perfil de jugador conservador:
//a. Este jugador tiende a ser cauteloso y prioriza la acumulación de
//propiedades de bajo costo. (Provincias prioritarias: Formosa, Río Negro
//y Salta)
//b. Este jugador comprará fuera de las provincias de preferencia 1 de cada
//5 propiedades que compre.
//c. Construirá mejoras solo cuando el costo de la construcción no
//sobrepase el 30% de su dinero en cuenta.

        PlayerActionBase selectedAction = null;
        for(PlayerActionBase action: actions){

            if( action instanceof PayBailAction){
                selectedAction = action;
                return selectedAction;
            }

            if (action instanceof DrawSpecialCardAction ) {
                selectedAction = action;
                return selectedAction;
            }

            if (action instanceof PurchaseSlotAction purchaseAction) {
                PurchasableSlot slot = purchaseAction.getSlot();



                if (slot instanceof ProvinceSlot &&
                        (((ProvinceSlot)slot).getProvince() == ProvinceName.FORMOSA ||
                                ((ProvinceSlot)slot).getProvince() == ProvinceName.RIO_NEGRO ||
                                ((ProvinceSlot)slot).getProvince() == ProvinceName.SALTA)) {
                    selectedAction = purchaseAction;
                    return selectedAction;
                }

                if( (getProvinceCount() > 0 && this.getProvinceCount() % 5 == 0) && slot instanceof ProvinceSlot ){
                    selectedAction = purchaseAction;
                    return selectedAction;
                }

                if(slot instanceof CompanySlot)
                {
                    selectedAction = purchaseAction;
                    return selectedAction;

                }

                if (slot instanceof RailwaySlot){

                    selectedAction = purchaseAction;
                    return selectedAction;
                }


            }



            if (action instanceof UpgradeProvinceAction upgradeAction){
                PurchasableSlot slot = upgradeAction.getSlot();
                if (slot instanceof ProvinceSlot provinceSlot){


                    if(provinceSlot.getRanchPrice() < getMoney() * 0.3){
                        selectedAction = upgradeAction;
                        return selectedAction;
                    }
                }
            }


        }
        return new SkipTurnAction();
    }
}