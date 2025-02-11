package co.kr.nomadlab.springgdic.mock;

import co.kr.nomadlab.springgdic.config.SecurityConfig;
import co.kr.nomadlab.springgdic.consts.AdminConst;
import co.kr.nomadlab.springgdic.consts.UserConst;
import co.kr.nomadlab.springgdic.module.board.dto.BoardSaveRequest;
import co.kr.nomadlab.springgdic.module.board.dto.BoardUpdateRequest;
import co.kr.nomadlab.springgdic.module.board.model.Board;
import co.kr.nomadlab.springgdic.module.board.model.BoardFile;
import co.kr.nomadlab.springgdic.module.board.model.Customer;
import co.kr.nomadlab.springgdic.module.board.service.BoardFileService;
import co.kr.nomadlab.springgdic.module.board.service.BoardService;
import co.kr.nomadlab.springgdic.module.board.service.CustomerService;
import co.kr.nomadlab.springgdic.module.board.status.BoardStatus;
import co.kr.nomadlab.springgdic.module.supply.model.Supply;
import co.kr.nomadlab.springgdic.module.supply.model.SupplyFile;
import co.kr.nomadlab.springgdic.module.supply.service.SupplyService;
import co.kr.nomadlab.springgdic.module.supply.status.SupplyStatus;
import co.kr.nomadlab.springgdic.module.user.admin.AdminController;
import co.kr.nomadlab.springgdic.module.user.admin.popup.PopupFile;
import co.kr.nomadlab.springgdic.module.user.admin.popup.PopupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(SecurityConfig.class)
@DisplayName("관리자 페이지 모킹 테스트")
public class AdminMockTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardService boardService;

    @MockBean
    private BoardFileService boardFileService;

    @MockBean
    private SupplyService supplyService;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private PopupService popupService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("관심고객 리스트 조회")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getCustomerList() throws Exception {
        Customer customer = AdminConst.customer;
        PageRequest pageable = AdminConst.pageRequest;
        Page<Customer> customerPage = new PageImpl<>(List.of(customer), pageable, 1);

        MockHttpServletRequestBuilder getRequest = get("/admin")
                .param("page", String.valueOf(pageable.getPageNumber()))
                .param("size", String.valueOf(pageable.getPageSize()));

        pageable.getSort().get().forEach(
                order -> getRequest.param("sort", order.getProperty() + "," + order.getDirection())
        );

        given(this.customerService.findAll(pageable)).willReturn(customerPage);

        ResultActions perform = this.mockMvc.perform(
                        getRequest
                )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("customerPage"));

        assertEquals(customer.getId(), 1L);
        assertEquals(customer.getEnterpriseName(), "enterPriseName");
        assertEquals(customer.getManagerName(), "managerName");
        assertEquals(customer.getTel(), "010-1234-1234");
        assertEquals(customer.getAddress(), "customerAddress");
        assertEquals(customer.getInquiry(), "문의내용");
    }

    @Test
    @DisplayName("공지사항 리스트 조회")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getNoticeList() throws Exception {
        Board board = AdminConst.board;
        PageRequest pageable = AdminConst.pageRequest;
        Page<Board> boardPage = new PageImpl<>(List.of(board), pageable, 1);

        MockHttpServletRequestBuilder getRequest = get("/admin/notice")
                .param("page", String.valueOf(pageable.getPageNumber()))
                .param("size", String.valueOf(pageable.getPageSize()));

        pageable.getSort().get().forEach(
                order -> getRequest.param("sort", order.getProperty() + "," + order.getDirection())
        );

        given(this.boardService.findAll(pageable)).willReturn(boardPage);

        ResultActions perform = this.mockMvc.perform(
                        getRequest
                )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("page"));

        assertEquals(board.getId(), 1L);
        assertEquals(board.getTitle(), "첫 번째 게시물 입니다.");
        assertEquals(board.getContent(), "첫 번째 게시물 내용");
        assertEquals(board.getViewCount(), 0);
        assertEquals(board.getReplyCount(), 0);
        assertEquals(board.getBoardStatus(), BoardStatus.ACTIVE);
        assertEquals(board.getUser(), UserConst.user);
    }

    @Test
    @DisplayName("공지사항 등록 페이지")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getNoticeAddPage() throws Exception {

        this.mockMvc.perform(
                        get("/admin/notice/add")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("admin/notice_add"));
    }

    @Test
    @DisplayName("공지사항 수정 페이지")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getNoticeUpdatePage() throws Exception {

        Board board = AdminConst.board;
        BoardFile boardFile = AdminConst.boardFile;

        given(this.boardService.getBoard(1L)).willReturn(
                Optional.of(AdminConst.board)
        );

        this.mockMvc.perform(
                        get("/admin/notice/{noticeId}/edit", 1L)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("admin/notice_edit"))
                .andExpect(model().attributeExists("board"))
                .andExpect(model().attributeExists("fileList"));

        assertEquals(board.getId(), 1L);
        assertEquals(board.getTitle(), "첫 번째 게시물 입니다.");
        assertEquals(board.getContent(), "첫 번째 게시물 내용");
        assertEquals(board.getViewCount(), 0);
        assertEquals(board.getReplyCount(), 0);
        assertEquals(board.getBoardStatus(), BoardStatus.ACTIVE);
        assertEquals(board.getUser(), UserConst.user);

        assertEquals(boardFile.getId(), 1L);
        assertEquals(boardFile.getFileName(), "fileName");
        assertEquals(boardFile.getFileContent(), "fileContent");
        assertEquals(boardFile.getFileExtension(), "fileExtension");
        assertEquals(boardFile.getFileSize(), 100L);
        assertEquals(boardFile.getBoard(), board);
    }
    
    @Test
    @DisplayName("공지사항 등록 실패")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void saveNoticeValidFail() throws Exception {

        ResultActions perform = this.mockMvc.perform(
                post("/admin/notice-save")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("title=&content=첫 번째 내용입니다.")
        );

        perform
                .andExpect(status().isBadRequest())
                .andDo(print());

        assertEquals(perform.andReturn().getResponse().getContentAsString(), "<script>alert('제목을 입력해주세요.');history.back();</script>");
    }

    @Test
    @DisplayName("공지사항 등록")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void addNotice() throws Exception {

        BoardSaveRequest request = new BoardSaveRequest("title", "content", null);

        this.boardService.saveNotice(request);

        this.mockMvc.perform(
                        post("/admin/notice-save")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content("title=title&content=content")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/notice"));
    }

    @Test
    @DisplayName("공지사항 수정실패")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void updateNoticeValidFail() throws Exception {
        Board board = AdminConst.board;

        given(this.boardService.getBoard(board.getId())).willReturn(
                Optional.of(board)
        );

        ResultActions perform = this.mockMvc.perform(
                post("/admin/notice-edit/{noticeId}", board.getId())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("title=&content=content")
        );

        perform
                .andExpect(status().isBadRequest())
                .andDo(print());

        assertEquals(perform.andReturn().getResponse().getContentAsString(), "<script>alert('제목을 입력해주세요.');history.back();</script>");
    }

    @Test
    @DisplayName("공급공고 리스트 조회")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getSupplyList() throws Exception {
        Supply supply = AdminConst.supply;
        PageRequest pageable = AdminConst.pageRequest;
        Page<Supply> supplyPage = new PageImpl<>(List.of(supply), pageable, 1);

        MockHttpServletRequestBuilder getRequest = get("/admin/supply")
                .param("page", String.valueOf(pageable.getPageNumber()))
                .param("size", String.valueOf(pageable.getPageSize()));

        pageable.getSort().get().forEach(
                order -> getRequest.param("sort", order.getProperty() + "," + order.getDirection())
        );

        given(this.supplyService.findAll(pageable)).willReturn(supplyPage);

        this.mockMvc.perform(
                        getRequest
                )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("page"));

        assertEquals(supply.getId(), 1L);
        assertEquals(supply.getTitle(), "첫 번째 공급공고");
        assertEquals(supply.getContent(), "첫 번째 공급공고 내용");
        assertEquals(supply.getViewCount(), 0);
        assertEquals(supply.getReplyCount(), 0);
        assertEquals(supply.getSupplyStatus(), SupplyStatus.ACTIVE);
        assertEquals(supply.getUser(), UserConst.user);
    }

    @Test
    @DisplayName("공급공고 등록 페이지")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getSupplyAddPage() throws Exception {

        this.mockMvc.perform(
                        get("/admin/supply/add")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("admin/supply_add"));
    }

    @Test
    @DisplayName("공급공고 수정 페이지")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getSupplyUpdatePage() throws Exception {

        Supply supply = AdminConst.supply;
        SupplyFile supplyFile = AdminConst.supplyFile;

        given(this.supplyService.getSupply(1L)).willReturn(
                Optional.of(AdminConst.supply)
        );

        this.mockMvc.perform(
                        get("/admin/supply/{supplyId}/edit", 1L)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("admin/supply_edit"))
                .andExpect(model().attributeExists("supply"))
                .andExpect(model().attributeExists("fileList"));

        assertEquals(supply.getId(), 1L);
        assertEquals(supply.getTitle(), "첫 번째 공급공고");
        assertEquals(supply.getContent(), "첫 번째 공급공고 내용");
        assertEquals(supply.getViewCount(), 0);
        assertEquals(supply.getReplyCount(), 0);
        assertEquals(supply.getSupplyStatus(), SupplyStatus.ACTIVE);
        assertEquals(supply.getUser(), UserConst.user);

        assertEquals(supplyFile.getId(), 1L);
        assertEquals(supplyFile.getFileName(), "fileName");
        assertEquals(supplyFile.getFileContent(), "fileContent");
        assertEquals(supplyFile.getFileExtension(), "fileExtension");
        assertEquals(supplyFile.getFileSize(), 100L);
        assertEquals(supplyFile.getSupply(), supply);
    }

    @Test
    @DisplayName("팝업 등록 페이지")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getPopupAddPage() throws Exception {

        PopupFile popupFile = AdminConst.popupFile;
        given(this.popupService.getPopup()).willReturn(
                List.of(popupFile)
        );

        this.mockMvc.perform(
                        get("/admin/popup")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("admin/popup"))
                .andExpect(model().attributeExists("popupFile"));

        assertEquals(popupFile.getId(), 1L);
        assertEquals(popupFile.getFileName(), "fileName");
        assertEquals(popupFile.getFileContent(), "fileContent");
        assertEquals(popupFile.getFileExtension(), "fileExtension");
        assertEquals(popupFile.getFileSize(), 100L);
        assertEquals(popupFile.getShowYn(), "Y");
        assertEquals(popupFile.getLink(), "http://gdic.co.kr/web/");

//        PopupFile popupFile = new PopupFile(1L, "fileName", "fileContent", "fileExtension", 100L, "Y", "http://gdic.co.kr/web/");
    }
}
