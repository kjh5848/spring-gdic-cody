package co.kr.nomadlab.springgdic.module.board.controller;

import co.kr.nomadlab.springgdic.exception.CustomException;
import co.kr.nomadlab.springgdic.module.board.model.BoardFile;
import co.kr.nomadlab.springgdic.module.board.service.BoardFileService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardFileService boardFileService;

    public BoardController(BoardFileService boardFileService) {
        this.boardFileService = boardFileService;
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) throws IOException {
        // fileId를 사용하여 BoardFile 엔티티를 조회합니다.
        Optional<BoardFile> optionalBoardFile = boardFileService.getBoardFile(fileId);
        if (optionalBoardFile.isEmpty()) {
            // 파일이 존재하지 않는 경우 에러 처리
            throw new CustomException("파일이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        BoardFile boardFile = optionalBoardFile.get();

        // 파일 다운로드를 위한 헤더 설정
        String encodedFileName = URLEncoder.encode(boardFile.getFileName().split("_")[1], StandardCharsets.UTF_8)
                .replace("+", "%20")
                .replace("%2F", "/")
                .replace("%5C", "\\")
                .replace("%7C", "|");
        String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);

        String content = boardFile.getFileContent();
        byte[] decoder = Base64.getDecoder().decode(content);
        InputStream is = new ByteArrayInputStream(decoder);
        InputStreamResource resource = new InputStreamResource(is);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }


}
