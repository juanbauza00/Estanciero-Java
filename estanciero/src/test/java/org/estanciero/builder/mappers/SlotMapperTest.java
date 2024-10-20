package org.estanciero.builder.mappers;

import org.estanciero.model.dbEntities.AuxiliarEntities.ProvinceZoneEntity;
import org.estanciero.model.dbEntities.AuxiliarEntities.RentalPriceEntity;
import org.estanciero.model.dbEntities.AuxiliarEntities.ZoneEntity;
import org.estanciero.model.dbEntities.BoardSlotEntity;
import org.estanciero.model.dbEntities.PurchasableSlotEntity;
import org.estanciero.model.dbEntities.PurchasableSlotId;
import org.estanciero.model.entities.player.BotPlayer;
import org.estanciero.model.entities.player.HumanPlayer;
import org.estanciero.model.entities.player.Player;
import org.estanciero.model.entities.player.enums.BotPlayStyle;
import org.estanciero.model.entities.slot.*;
import org.estanciero.model.entities.slot.enums.ProvinceName;
import org.estanciero.model.entities.slot.enums.ProvinceZone;
import org.estanciero.model.entities.special_cards.SpecialCard;
import org.estanciero.repositories.ProvinceZoneRepository;
import org.estanciero.repositories.RentalPriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.when;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.junit.jupiter.api.Assertions.*;

class SlotMapperTest {

    @Mock
    private RentalPriceRepository rentalPriceRepository;
    @Mock
    private ProvinceZoneRepository provinceZoneRepository;
    @Mock
    private List<BoardSlotEntity> slotEntities;
    @Mock
    private List<PurchasableSlotEntity> purchSlotEntities;
    @Mock
    private List<Slot> slotModels;
    @Mock
    private List<PurchasableSlot> purchSlotModels;
    @Mock
    private List<Player> players;
    @InjectMocks
    private SlotMapper slotMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // ----------------------------- PLAYERS -------------------------------------
        players = new ArrayList<>();
        //Human player model
        HumanPlayer humanPlayer = new HumanPlayer("humanEntity",1);
        humanPlayer.setProvinceCount(3);
        humanPlayer.setUpgradeCount(2);
        players.add(humanPlayer);

        //Bot player model
        BotPlayer botPlayer = new BotPlayer("botEntity", BotPlayStyle.AGGRESSIVE);
        botPlayer.setId(2);
        botPlayer.setRailwayCount(1);
        players.add(botPlayer);


        // ----------------------------- SLOT ENTITIES-------------------------------------
        // Not pruchasable slots entities
        slotEntities = new ArrayList<>();
        BoardSlotEntity jailEntity = new BoardSlotEntity();
        jailEntity.setBoardSlotId(14);
        jailEntity.setBoardSlotTypeId(3); // Jail
        slotEntities.add(jailEntity);

        BoardSlotEntity restEntity = new BoardSlotEntity();
        restEntity.setBoardSlotId(21);
        restEntity.setBoardSlotTypeId(4); // Rest
        slotEntities.add(restEntity);

        BoardSlotEntity taxEntity = new BoardSlotEntity();
        taxEntity.setBoardSlotId(41);
        taxEntity.setBoardSlotTypeId(5); // Tax
        taxEntity.setAmountPrice(2000);
        slotEntities.add(taxEntity);

        BoardSlotEntity prizeEntity = new BoardSlotEntity();
        prizeEntity.setBoardSlotId(7);
        prizeEntity.setBoardSlotTypeId(6); // Prize
        prizeEntity.setAmountPrice(2500);
        slotEntities.add(prizeEntity);

        // Purchasable slots entities
        purchSlotEntities = new ArrayList<>();
        PurchasableSlotEntity provinceEntity = new PurchasableSlotEntity();
        PurchasableSlotId provinceId = new PurchasableSlotId();
        provinceId.setBoardSlotId(34);
        provinceEntity.setId(provinceId);
        provinceEntity.setBoardSlotTypeId(0); // Province
        provinceEntity.setPurchasePrice(6400);
        provinceEntity.setProvinceZoneId(18);
        provinceEntity.setOwnerId(1);
        provinceEntity.setRanchCount(2);
        purchSlotEntities.add(provinceEntity);

