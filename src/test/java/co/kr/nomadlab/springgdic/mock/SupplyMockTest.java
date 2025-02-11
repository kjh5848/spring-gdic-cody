//package co.kr.nomadlab.springgdic.mock;
//
//
//import co.kr.nomadlab.springgdic.common.RoleType;
//import co.kr.nomadlab.springgdic.config.SecurityConfig;
//import co.kr.nomadlab.springgdic.example.BoardExample;
//import co.kr.nomadlab.springgdic.module.board.controller.BoardViewController;
//import co.kr.nomadlab.springgdic.module.board.model.Board;
//import co.kr.nomadlab.springgdic.module.board.status.BoardStatus;
//import co.kr.nomadlab.springgdic.module.supply.controller.SupplyController;
//import co.kr.nomadlab.springgdic.module.supply.model.Supply;
//import co.kr.nomadlab.springgdic.module.supply.service.SupplyFileService;
//import co.kr.nomadlab.springgdic.module.supply.service.SupplyService;
//import co.kr.nomadlab.springgdic.module.supply.status.SupplyStatus;
//import co.kr.nomadlab.springgdic.module.user.User;
//import co.kr.nomadlab.springgdic.module.user.UserStatus;
//import org.junit.Before;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.testng.annotations.Test;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@MockBean(JpaMetamodelMappingContext.class)
//@WebMvcTest(BoardViewController.class)
//@Import(SecurityConfig.class)
//public class SupplyMockTest {
//
//    @Autowired
//    MockMvc mvc;
//
//    @MockBean
//    private SupplyService supplyService;
//
//    @MockBean
//    private SupplyFileService supplyFileService;
//
//    @Before
//    public void setUp() {
//        mvc = MockMvcBuilders.standaloneSetup(
//                        new SupplyController(supplyService, supplyFileService))
//                .build();
//    }
//
//    @Test
//    void notice() {
//
//        User user1 = new User(1L, "ssar", "1234", "ssar@nate.com", RoleType.USER, UserStatus.ACTIVE);
//        User user2 = new User(2L, "cos", "1234", "cos@nate.com", RoleType.USER, UserStatus.ACTIVE);
//
//        List<Supply> supplyList = List.of(
//                new Supply(1L, "공지사항 제목", "공지사항 내용", 10, 5, SupplyStatus.ACTIVE, user1, LocalDateTime.of(2023, 6, 23, 10, 30)),
//                new Supply(2L, "공지사항 제목2", "공지사항 내용2", 20, 10, SupplyStatus.ACTIVE, user2, LocalDateTime.of(2023, 6, 23, 10, 30))
//        );
//
//
//        Pageable pageable = BoardExample.pageRequest;
//
//        Page<Board> boardPage = new PageImpl<>(boardList, pageable, boardList.size());
//
//        given(boardService.findAll(pageable)).willReturn(boardPage);
//
//
//        MockHttpServletRequestBuilder getRequest = get("/board/notice")
//                .param("page", String.valueOf(pageable.getPageNumber()))
//                .param("size", String.valueOf(pageable.getPageSize()));
//
//        pageable.getSort().get().forEach(
//                order -> getRequest.param("sort", order.getProperty() + "," + order.getDirection())
//        );
//
//        ResultActions resultActions = this.mvc.perform(
//                        getRequest
//                                .accept(MediaType.APPLICATION_JSON)
//                                .with(csrf()))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(view().name("board/notice"))
//                .andExpect(model().attribute("boardPage", boardPage))
//                .andExpect(model().size(6))
//                .andExpect(model().attributeExists(
//                        "prev", "next", "startPage", "endPage", "basePage", "boardPage")
//                );
//
//        resultActions.andExpectAll(status().isOk());
//        assertThat(boardList.size()).isEqualTo(2);
//        assertThat(boardList.get(0).getTitle()).isEqualTo("공지사항 제목");
//        assertThat(boardList.get(0).getContent()).isEqualTo("공지사항 내용");
//        assertThat(boardList.get(0).getViewCount()).isEqualTo(10);
//        assertThat(boardList.get(0).getReplyCount()).isEqualTo(5);
//        assertThat(boardList.get(0).getBoardStatus().toString()).isEqualTo("ACTIVE");
//
//        assertThat(boardList.get(0).getUser().getUsername()).isEqualTo("ssar");
//        assertThat(boardList.get(0).getUser().getPassword()).isEqualTo("1234");
//        assertThat(boardList.get(0).getUser().getEmail()).isEqualTo("ssar@nate.com");
//        assertThat(boardList.get(0).getUser().getRole().toString()).isEqualTo("USER");
//        assertThat(boardList.get(0).getUser().getStatus().toString()).isEqualTo("ACTIVE");
//    }
//    }
//}
