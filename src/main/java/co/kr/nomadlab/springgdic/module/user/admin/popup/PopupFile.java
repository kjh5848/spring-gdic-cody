package co.kr.nomadlab.springgdic.module.user.admin.popup;

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
public class PopupFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    private Long id;

    @Comment("팝업 파일명")
    private String fileName;

    @Comment("팝업 파일(Base64)")
    @Lob
    private String fileContent;

    @Comment("팝업 파일 확장자")
    private String fileExtension;

    @Comment("팝업 파일 크기")
    private Long fileSize;

    private String showYn;

    private String link;

}