        PurchasableSlotEntity railwayEntity = new PurchasableSlotEntity();
        PurchasableSlotId railwayId = new PurchasableSlotId();
        railwayId.setBoardSlotId(12);
        railwayEntity.setId(railwayId);
        railwayEntity.setBoardSlotTypeId(1); // Railway
        railwayEntity.setPurchasePrice(3600);
        railwayEntity.setOwnerId(2);
        purchSlotEntities.add(railwayEntity);

        PurchasableSlotEntity companyEntity = new PurchasableSlotEntity();
        PurchasableSlotId companyId = new PurchasableSlotId();
        companyId.setBoardSlotId(16);
        companyEntity.setId(companyId);
        companyEntity.setBoardSlotTypeId(2); // Company
        companyEntity.setPurchasePrice(3800);
        purchSlotEntities.add(companyEntity);

        // ----------------------------- SLOT MODELS-------------------------------------
        // Not purch slot models
        slotModels = new ArrayList<>();
        Slot jailSlot = new JailSlot(14);
        slotModels.add(jailSlot);

        Slot restSlot = new RestSlot(21);
        slotModels.add(restSlot);

        Slot taxSlot = new TaxSlot(41,2000);
        slotModels.add(taxSlot);

        Slot prizeSlot = new PrizeSlot(7,2500);
        slotModels.add(prizeSlot);

        // Purchasable slot models
        purchSlotModels = new ArrayList<>();
        //Province Slot
        ProvinceSlot provinceSlot = new ProvinceSlot(34);
        provinceSlot.setPurchasePrice(6400);
        int[] rentVal1 = {550, 2850, 8500, 19000, 23000, 27000};
        provinceSlot.setRentValue(rentVal1);
        provinceSlot.setProvinceZoneId(18);
        provinceSlot.setProvince(ProvinceName.CORDOBA);
        provinceSlot.setZone(ProvinceZone.NORTE);
        provinceSlot.setOwner(players.get(0));
        provinceSlot.setRanchCount(2);
        purchSlotModels.add(provinceSlot);

        //Railway Slot
        RailwaySlot railwaySlot = new RailwaySlot(12);
        railwaySlot.setPurchasePrice(3600);
        int[] rentVal2 = {500, 1000, 2000, 4000};
        railwaySlot.setRentValue(rentVal2);
        railwaySlot.setOwner(players.get(1));
        purchSlotModels.add(railwaySlot);

        //Company Slot
        CompanySlot companySlot = new CompanySlot(16);
        companySlot.setPurchasePrice(3800);
        int[] rentVal3 = {100,200};
        companySlot.setRentValue(rentVal3);
        purchSlotModels.add(companySlot);



        // ----------------------------- MOCK REPO RESPONSES -------------------------------------
        //Province Rent Prices
        RentalPriceEntity rentalPrice0 = new RentalPriceEntity(0,34,0,550);
        RentalPriceEntity rentalPrice1 = new RentalPriceEntity(1,34,1,2850);
        RentalPriceEntity rentalPrice2 = new RentalPriceEntity(2,34,2,8500);
        RentalPriceEntity rentalPrice3 = new RentalPriceEntity(3,34,3,19000);
        RentalPriceEntity rentalPrice4 = new RentalPriceEntity(4,34,4,23000);
        RentalPriceEntity rentalPrice5 = new RentalPriceEntity(5,34,5,27000);
        List<RentalPriceEntity> rentalPrices1 = new ArrayList<>(Arrays.asList(
                rentalPrice0,rentalPrice1,rentalPrice2,rentalPrice3,rentalPrice4,rentalPrice5
        ));

