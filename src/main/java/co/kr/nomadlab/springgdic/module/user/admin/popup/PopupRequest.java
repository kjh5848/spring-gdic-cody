package co.kr.nomadlab.springgdic.module.user.admin.popup;

import jakarta.annotation.Nullable;
import org.springframework.web.multipart.MultipartFile;


public record PopupRequest(
        @Nullable
        MultipartFile file,

        String showYn,
        String link
) {

}
