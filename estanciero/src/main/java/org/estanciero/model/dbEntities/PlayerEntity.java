package org.estanciero.model.dbEntities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.estanciero.model.dbEntities.enums.PlayerTypes;
import org.estanciero.model.entities.player.enums.BotPlayStyle;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="PLAYERS")
public class PlayerEntity {
    @Id
    @Column(name = "player_id")
    private int playerId;

//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_seq_gen")
//    @SequenceGenerator(name = "player_seq_gen", sequenceName = "players_seq", allocationSize = 1)
//    @Column(name = "player_id")
//    private int playerId;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "player_style_id")
    private BotPlayStyle playerStyleId;

    @Column(name = "player_type_id")
    private int playerTypeId;

    @Column(name = "player_name")
    private String playerName;

    @Column(name = "player_money")
    private int playerMoney;

    @Column(name = "player_position")
    private int playerPosition;

    @Column(name = "is_player_in_jail")
    private boolean isPlayerInJail;

    @Column(name = "amount_of_free_jail_cards")
    private int amountOfFreeJailCards;

    @Column(name = "province_count")
    private int provinceCount;

    @Column(name = "railway_count")
    private int railwayCount;

    @Column(name = "company_count")
    private int companyCount;

    @Column(name = "upgrade_count")
    private int upgradeCount;

    @Column(name = "rest_count")
    private int restCount;

    @Column(name = "is_player_turn")
    private boolean isPlayerTurn;

    @Column(name = "is_bankrupt")
    private boolean isBankrupt;

    @Column(name = "match_id")
    private int matchId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private List<PurchasableSlotEntity> purchasableSlotEntityList;

//    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<PurchasableSlotEntity> purchasableSlotEntityList;

}
