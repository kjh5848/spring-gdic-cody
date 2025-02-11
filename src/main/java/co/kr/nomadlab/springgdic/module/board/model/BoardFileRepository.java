package co.kr.nomadlab.springgdic.module.board.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {

    List<BoardFile> findByBoardId(Long boardId);
}
