package co.kr.nomadlab.springgdic.module.board.dto;

import co.kr.nomadlab.springgdic.module.board.model.Board;
import co.kr.nomadlab.springgdic.module.board.status.BoardStatus;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record BoardSaveRequest(

    @NotBlank(message = "제목을 입력해주세요.")
    String title,
    @NotBlank(message = "내용을 입력해주세요.")
    String content,

    @Nullable
    List<MultipartFile> noticeFiles

) {
    public Board toEntity() {
        return new Board(null, title, content, 0, 0, BoardStatus.ACTIVE, null);
    }
}
