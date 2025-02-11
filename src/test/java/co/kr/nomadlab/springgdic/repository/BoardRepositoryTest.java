package co.kr.nomadlab.springgdic.repository;

import co.kr.nomadlab.springgdic.module.board.model.Board;
import co.kr.nomadlab.springgdic.module.board.model.BoardRepository;
import co.kr.nomadlab.springgdic.module.board.status.BoardStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("BoardRepository 테스트")
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    public void init() {
        setUp("공지사항 제목", "공지사항 내용",  10, 5, BoardStatus.ACTIVE);
    }

    public Board setUp (
            String title,
            String content,
            Integer viewCount,
            Integer replyCount,
            BoardStatus boardStatus
    ) {

        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
        board.setViewCount(viewCount);
        board.setReplyCount(replyCount);
        board.setBoardStatus(boardStatus);

        return testEntityManager.persist(board);
    }

    @Test
    @DisplayName("공지사항 조회 테스트")
    void selectBoard() {

        List<Board> boardList = boardRepository.findAll();
        Assertions.assertNotEquals(boardList.size(), 0);

        Board board = boardList.get(0);
        Assertions.assertEquals(board.getTitle(), "【변경공고】 김해대동첨단일반산업단지 조성사업 생활대책용지 공급 【변경공고】");

    }

    @Test
    @DisplayName("공지사항 등록 및 삭제 테스트")
    void insertBoard() {
        Board board = setUp("제목입니다.", "내용입니다.", 10, 5, BoardStatus.ACTIVE);
        boardRepository.findById(board.getId()).ifPresentOrElse(
                findBoard -> Assertions.assertEquals(findBoard.getTitle(), "제목입니다."),
                () -> Assertions.fail("공지사항이 존재하지 않습니다.")
        );

        testEntityManager.remove(board);
        boardRepository.findById(board.getId()).ifPresentOrElse(
                findBoard -> Assertions.fail("공지사항이 존재합니다."),
                () -> Assertions.assertTrue(true)
        );

    }

    @Test
    @DisplayName("공지사항 수정 테스트")
    void updateBoard() {

        Board board = setUp("제목입니다.", "내용입니다.", 10, 5, BoardStatus.ACTIVE);

        boardRepository.findById(board.getId()).ifPresentOrElse(
                findBoard -> {
                    findBoard.setTitle("제목입니다-수정");
                    testEntityManager.persist(findBoard);
                },
                () -> Assertions.fail("공지사항이 존재하지 않습니다.")
        );

        boardRepository.findById(board.getId()).ifPresentOrElse(
                findBoard -> Assertions.assertEquals(findBoard.getTitle(), "제목입니다-수정"),
                () -> Assertions.fail("공지사항이 존재하지 않습니다.")
        );
    }


}
