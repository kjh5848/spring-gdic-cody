package co.kr.nomadlab.springgdic.module.supply.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SUPPLY_FILE")
public class SupplyFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    private Long id;

    @Comment("공급공고 파일명")
    private String fileName;

    @Comment("공급공고 파일(Base64)")
    @Lob
    private String fileContent;

    @Comment("공급공고 파일 확장자")
    private String fileExtension;

    @Comment("공급공고 파일 크기")
    private Long fileSize;

    @Comment("공급공고 게시글")
    @ManyToOne
    private Supply supply;
}
