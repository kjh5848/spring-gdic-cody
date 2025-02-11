package co.kr.nomadlab.springgdic.mock;

import co.kr.nomadlab.springgdic.common.RoleType;
import co.kr.nomadlab.springgdic.config.SecurityConfig;
import co.kr.nomadlab.springgdic.example.BoardExample;
import co.kr.nomadlab.springgdic.module.board.controller.BoardViewController;
import co.kr.nomadlab.springgdic.module.board.model.Board;
import co.kr.nomadlab.springgdic.module.board.model.BoardFile;
import co.kr.nomadlab.springgdic.module.board.service.BoardFileService;
import co.kr.nomadlab.springgdic.module.board.service.BoardService;
import co.kr.nomadlab.springgdic.module.board.status.BoardStatus;
import co.kr.nomadlab.springgdic.module.user.User;
import co.kr.nomadlab.springgdic.module.user.UserStatus;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(BoardViewController.class)
@Import(SecurityConfig.class)
public class BoardMockTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private BoardFileService boardFileService;

    @MockBean
    private BoardService boardService;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(
                        new BoardViewController(boardService, boardFileService))
                .build();
    }

    @Test
    @DisplayName("공지사항 조회")
    void notice() throws Exception {

        User user1 = new User(1L, "ssar", "1234", "ssar@nate.com", RoleType.USER, UserStatus.ACTIVE);
        User user2 = new User(2L, "cos", "1234", "cos@nate.com", RoleType.USER, UserStatus.ACTIVE);

        List<Board> boardList = List.of(
                new Board(1L, "공지사항 제목", "공지사항 내용", 10, 5, BoardStatus.ACTIVE, user1, LocalDateTime.of(2023, 6, 23, 10, 30)),
                new Board(2L, "공지사항 제목2", "공지사항 내용2", 20, 10, BoardStatus.ACTIVE, user2, LocalDateTime.of(2023, 6, 23, 10, 30))
        );


        Pageable pageable = BoardExample.pageRequest;

        Page<Board> boardPage = new PageImpl<>(boardList, pageable, boardList.size());

        given(boardService.findAll(pageable)).willReturn(boardPage);


        MockHttpServletRequestBuilder getRequest = get("/board/notice")
                .param("page", String.valueOf(pageable.getPageNumber()))
                .param("size", String.valueOf(pageable.getPageSize()));

        pageable.getSort().get().forEach(
                order -> getRequest.param("sort", order.getProperty() + "," + order.getDirection())
        );

        ResultActions resultActions = this.mvc.perform(
                        getRequest
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(view().name("board/notice"))
                                .andExpect(model().attribute("boardPage", boardPage))
                                .andExpect(model().size(6))
                                .andExpect(model().attributeExists(
                                        "prev", "next", "startPage", "endPage", "basePage", "boardPage")
                                );

        resultActions.andExpectAll(status().isOk());
        assertThat(boardList.size()).isEqualTo(2);
        assertThat(boardList.get(0).getTitle()).isEqualTo("공지사항 제목");
        assertThat(boardList.get(0).getContent()).isEqualTo("공지사항 내용");
        assertThat(boardList.get(0).getViewCount()).isEqualTo(10);
        assertThat(boardList.get(0).getReplyCount()).isEqualTo(5);
        assertThat(boardList.get(0).getBoardStatus().toString()).isEqualTo("ACTIVE");

        assertThat(boardList.get(0).getUser().getUsername()).isEqualTo("ssar");
        assertThat(boardList.get(0).getUser().getPassword()).isEqualTo("1234");
        assertThat(boardList.get(0).getUser().getEmail()).isEqualTo("ssar@nate.com");
        assertThat(boardList.get(0).getUser().getRole().toString()).isEqualTo("USER");
        assertThat(boardList.get(0).getUser().getStatus().toString()).isEqualTo("ACTIVE");
    }

    @Test
    @DisplayName("공지사항 상세 조회")
    void noticeDetail() throws Exception {

        Long id = 1L;

        User user1 = new User(1L, "ssar", "1234", "ssar@nate.com", RoleType.USER, UserStatus.ACTIVE);
        User user2 = new User(2L, "cos", "1234", "cos@nate.com", RoleType.USER, UserStatus.ACTIVE);

        List<Board> boardList = List.of(
                new Board(1L, "공지사항 제목", "공지사항 내용", 10, 5, BoardStatus.ACTIVE, user1, LocalDateTime.of(2023, 6, 23, 10, 30)),
                new Board(2L, "공지사항 제목2", "공지사항 내용2", 20, 10, BoardStatus.ACTIVE, user2, LocalDateTime.of(2023, 6, 23, 10, 30))
        );

        List<BoardFile> boardFileList = List.of(
                new BoardFile(1L, "fileName1", "fileName1", "jpg", 1L, boardList.get(0)),
                new BoardFile(2L, "fileName2", "fileName2", "jpg", 1L, boardList.get(1))
        );

        Pageable pageable = BoardExample.pageRequest;

        Page<Board> boardPage = new PageImpl<>(boardList, pageable, boardList.size());

        given(boardService.findAll(pageable)).willReturn(boardPage);

        Board board = boardList.get(0);
        given(boardService.getBoard(id)).willReturn(Optional.of(board));

        given(boardFileService.getBoardFileList(board.getId())).willReturn(boardFileList);

        MockHttpServletRequestBuilder getRequest = get("/board/notice/"+ id)
                .param("page", String.valueOf(pageable.getPageNumber()))
                .param("size", String.valueOf(pageable.getPageSize()));

        pageable.getSort().get().forEach(
                order -> getRequest.param("sort", order.getProperty() + "," + order.getDirection())
        );

        ResultActions resultActions = this.mvc.perform(
                        getRequest
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf())) .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(view().name("board/detail"))
                                .andExpect(model().attribute("boardPage", boardPage))
                                .andExpect(model().attribute("board", board))
                                .andExpect(model().attribute("fileList", boardFileList))
                                .andExpect(model().size(8))
                                .andExpect(model().attributeExists(
                                        "prev", "next", "startPage", "endPage", "basePage", "boardPage", "board", "fileList")
                                );
        resultActions.andExpectAll(status().isOk());
        assertThat(boardList.size()).isEqualTo(2);
        assertThat(boardList.get(0).getTitle()).isEqualTo("공지사항 제목");
        assertThat(boardList.get(0).getContent()).isEqualTo("공지사항 내용");
        assertThat(boardList.get(0).getViewCount()).isEqualTo(10);
        assertThat(boardList.get(0).getReplyCount()).isEqualTo(5);
        assertThat(boardList.get(0).getBoardStatus().toString()).isEqualTo("ACTIVE");

        assertThat(boardList.get(0).getUser().getUsername()).isEqualTo("ssar");
        assertThat(boardList.get(0).getUser().getPassword()).isEqualTo("1234");
        assertThat(boardList.get(0).getUser().getEmail()).isEqualTo("ssar@nate.com");
        assertThat(boardList.get(0).getUser().getRole().toString()).isEqualTo("USER");
        assertThat(boardList.get(0).getUser().getStatus().toString()).isEqualTo("ACTIVE");

        assertThat(boardFileList.get(0).getFileName()).isEqualTo("fileName1");
        assertThat(boardFileList.get(0).getFileExtension()).isEqualTo("jpg");
        assertThat(boardFileList.get(0).getFileContent()).isEqualTo("fileName1");
        assertThat(boardFileList.get(0).getFileSize()).isEqualTo(1L);
    }


}

