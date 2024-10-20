package org.estanciero.model.dbEntities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="MATCHES")
public class MatchEntity {

    @Id
    @Column(name = "match_id")
    private int matchId;

    @Column(name = "match_date")
    @CreationTimestamp
    private LocalDateTime matchDate;

    @Column(name = "money_goal")
    private Integer moneyGoal;

    @Column(name = "game_difficulty")
    private Integer gameDifficulty;

    @Column(name = "game_mode")
    private Integer gameMode;



//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "match_id")
//    private Set<PurchasableSlotEntity> purchasableSlotEntities = new HashSet<>();



//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "match_id", referencedColumnName = "match_id")
//    private Set<SpecialCardEntity> specialCardEntities = new HashSet<>();
}
