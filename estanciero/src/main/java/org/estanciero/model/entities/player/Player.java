package org.estanciero.model.entities.player;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.estanciero.model.entities.slot.PurchasableSlot;
import org.estanciero.model.entities.slot.Slot;
import org.estanciero.model.entities.special_cards.SpecialCard;
import org.estanciero.model.game.Game;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class Player {
    private String name;
    private int id;
    private int money;
    private int position;
    private boolean isInJail;
    private List<SpecialCard> outOfJailCards;
    private int railwayCount;
    private int companyCount;
    private int restCount;
    private int provinceCount;
    private int upgradeCount;
    private boolean isPlayerTurn;
    private boolean isBankrupt;
    private int consecutiveRollCount;

    //Constructor partida nueva
    public Player(String name) {
        this.name = name;
        this.money = 35000;
        this.position = 0;
        this.isInJail = false;
        this.outOfJailCards = new ArrayList<>(); //TODO VER
        this.railwayCount = 0;
        this.companyCount = 0;
        this.restCount =0;
        this.provinceCount =0;
        this.upgradeCount =0;
        this.isBankrupt = false;
        this.consecutiveRollCount = 0;
        this.outOfJailCards = new ArrayList<>();


    }

    //Constructor partida cargada
    public Player(String name, int id) {
        this.name = name;
        this.id = id;
        this.money = 35000;
        this.position = 0;
        this.isInJail = false;
        this.outOfJailCards = new ArrayList<>();
        this.railwayCount = 0;
        this.companyCount = 0;
        this.restCount =0;
        this.provinceCount =0;
        this.upgradeCount =0;
        this.isBankrupt = false;
        this.outOfJailCards = new ArrayList<>();

    }

    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        isPlayerTurn = playerTurn;
    }

    public ArrayList<Slot> getOwnedSlots()
    {
        List<Slot> slots = Game.getSlots();
        ArrayList<Slot> ownedSlots = new ArrayList<>();
        for (Slot slot : slots )
        {
            if( slot instanceof PurchasableSlot purchasableSlot)
            {
                if(purchasableSlot.getOwner()==this)
                {
                    ownedSlots.add(slot);

                }
            }
        }
        return ownedSlots;
    }

    //implementado por las clases que heredan, retorna el nombre del jugador con un color para diferenciar los bots
    //de los humanos en la interfaz de consola
    public abstract String getPlayerNameWithColor();



}
