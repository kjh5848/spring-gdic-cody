package co.kr.nomadlab.springgdic.module.supply.request;

import co.kr.nomadlab.springgdic.module.board.model.Board;
import co.kr.nomadlab.springgdic.module.board.status.BoardStatus;
import co.kr.nomadlab.springgdic.module.supply.model.Supply;
import co.kr.nomadlab.springgdic.module.supply.status.SupplyStatus;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record SupplyUpdateRequest(

    @NotBlank(message = "제목을 입력해주세요.")
    String title,

    @NotBlank(message = "내용을 입력해주세요.")
    String content,

    @Nullable
    List<MultipartFile> supplyFiles,

    @Nullable
    List<Long> deleteIds

) {
    public Supply toEntity() {
        return new Supply(null, title, content, 0, 0, SupplyStatus.ACTIVE, null);
    }
}
