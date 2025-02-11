package co.kr.nomadlab.springgdic.module.board.model;

import co.kr.nomadlab.springgdic.module.board.status.BoardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findAllByBoardStatus(Pageable pageable, BoardStatus boardStatus);
}