        //Railway Rent Prices
        RentalPriceEntity rentalPrice6 = new RentalPriceEntity(6,12,1,500);
        RentalPriceEntity rentalPrice7 = new RentalPriceEntity(7,12,2,1000);
        RentalPriceEntity rentalPrice8 = new RentalPriceEntity(8,12,3,2000);
        RentalPriceEntity rentalPrice9 = new RentalPriceEntity(9,12,4,4000);
        List<RentalPriceEntity> rentalPrices2 = new ArrayList<>(Arrays.asList(
                rentalPrice6,rentalPrice7,rentalPrice8,rentalPrice9
        ));

        //Company Rent Prices
        RentalPriceEntity rentalPrice10 = new RentalPriceEntity(10,16,1,100);
        RentalPriceEntity rentalPrice11 = new RentalPriceEntity(11,16,2,200);
        List<RentalPriceEntity> rentalPrices3 = new ArrayList<>(Arrays.asList(
                rentalPrice10,rentalPrice11
        ));

        when(this.rentalPriceRepository.findByBoardSlotId(34)).thenReturn(rentalPrices1);
        when(this.rentalPriceRepository.findByBoardSlotId(12)).thenReturn(rentalPrices2);
        when(this.rentalPriceRepository.findByBoardSlotId(16)).thenReturn(rentalPrices3);

