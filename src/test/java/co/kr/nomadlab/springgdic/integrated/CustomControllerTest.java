package co.kr.nomadlab.springgdic.integrated;

import co.kr.nomadlab.springgdic.module.board.dto.CustomerRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

public class CustomControllerTest extends AbstractIntegrated {

    @Test
    @DisplayName("관심고객등록 성공")
    public void successCustomer() {
        CustomerRequest customerRequest = new CustomerRequest("업체명", "담당자명", "010-1234-5678", "주소", "문의내용");
        client.post()
                .uri("/customer/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(customerRequest), CustomerRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader()
                .contentType("application/json;charset=UTF-8")
                .expectBody(String.class)
                .value(response -> {
                        Assertions.assertEquals("success", response);
                    });

    }

    @Test
    @DisplayName("관심고객등록 실패 (업체명 미입력)")
    void failCustomer() {
        CustomerRequest customerRequest = new CustomerRequest("", "담당자명", "010-1234-5678", "주소", "문의내용");
        client.post()
                .uri("/customer/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(customerRequest), CustomerRequest.class)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader()
                .contentType("application/json;charset=UTF-8")
                .expectBody(String.class)
                .value(response -> {
                    Assertions.assertEquals("업체명은 필수입니다.", response);
                });

    }

}
