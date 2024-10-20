package org.estanciero.builder.mappers;

import lombok.NoArgsConstructor;
import org.estanciero.model.dbEntities.AuxiliarEntities.ProvinceZoneEntity;
import org.estanciero.model.dbEntities.AuxiliarEntities.RentalPriceEntity;
import org.estanciero.model.dbEntities.BoardSlotEntity;
import org.estanciero.model.dbEntities.PlayerEntity;
import org.estanciero.model.dbEntities.PurchasableSlotEntity;
import org.estanciero.model.dbEntities.PurchasableSlotId;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.slot.*;
import org.estanciero.model.entities.slot.enums.ProvinceName;
import org.estanciero.model.entities.slot.enums.ProvinceZone;
import org.estanciero.model.entities.special_cards.SpecialCardType;
import org.estanciero.repositories.ProvinceZoneRepository;
import org.estanciero.repositories.RentalPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@NoArgsConstructor
@Service
public class SlotMapper {
    @Autowired
    RentalPriceRepository rentalPriceRepository;

    @Autowired
    ProvinceZoneRepository provinceZoneRepository;


    public List<Slot> mapNoPurchSlotEntityToModel(List<BoardSlotEntity> slotEntities){
        List<Slot>slotList = new ArrayList<>();
        for(BoardSlotEntity slotEntity : slotEntities){
            int id = slotEntity.getBoardSlotId();
            switch (slotEntity.getBoardSlotTypeId()){
                case 3: //Jail
                    JailSlot jailSlot = new JailSlot(id);
                    slotList.add(jailSlot);
                    break;

                case 4: //Rest
                    RestSlot restSlot = new RestSlot(id);
                    slotList.add(restSlot);
                    break;

                case 5: //Tax
                    TaxSlot taxSlot = new TaxSlot(id);
                    taxSlot.setTax(slotEntity.getAmountPrice());
                    slotList.add(taxSlot);
                    break;

                case 6: //Prize
                    int prizeAmount = slotEntity.getAmountPrice();
                    PrizeSlot prizeSlot = new PrizeSlot(id, prizeAmount);
                    slotList.add(prizeSlot);
                    break;

                case 7: //Luck
                    SpecialCardSlot luckSlot = new SpecialCardSlot(id);
                    luckSlot.setSlotType(SpecialCardType.LUCK);
                    slotList.add(luckSlot);
                    break;

                case 8: //Destiny
                    SpecialCardSlot destinySlot = new SpecialCardSlot(id);
                    destinySlot.setSlotType(SpecialCardType.DESTINY);
                    slotList.add(destinySlot);
                    break;
            }
        }
        return slotList;
    }

    public List<Slot> mapPurchSlotEntityToModel(List<PurchasableSlotEntity> slotEntities, Optional<List<Player>> playerList){
        List<Slot>slotList = new ArrayList<>();
        for(PurchasableSlotEntity slotEntity : slotEntities){
            int id = slotEntity.getId().getBoardSlotId();
            switch (slotEntity.getBoardSlotTypeId()){
                case 0: // Province
                    ProvinceSlot provinceSlot = new ProvinceSlot(id);
                    provinceSlot.setRentValue(mapRentalPrices(slotEntity));
                    provinceSlot.setPurchasePrice(slotEntity.getPurchasePrice());

                    //Mapping province and zone
                    provinceSlot.setProvinceZoneId(slotEntity.getProvinceZoneId());
                    Optional<ProvinceZoneEntity> optionalProvinceZoneEntity = provinceZoneRepository.findById(slotEntity.getProvinceZoneId());
                    ProvinceZoneEntity provinceZone = optionalProvinceZoneEntity.get();
                    ProvinceName province = ProvinceName.valueOf(provinceZone.getProvince());
                    provinceSlot.setProvince(province);
                    ProvinceZone zone = ProvinceZone.valueOf(provinceZone.getZone().getZoneName());
                    provinceSlot.setZone(zone);
                    provinceSlot.setRanchPrice(provinceZone.getRanchPrice());

                    provinceSlot.setRanchCount(slotEntity.getRanchCount());
                    if(slotEntity.getOwnerId() != null){
                        provinceSlot.setOwner(insertOwner(playerList,slotEntity.getOwnerId()));
                    }
                    slotList.add(provinceSlot);
                    break;

                case 1: //Railway
                    RailwaySlot railwaySlot = new RailwaySlot(id);
                    railwaySlot.setPurchasePrice(slotEntity.getPurchasePrice());
                    railwaySlot.setRentValue(mapRentalPrices(slotEntity));
                    if(slotEntity.getOwnerId() != null){
                        railwaySlot.setOwner(insertOwner(playerList,slotEntity.getOwnerId()));
                    }
                    slotList.add(railwaySlot);
                    break;

                case 2: //Company
                    CompanySlot companySlot = new CompanySlot(id);
                    companySlot.setPurchasePrice(slotEntity.getPurchasePrice());
                    companySlot.setRentValue(mapRentalPrices(slotEntity));
                    if(slotEntity.getOwnerId() != null){
                        companySlot.setOwner(insertOwner(playerList,slotEntity.getOwnerId()));
                    }
                    slotList.add(companySlot);
                    break;
            }
        }
        return slotList;
    }

