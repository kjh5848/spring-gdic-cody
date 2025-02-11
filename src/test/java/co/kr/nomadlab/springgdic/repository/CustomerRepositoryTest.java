package co.kr.nomadlab.springgdic.repository;

import co.kr.nomadlab.springgdic.module.board.model.Customer;
import co.kr.nomadlab.springgdic.module.board.model.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("CustomerRepository 테스트")
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    public void init() {
        setUp("GDIC", "홍길동", "010-1234-5678", "경상남도 김해시", "관심고객 등록 테스트");
    }

    public Customer setUp(String enterpriseName, String managerName, String tel, String address, String inquiry) {
        Customer customer = new Customer();
        customer.setEnterpriseName(enterpriseName);
        customer.setManagerName(managerName);
        customer.setTel(tel);
        customer.setAddress(address);
        customer.setInquiry(inquiry);

        return testEntityManager.persist(customer);

    }

    @Test
    @DisplayName("관심고객 조회 테스트")
    void selectCustomer() {
        List<Customer> customerList = customerRepository.findAll();
        Assertions.assertNotEquals(customerList.size(), 0);

        Customer customer = customerList.get(0);
        Assertions.assertEquals(customer.getEnterpriseName(), "김해대동산업단지");
    }

    @Test
    @DisplayName("관심고객 등록 및 삭제 테스트")
    void insertCustomer() {
        Customer customer = setUp("Green", "이순신", "010-1234-5678", "부산진구 부전동", "문의 내용 알림");
        customerRepository.findById(customer.getId()).ifPresentOrElse(
                findCustomer -> Assertions.assertEquals(findCustomer.getEnterpriseName(), "Green"),
                () -> Assertions.fail("관심고객 등록 실패")
        );

        testEntityManager.remove(customer);
        customerRepository.findById(customer.getId()).ifPresentOrElse(
                findCustomer -> Assertions.fail("관심고객 삭제 실패"),
                () -> Assertions.assertTrue(true)
        );
    }

    @Test
    @DisplayName("관심고객 수정 테스트")
    void updateCustomer(){

        Customer customer = setUp("Green", "이순신", "010-1234-5678", "부산진구 부전동", "문의 내용 알림");

        customerRepository.findById(customer.getId()).ifPresentOrElse(
                findCustomer -> {
                    findCustomer.setEnterpriseName("Green");
                    testEntityManager.persist(findCustomer);
                },
                () -> {
                    Assertions.fail("관심고객 등록 실패");
                }
        );

        customerRepository.findById(customer.getId()).ifPresentOrElse(
                findCustomer -> Assertions.assertEquals(findCustomer.getEnterpriseName(), "Green"),
                () -> Assertions.fail("관심고객 등록 실패")
        );

    }


}
