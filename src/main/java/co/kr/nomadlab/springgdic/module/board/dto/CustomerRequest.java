package co.kr.nomadlab.springgdic.module.board.dto;

import co.kr.nomadlab.springgdic.module.board.model.Customer;
import jakarta.validation.constraints.NotBlank;

public record CustomerRequest(
        @NotBlank(message = "업체명은 필수입니다.")
        String enterpriseName,
        @NotBlank(message = "담당자명은 필수입니다.")
        String managerName,
        @NotBlank(message = "연락처는 필수입니다.")
        String tel,
        @NotBlank(message = "주소는 필수입니다.")
        String address,
        @NotBlank(message = "문의내용은 필수입니다.")
        String inquiry
) {

        public Customer toEntity() {
                return new Customer(null, enterpriseName, managerName, tel, address, inquiry);
        }
}
