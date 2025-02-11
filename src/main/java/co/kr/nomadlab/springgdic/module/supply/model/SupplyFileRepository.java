package co.kr.nomadlab.springgdic.module.supply.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplyFileRepository extends JpaRepository<SupplyFile, Long> {

    List<SupplyFile> findBySupplyId(Long boardId);
}