    //Toma la lista de jugadores creados, compara por id y devuelve el objeto jugador con el mismo id que el owner
    public Player insertOwner (Optional<List<Player>> playerList, int ownerId){
        int id = ownerId;
        if(playerList.isPresent()){
            for (Player player : playerList.get()){
                if(player.getId() == id)return player;
            }
        }
        return null;
    }

    public int[] mapRentalPrices(PurchasableSlotEntity slotEntity){
        List<Integer> rentalPriceList = new ArrayList<>();
        int id = slotEntity.getId().getBoardSlotId();
        List<RentalPriceEntity> rentalPriceEntities = rentalPriceRepository.findByBoardSlotId(id);
        for(RentalPriceEntity rentalPrice : rentalPriceEntities){
            rentalPriceList.add(rentalPrice.getPrices());
        }
        int[] rentalPrices = new int[rentalPriceList.size()];
        for (int i = 0; i < rentalPriceList.size(); i++) rentalPrices[i] = rentalPriceList.get(i);
        return rentalPrices;
    }

    public List<PurchasableSlotEntity> mapPurchSlotModelToEntity(List<PurchasableSlot> purchSlots, int matchId){
        List<PurchasableSlotEntity> slotEntityList = new ArrayList<>();
        for(PurchasableSlot slotModel : purchSlots){
            PurchasableSlotEntity slotEntity = new PurchasableSlotEntity();
            //SET ID
            PurchasableSlotId slotId = new PurchasableSlotId();
            slotId.setBoardSlotId(slotModel.getPosition());
            slotId.setMatchId(matchId);
            slotEntity.setId(slotId);

            slotEntity.setPurchasePrice(slotModel.getPurchasePrice());

            //SetOwner
            if(slotModel.getOwner() != null){
                slotEntity.setOwnerId(slotModel.getOwner().getId());
            }

            if(slotModel instanceof ProvinceSlot){
                ProvinceSlot provinceSlot = (ProvinceSlot) slotModel;
                slotEntity.setBoardSlotTypeId(0);
                slotEntity.setRanchCount(provinceSlot.getRanchCount());
                slotEntity.setProvinceZoneId(provinceSlot.getProvinceZoneId());

            }

            if(slotModel instanceof RailwaySlot){
                // RailwaySlot railwaySlot = (RailwaySlot) slotModel;
                slotEntity.setBoardSlotTypeId(1);
                slotEntity.setRanchCount(0);
                slotEntity.setProvinceZoneId(null);

            }

            if(slotModel instanceof CompanySlot){
                // CompanySlot companySlot = (CompanySlot) slotModel;
                slotEntity.setBoardSlotTypeId(2);
                slotEntity.setRanchCount(0);
                slotEntity.setProvinceZoneId(null);

            }
            slotEntityList.add(slotEntity);
        }
        return slotEntityList;
    }

}
