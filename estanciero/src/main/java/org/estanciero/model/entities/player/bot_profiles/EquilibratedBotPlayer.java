package org.estanciero.model.entities.player.bot_profiles;

import lombok.Getter;
import lombok.Setter;
import org.estanciero.model.action.PlayerActionBase;
import org.estanciero.model.action.RestAction;
import org.estanciero.model.action.SkipTurnAction;
import org.estanciero.model.action.card_action.DrawSpecialCardAction;
import org.estanciero.model.action.card_action.UseGetOutOfJailCardAction;
import org.estanciero.model.action.jail_action.PayBailAction;
import org.estanciero.model.action.slot_action.PurchaseSlotAction;
import org.estanciero.model.action.slot_action.UpgradeProvinceAction;
import org.estanciero.model.entities.player.BotPlayer;
import org.estanciero.model.entities.player.enums.BotPlayStyle;
import org.estanciero.model.entities.slot.*;
import org.estanciero.model.entities.slot.enums.ProvinceName;

import java.util.ArrayList;
@Setter
@Getter
public class EquilibratedBotPlayer extends BotPlayer implements IBotProfile {


    private  boolean flagCompany = false ;
    private boolean flagUpgrade = false;



    /*● Perfil de jugador equilibrado:
    a. Este jugador busca un equilibrio entre la acumulación de propiedades
    y la construcción de mejoras. (Provincias prioritarias: Mendoza, Santa
    Fe y Tucuman)
    b. Este jugador buscará comprar la serie de Ferrocarriles.
    c. Este jugador comprará fuera de las provincias de preferencia 1 de cada
     3 propiedades que compre.
    d. Construirá mejoras cuando el costo de la construcción no supere el
       50% de su dinero en cuenta o cuando se hayan vendido más del 75%
    de las propiedades.*/
    public EquilibratedBotPlayer(String name, BotPlayStyle playStyle) {
        super(name, playStyle);
    }

    @Override
    public PlayerActionBase selectAction(ArrayList<PlayerActionBase> actions) {

        for (PlayerActionBase action : actions)
        {
            if(action instanceof UseGetOutOfJailCardAction)
            {
                return action;
            }
            if(action instanceof RestAction)
            {
                return  action;
            }
            if(action instanceof PayBailAction)
            {
                return action;
            }
            if(action instanceof DrawSpecialCardAction)
            {
                return  action;
            }

          PlayerActionBase selectedAction = null;
            //purchases
        if(action instanceof PurchaseSlotAction purchaseAction)
        {

                    if (purchaseAction.getSlot() instanceof ProvinceSlot &&
                            (((ProvinceSlot)purchaseAction.getSlot()).getProvince() == ProvinceName.MENDOZA ||
                                    ((ProvinceSlot)purchaseAction.getSlot()).getProvince() == ProvinceName.SANTA_FE ||
                                    ((ProvinceSlot)purchaseAction.getSlot()).getProvince() == ProvinceName.SALTA))
                    {
                        flagUpgrade=false;
                        flagCompany=false;
                        selectedAction = purchaseAction;
                        return selectedAction;

                    }

                  if ( (this.getProvinceCount()>0 && this.getProvinceCount() % 3 == 0) && purchaseAction.getSlot() instanceof ProvinceSlot)
                  {
                      flagUpgrade=false;
                      flagCompany=false;
                  selectedAction = purchaseAction;
                  return selectedAction;
                  }
                   if(purchaseAction.getSlot() instanceof RailwaySlot)
                   {
                       selectedAction = purchaseAction;
                       return  selectedAction;
                   }
                 if(purchaseAction.getSlot() instanceof CompanySlot )
                 {
                     if(!flagCompany)
                     {
                         flagCompany=true;
                         selectedAction = purchaseAction;
                         return  selectedAction;
                     }

                 }

                }
            if (action instanceof UpgradeProvinceAction upgradeAction)
            {
                PurchasableSlot slot = upgradeAction.getSlot();
                if (slot instanceof ProvinceSlot provinceSlot)
                {
                    if(!flagUpgrade)
                    {
                        if(provinceSlot.getRanchPrice() < getMoney() * 0.5 || PurchasableSlot.getPurchasablesSlotWithOwner().size()>=21)
                        {
                            flagUpgrade=true;
                            selectedAction = upgradeAction;
                            return selectedAction;
                        }
                    }

                }
            }



        }


        return new SkipTurnAction();
    }

    }



