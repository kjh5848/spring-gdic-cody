package co.kr.nomadlab.springgdic.module.board.model;

import co.kr.nomadlab.springgdic.common.BaseTime;
import co.kr.nomadlab.springgdic.module.board.status.BoardStatus;
import co.kr.nomadlab.springgdic.module.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "BOARD")
public class Board extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    private Integer viewCount;

    private Integer replyCount;

    @Enumerated(EnumType.STRING)
    private BoardStatus boardStatus;

    @ManyToOne
    private User user;

    public Board(Long id, String title, String content, Integer viewCount, Integer replyCount, BoardStatus boardStatus, User user, LocalDateTime createdDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.replyCount = replyCount;
        this.boardStatus = boardStatus;
        this.user = user;
    }
}
