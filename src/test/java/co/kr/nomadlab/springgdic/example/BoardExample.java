package co.kr.nomadlab.springgdic.example;

import co.kr.nomadlab.springgdic.common.RoleType;
import co.kr.nomadlab.springgdic.module.board.model.Board;
import co.kr.nomadlab.springgdic.module.board.status.BoardStatus;
import co.kr.nomadlab.springgdic.module.user.User;
import co.kr.nomadlab.springgdic.module.user.UserStatus;
import net.bytebuddy.description.annotation.AnnotationValue;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public interface BoardExample {

    Board board = new Board(1L, "제목", "내용", 10, 20, BoardStatus.ACTIVE
        , new User(1L, "ssar", "1234", "ssar@nate.com", RoleType.USER, UserStatus.ACTIVE)
    );

    PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("id").descending().and(Sort.by("title").ascending()));
//    PageRequest pageRequest = PageRequest.of(0, 10);

}