        ZoneEntity zone = new ZoneEntity();
        zone.setZoneName("NORTE");
        ProvinceZoneEntity provinceZoneEntity = new ProvinceZoneEntity();
        provinceZoneEntity.setProvince("CORDOBA");
        provinceZoneEntity.setZone(zone);
        provinceZoneEntity.setRanchPrice(3000);
        when(provinceZoneRepository.findById(18)).thenReturn(Optional.of(provinceZoneEntity));
    }


    @Test
    void mapNoPurchSlotEntityToModel() {
        List<Slot> noPurchSlotList = slotMapper.mapNoPurchSlotEntityToModel(slotEntities);

        assertNotNull(noPurchSlotList);
        assertEquals(4, noPurchSlotList.size());
        //Jail, rest, tax, prize

        assertInstanceOf(JailSlot.class, noPurchSlotList.get(0));
        JailSlot jailSlot = (JailSlot) noPurchSlotList.get(0);
        assertEquals(14, jailSlot.getPosition());

        assertInstanceOf(RestSlot.class, noPurchSlotList.get(1));
        RestSlot restSlot = (RestSlot) noPurchSlotList.get(1);
        assertEquals(21, restSlot.getPosition());

        assertInstanceOf(TaxSlot.class, noPurchSlotList.get(2));
        TaxSlot taxSlot = (TaxSlot) noPurchSlotList.get(2);
        assertEquals(41, taxSlot.getPosition());
        assertEquals(2000, taxSlot.getTax());

        assertInstanceOf(PrizeSlot.class, noPurchSlotList.get(3));
        PrizeSlot prizeSlot = (PrizeSlot) noPurchSlotList.get(3);
        assertEquals(7, prizeSlot.getPosition());
        assertEquals(2500, prizeSlot.getAmount());

    }

    @Test
    void mapPurchSlotEntityToModel() {
        List<Slot> purchSlotList = slotMapper.mapPurchSlotEntityToModel(purchSlotEntities, Optional.ofNullable(players));
        assertNotNull(purchSlotList);

        assertInstanceOf(ProvinceSlot.class, purchSlotList.get(0));
        ProvinceSlot provinceSlot = (ProvinceSlot) purchSlotList.get(0);
        assertEquals(34, provinceSlot.getPosition());
        assertEquals(6400, provinceSlot.getPurchasePrice());
        assertEquals(18, provinceSlot.getProvinceZoneId());
        assertEquals(ProvinceName.CORDOBA, provinceSlot.getProvince());
        assertEquals(ProvinceZone.NORTE, provinceSlot.getZone());
        assertEquals(2, provinceSlot.getRanchCount());
        int[] rentVal1 = {550, 2850, 8500, 19000, 23000, 27000};
        assertArrayEquals(rentVal1, provinceSlot.rentValue);
        assertEquals(players.get(0), provinceSlot.getOwner());


        assertInstanceOf(RailwaySlot.class, purchSlotList.get(1));
        RailwaySlot railwaySlot = (RailwaySlot) purchSlotList.get(1);
        assertEquals(12, railwaySlot.getPosition());
        assertEquals(3600, railwaySlot.getPurchasePrice());
        int[] rentVal2 = {500, 1000, 2000, 4000};
        assertArrayEquals(rentVal2, railwaySlot.rentValue);
        assertEquals(players.get(1), railwaySlot.getOwner());

        assertInstanceOf(CompanySlot.class, purchSlotList.get(2));
        CompanySlot companySlot = (CompanySlot) purchSlotList.get(2);
        assertEquals(16, companySlot.getPosition());
        assertEquals(3800, companySlot.getPurchasePrice());
        int[] rentVal3 = {100,200};
        assertArrayEquals(rentVal3, companySlot.rentValue);
        assertNull(companySlot.getOwner());

    }

    @Test
    void insertOwner() {
        Player testPlayer1 = this.slotMapper.insertOwner(Optional.ofNullable(players),1);
        Player testPlayer2 = this.slotMapper.insertOwner(Optional.ofNullable(players),2);
        Player testPlayer3 = this.slotMapper.insertOwner(Optional.ofNullable(players),3);
        assertEquals(players.get(0), testPlayer1);
        assertEquals(players.get(1), testPlayer2);
        assertNull(testPlayer3);
    }

    @Test
    void mapRentalPrices() {
        int[] testVal1 = this.slotMapper.mapRentalPrices(purchSlotEntities.get(0));
        int[] testVal2 = this.slotMapper.mapRentalPrices(purchSlotEntities.get(1));
        int[] testVal3 = this.slotMapper.mapRentalPrices(purchSlotEntities.get(2));

        int[] rentVal1 = {550, 2850, 8500, 19000, 23000, 27000};
        int[] rentVal2 = {500, 1000, 2000, 4000};
        int[] rentVal3 = {100,200};

        assertArrayEquals(rentVal1,testVal1);
        assertArrayEquals(rentVal2,testVal2);
        assertArrayEquals(rentVal3,testVal3);
    }

    @Test
    void mapPurchSlotModelToEntity() {
        List<PurchasableSlotEntity> slotEntityTestList = this.slotMapper.mapPurchSlotModelToEntity(purchSlotModels,1);
        assertNotNull(slotEntityTestList);
        assertThat(slotEntityTestList, everyItem(instanceOf(PurchasableSlotEntity.class)));
        int matchId = 1;

        PurchasableSlotEntity provinceSlotEntity = slotEntityTestList.get(0);
        int idProvince = 34;

        PurchasableSlotEntity railwaySlotEntity = slotEntityTestList.get(1);
        int idRailway = 12;

        PurchasableSlotEntity companySlotEntity = slotEntityTestList.get(2);
        int idCompany = 16;

        assertEquals(idProvince, provinceSlotEntity.getId().getBoardSlotId());
        assertEquals(matchId, provinceSlotEntity.getId().getMatchId());
        assertEquals(6400, provinceSlotEntity.getPurchasePrice());
        assertEquals(18, provinceSlotEntity.getProvinceZoneId());
        assertEquals(2, provinceSlotEntity.getRanchCount());
        assertEquals(players.get(0).getId(), provinceSlotEntity.getOwnerId());

        assertEquals(idRailway, railwaySlotEntity.getId().getBoardSlotId());
        assertEquals(matchId, railwaySlotEntity.getId().getMatchId());
        assertEquals(3600, railwaySlotEntity.getPurchasePrice());
        assertNull(railwaySlotEntity.getProvinceZoneId());
        assertEquals(0, railwaySlotEntity.getRanchCount());
        assertEquals(players.get(1).getId(), railwaySlotEntity.getOwnerId());

        assertEquals(idCompany, companySlotEntity.getId().getBoardSlotId());
        assertEquals(matchId, companySlotEntity.getId().getMatchId());
        assertEquals(3800, companySlotEntity.getPurchasePrice());
        assertNull(companySlotEntity.getProvinceZoneId());
        assertEquals(0, companySlotEntity.getRanchCount());
        assertNull(companySlotEntity.getOwnerId());
    }
}