package co.kr.nomadlab.springgdic.repository;

import co.kr.nomadlab.springgdic.module.supply.status.SupplyStatus;
import co.kr.nomadlab.springgdic.module.supply.model.Supply;
import co.kr.nomadlab.springgdic.module.supply.model.SupplyRepository;
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
@DisplayName("SupplyRepository 테스트")
public class SupplyRepositoryTest {

    @Autowired
    private SupplyRepository supplyRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    public void init() {
        setUp("공급공고 제목", "공급공고 내용",  10, 5, SupplyStatus.ACTIVE);
    }

    public Supply setUp(String title, String content, Integer viewCount, Integer replyCount, SupplyStatus supplyStatus) {
        Supply supply = new Supply();
        supply.setTitle(title);
        supply.setContent(content);
        supply.setViewCount(viewCount);
        supply.setReplyCount(replyCount);
        supply.setSupplyStatus(supplyStatus);

        return testEntityManager.persist(supply);
    }

    @Test
    @DisplayName("공급공고 조회 테스트")
    void selectSupply() {

        List<Supply> supplyList = supplyRepository.findAll();
        Assertions.assertNotEquals(supplyList.size(), 0);

        Supply supply = supplyList.get(0);
        Assertions.assertEquals(supply.getTitle(), "【공고】김해대동첨단일반산업단지 단독주택용지 입찰공고 인기글첨부파일");

    }

    @Test
    @DisplayName("공급공고 등록 및 삭제 테스트")
    void insertSupply() {
        Supply supply = setUp("제목입니다.", "내용입니다.", 10, 5, SupplyStatus.ACTIVE);
        supplyRepository.findById(supply.getId()).ifPresentOrElse(
                findSupply -> Assertions.assertEquals(findSupply.getTitle(), "제목입니다."),
                Assertions::fail
        );

        testEntityManager.remove(supply);
        supplyRepository.findById(supply.getId()).ifPresentOrElse(
                findSupply -> Assertions.fail("공급공고가 존재하지 않습니다."),
                () -> Assertions.assertTrue(true)
        );

    }

    @Test
    @DisplayName("공급공고 수정 테스트")
    void updateSupply() {

        Supply supply = setUp("제목입니다.", "내용입니다.", 10, 5, SupplyStatus.ACTIVE);

        supplyRepository.findById(supply.getId()).ifPresentOrElse(
                findSupply -> {
                    Assertions.assertEquals(findSupply.getTitle(), "제목입니다.");
                    findSupply.setTitle("수정된 제목입니다.");
                    testEntityManager.persist(findSupply);
                },
                () -> Assertions.fail("공급공고가 존재하지 않습니다.")

        );

        supplyRepository.findById(supply.getId()).ifPresentOrElse(
                findSupply -> Assertions.assertEquals(findSupply.getTitle(), "수정된 제목입니다."),
                () -> Assertions.fail("공급공고가 존재하지 않습니다.")
        );
    }


}
