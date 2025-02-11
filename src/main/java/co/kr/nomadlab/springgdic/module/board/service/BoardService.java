package co.kr.nomadlab.springgdic.module.board.service;

import co.kr.nomadlab.springgdic.exception.CustomException;
import co.kr.nomadlab.springgdic.module.board.dto.BoardSaveRequest;
import co.kr.nomadlab.springgdic.module.board.dto.BoardUpdateRequest;
import co.kr.nomadlab.springgdic.module.board.model.Board;
import co.kr.nomadlab.springgdic.module.board.model.BoardFile;
import co.kr.nomadlab.springgdic.module.board.model.BoardFileRepository;
import co.kr.nomadlab.springgdic.module.board.model.BoardRepository;
import co.kr.nomadlab.springgdic.module.board.status.BoardStatus;
import co.kr.nomadlab.springgdic.util.FileUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    private final BoardFileRepository boardFileRepository;

    private final FileUtil fileUtil;

    private final String dirName = "notice";

    public BoardService(BoardRepository boardRepository, BoardFileRepository boardFileRepository, FileUtil fileUtil) {
        this.boardRepository = boardRepository;
        this.boardFileRepository = boardFileRepository;
        this.fileUtil = fileUtil;
    }

    public Page<Board> findAll(Pageable pageable) {
        return boardRepository.findAllByBoardStatus(pageable, BoardStatus.ACTIVE);
    }

    @Transactional
    public void saveNotice(BoardSaveRequest request) {
        // TODO: Security 적용 후 User 정보를 가져와서 등록해야함.
        Board board = boardRepository.save(request.toEntity());

        if (request.noticeFiles() != null) {
            boardFileSave(board, request.noticeFiles());
        }

    }

    public Optional<Board> saveCustomer() {
        return null;
    }

    @Transactional
    public void updateNotice(BoardUpdateRequest request, Board board) {
        if (request.deleteIds() != null) {
            request.deleteIds()
                    .forEach(id -> {
                        boardRepository.findById(id).orElseThrow(()-> new CustomException("존재하지 않는 파일입니다.", HttpStatus.BAD_REQUEST));
                        boardFileRepository.deleteById(id);
                    });
        }

        if (request.noticeFiles() != null) {
            boardFileSave(board, request.noticeFiles());
        }

        board.setTitle(request.title());
        board.setContent(request.content());

        // TODO: Security 적용 후 User 정보를 가져와서 등록해야함.
        board.setUser(null);

        boardRepository.save(board);
    }

    private void boardFileSave(Board board, List<MultipartFile> files) {
        if (!files.get(0).isEmpty()) {
            files.forEach(file -> {
                BoardFile boardFile = null;
                try {
                    boardFile = new BoardFile(
                            null,
                            fileUtil.getFileName(file),
    //                        dirName + fileUtil.getFileName(file, dirName),
                            Base64.getEncoder().encodeToString(file.getBytes()),
                            fileUtil.getFileExtension(file),
                            file.getSize(),
                            board
                    );
                } catch (IOException e) {
                    throw new CustomException("파일 저장에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
                }
                boardFileRepository.save(boardFile);
            });
        }
    }

    public Optional<Board> getBoard(Long id) {
        return boardRepository.findById(id);
    }

    @Transactional
    public void deleteNotice(Board board) {
        board.setBoardStatus(BoardStatus.DELETE);

        boardRepository.save(board);
    }
}
