package co.kr.nomadlab.springgdic.module.supply.model;

import co.kr.nomadlab.springgdic.common.BaseTime;
import co.kr.nomadlab.springgdic.module.supply.status.SupplyStatus;
import co.kr.nomadlab.springgdic.module.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SUPPLY")
public class Supply extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    private Integer viewCount;

    private Integer replyCount;

    @Enumerated(EnumType.STRING)
    private SupplyStatus supplyStatus;

    @ManyToOne
    private User user;
}
