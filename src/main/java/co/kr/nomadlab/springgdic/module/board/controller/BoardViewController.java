package co.kr.nomadlab.springgdic.module.board.controller;

import co.kr.nomadlab.springgdic.exception.CustomException;
import co.kr.nomadlab.springgdic.module.board.model.Board;
import co.kr.nomadlab.springgdic.module.board.model.BoardFile;
import co.kr.nomadlab.springgdic.module.board.service.BoardFileService;
import co.kr.nomadlab.springgdic.module.board.service.BoardService;
import co.kr.nomadlab.springgdic.util.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/board")
public class BoardViewController {

    private final BoardService boardService;

    private final BoardFileService boardFileService;

    public BoardViewController(BoardService boardService, BoardFileService boardFileService) {
        this.boardService = boardService;
        this.boardFileService = boardFileService;
    }


    // 공지사항 페이지
    @GetMapping("/notice")
    public String notice(
            @PageableDefault(sort = "id" ,direction = Sort.Direction.DESC, size = 15)
            Pageable pageable,
            Model model
    ) {
        Page<Board> boardPage = boardService.findAll(pageable);
        PageUtil.set(pageable, model, boardPage.getTotalPages());
        model.addAttribute("boardPage", boardPage);
        return "board/notice";
    }


    @GetMapping("/notice/{id}")
    public String getNotice(@PathVariable Long id, Model model, Pageable pageable) {

        // 목록 보기 위한 pageable 추가
        Page<Board> boardPage = boardService.findAll(pageable);
        PageUtil.set(pageable, model, boardPage.getTotalPages());
        model.addAttribute("boardPage", boardPage);

        Optional<Board> optionalBoard = boardService.getBoard(id);
        if (optionalBoard.isEmpty()){
            throw new CustomException("공지사항이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        List<BoardFile> boardFileList = boardFileService.getBoardFileList(optionalBoard.get().getId());

        model.addAttribute("fileList", boardFileList);
        model.addAttribute("board", optionalBoard.get());

        return "board/detail";
    }


    // 관심고객등록 페이지
    @GetMapping("/registerCustomer")
    public String registerCustomer() {
        return "board/registerCustomer";
    }



}
