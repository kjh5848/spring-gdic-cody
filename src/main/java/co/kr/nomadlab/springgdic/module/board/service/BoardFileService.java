package co.kr.nomadlab.springgdic.module.board.service;

import co.kr.nomadlab.springgdic.module.board.model.BoardFile;
import co.kr.nomadlab.springgdic.module.board.model.BoardFileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardFileService {

    private final BoardFileRepository boardFileRepository;

    public BoardFileService(BoardFileRepository boardFileRepository) {
        this.boardFileRepository = boardFileRepository;
    }

    public List<BoardFile> getBoardFileList(Long noticeId) {
        List<BoardFile> boardFileList = boardFileRepository.findByBoardId(noticeId);
        boardFileList
                .forEach(
                        boardFile -> {
                            boardFile.setFileName(boardFile.getFileName().split("_")[1]);
                        }
                );
        return boardFileList;
    }

    // 파일 단건 조회
    public Optional<BoardFile> getBoardFile(Long fileId) {
        return boardFileRepository.findById(fileId);
    }
}
