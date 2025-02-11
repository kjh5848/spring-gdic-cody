package co.kr.nomadlab.springgdic.consts;

import co.kr.nomadlab.springgdic.module.board.model.Board;
import co.kr.nomadlab.springgdic.module.board.status.BoardStatus;
import org.springframework.data.domain.PageRequest;

public interface BoardConst {
    Board board = new Board(1L, "첫 번째 게시물 입니다.", "첫 번째 게시물 내용", 0, 0, BoardStatus.ACTIVE,  UserConst.user);

    PageRequest pageRequest = PageRequest.of(1, 10);
}
