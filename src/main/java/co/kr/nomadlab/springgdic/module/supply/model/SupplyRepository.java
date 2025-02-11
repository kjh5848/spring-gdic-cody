package co.kr.nomadlab.springgdic.module.supply.model;

import co.kr.nomadlab.springgdic.module.supply.status.SupplyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplyRepository extends JpaRepository<Supply, Long> {
    Page<Supply> findAllBySupplyStatus(Pageable pageable, SupplyStatus boardStatus);
}
