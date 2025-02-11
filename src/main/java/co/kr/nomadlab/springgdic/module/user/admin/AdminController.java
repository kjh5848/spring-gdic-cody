package co.kr.nomadlab.springgdic.module.user.admin;

import co.kr.nomadlab.springgdic.exception.CustomException;
import co.kr.nomadlab.springgdic.module.board.dto.BoardSaveRequest;
import co.kr.nomadlab.springgdic.module.board.dto.BoardUpdateRequest;
import co.kr.nomadlab.springgdic.module.board.model.Board;
import co.kr.nomadlab.springgdic.module.board.model.BoardFile;
import co.kr.nomadlab.springgdic.module.board.model.Customer;
import co.kr.nomadlab.springgdic.module.board.service.BoardFileService;
import co.kr.nomadlab.springgdic.module.board.service.BoardService;
import co.kr.nomadlab.springgdic.module.board.service.CustomerService;
import co.kr.nomadlab.springgdic.module.supply.model.Supply;
import co.kr.nomadlab.springgdic.module.supply.model.SupplyFile;
import co.kr.nomadlab.springgdic.module.supply.request.SupplySaveRequest;
import co.kr.nomadlab.springgdic.module.supply.request.SupplyUpdateRequest;
import co.kr.nomadlab.springgdic.module.supply.service.SupplyService;
import co.kr.nomadlab.springgdic.module.user.admin.popup.PopupFile;
import co.kr.nomadlab.springgdic.module.user.admin.popup.PopupRequest;
import co.kr.nomadlab.springgdic.module.user.admin.popup.PopupService;
import co.kr.nomadlab.springgdic.util.Base64Decoded;
import co.kr.nomadlab.springgdic.util.PageUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BoardService boardService;

    private final BoardFileService boardFileService;

    private final SupplyService supplyService;

    private final CustomerService customerService;

    private final PopupService popupService;

    public AdminController(BoardService boardService, BoardFileService boardFileService, SupplyService supplyService, CustomerService customerService, PopupService popupService) {
        this.boardService = boardService;
        this.boardFileService = boardFileService;
        this.supplyService = supplyService;
        this.customerService = customerService;
        this.popupService = popupService;
    }

    @GetMapping({"/", ""})
    public String main(
            @PageableDefault(sort = "id" ,direction = Sort.Direction.DESC, size = 15)
            Pageable pageable,
            Model model
    ) {
        Page<Customer> customerPage = customerService.findAll(pageable);
        PageUtil.set(pageable, model, customerPage.getTotalPages());

            model.addAttribute("customerPage", customerPage);

        return "admin/main";
    }

    @GetMapping("/notice")
    public String notice(
            @PageableDefault(sort = "id" ,direction = Sort.Direction.DESC, size = 15)
            Pageable pageable,
            Model model) {
        Page<Board> page = boardService.findAll(pageable);
        PageUtil.set(pageable, model, page.getTotalPages());
        model.addAttribute("page", page);

        return "admin/notice";
    }

    @GetMapping("/notice/add")
    public String noticeAdd() {
        return "admin/notice_add";
    }

    @GetMapping("/notice/{noticeId}/edit")
    public String noticeEdit(
            @PathVariable Long noticeId,
            Model model
    ) {
        Optional<Board> optionalBoard = boardService.getBoard(noticeId);
        if (optionalBoard.isEmpty()) {
            throw new CustomException("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        List<BoardFile> boardFileList = boardFileService.getBoardFileList(noticeId);

        model.addAttribute("board", optionalBoard.get());
        model.addAttribute("fileList", boardFileList);

        return "admin/notice_edit";
    }

    @GetMapping("/supply")
    public String supply(
            @PageableDefault(sort = "id" ,direction = Sort.Direction.DESC, size = 15)
            Pageable pageable,
            Model model) {
        Page<Supply> page = supplyService.findAll(pageable);
        PageUtil.set(pageable, model, page.getTotalPages());
        model.addAttribute("page", page);

        return "admin/supply";
    }

    @GetMapping("/supply/add")
    public String supplyAdd() {
        return "admin/supply_add";
    }

    @GetMapping("/supply/{supplyId}/edit")
    public String supplyEdit(
            @PathVariable Long supplyId,
            Model model
    ) {
        Optional<Supply> optionalSupply = supplyService.getSupply(supplyId);
        if (optionalSupply.isEmpty()) {
            throw new CustomException("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        List<SupplyFile> supplyFileList = supplyService.getSupplyFileList(supplyId);

        model.addAttribute("supply", optionalSupply.get());
        model.addAttribute("fileList", supplyFileList);

        return "admin/supply_edit";
    }

    @GetMapping("/popup")
    public String popup(Model model) throws IOException {

        List<PopupFile> popupFileList = popupService.getPopup();

        PopupFile popupFile = popupFileList.get(0);

        model.addAttribute("popupFile", popupFile);
        return "admin/popup";
    }

    @PostMapping("/notice-save")
    public String saveNotice(@Valid BoardSaveRequest boardSaveRequest, Errors errors) {
        if (errors.hasErrors()){
            throw new CustomException(errors.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        boardService.saveNotice(boardSaveRequest);

        return "redirect:/admin/notice";
    }

    @PostMapping("/notice-edit/{noticeId}")
    public String editNotice(@PathVariable Long noticeId, @Valid BoardUpdateRequest request, Errors errors) {
        if (errors.hasErrors()){
            throw new CustomException(errors.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        Optional<Board> optionalBoard = boardService.getBoard(noticeId);
        optionalBoard.orElseThrow(() -> new CustomException("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND));
        Board board = optionalBoard.get();
        if (errors.hasErrors()){
            throw new CustomException(errors.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        boardService.updateNotice(request, board);

        return "redirect:/admin/notice";
    }

    @PostMapping("/notice-delete/{noticeId}")
    public String deleteNotice(@PathVariable Long noticeId) {
        Optional<Board> optionalBoard = boardService.getBoard(noticeId);
        optionalBoard.orElseThrow(() -> new CustomException("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND));
        Board board = optionalBoard.get();

        boardService.deleteNotice(board);

        return "redirect:/admin/notice";
    }

    @PostMapping("/supply-save")
    public String saveSupply(@Valid SupplySaveRequest supplySaveRequest, Errors errors) {

        if (errors.hasErrors()){
            throw new CustomException(errors.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        supplyService.saveSupply(supplySaveRequest);

        return "redirect:/admin/supply";
    }

    @PostMapping("/supply-edit/{supplyId}")
    public String editSupply(@PathVariable Long supplyId, @Valid SupplyUpdateRequest request, Errors errors) {

        Optional<Supply> optionalSupply = supplyService.getSupply(supplyId);
        optionalSupply.orElseThrow(() -> new CustomException("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND));
        if (errors.hasErrors()){
            throw new CustomException(errors.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        supplyService.updateSupply(request, optionalSupply.get());

        return "redirect:/admin/supply";
    }

    @PostMapping("/supply-delete/{supplyId}")
    public String deleteSupply(@PathVariable Long supplyId) {
        Optional<Supply> optionalSupply = supplyService.getSupply(supplyId);
        optionalSupply.orElseThrow(() -> new CustomException("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND));

        supplyService.deleteSupply(optionalSupply.get());

        return "redirect:/admin/supply";
    }

    @PostMapping("/popup")
    public String popupPost(PopupRequest popupRequest, Model model) throws IOException {
        PopupFile popupFile = popupService.savePopup(popupRequest);

        if (popupFile == null) {
            throw new CustomException("팝업 등록에 실패하였습니다.", HttpStatus.BAD_REQUEST);
        }

        model.addAttribute("popupFile", popupFile);
        return "redirect:/admin/popup";
    }

    @PostMapping("/popup/show")
    public String popupShow() {
        popupService.showPopup();
        return "redirect:/admin/popup";
    }
}
