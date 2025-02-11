package co.kr.nomadlab.springgdic.module.supply.request;

import co.kr.nomadlab.springgdic.module.supply.model.Supply;
import co.kr.nomadlab.springgdic.module.supply.status.SupplyStatus;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record SupplySaveRequest(

    @NotBlank(message = "제목을 입력해주세요.")
    String title,

    @NotBlank(message = "내용을 입력해주세요.")
    String content,

    @Nullable
    List<MultipartFile> supplyFiles

) {
    public Supply toEntity() {
        return new Supply(null, title, content, 0, 0, SupplyStatus.ACTIVE, null);
    }
}
