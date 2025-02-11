package co.kr.nomadlab.springgdic.module.supply.service;

import co.kr.nomadlab.springgdic.module.board.model.BoardFile;
import co.kr.nomadlab.springgdic.module.board.model.BoardFileRepository;
import co.kr.nomadlab.springgdic.module.supply.model.SupplyFile;
import co.kr.nomadlab.springgdic.module.supply.model.SupplyFileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplyFileService {

    private final SupplyFileRepository supplyFileRepository;

    public SupplyFileService(SupplyFileRepository supplyFileRepository) {
        this.supplyFileRepository = supplyFileRepository;
    }

    public List<SupplyFile> getBoardFileList(Long supplyId) {
        List<SupplyFile> supplyFileList = supplyFileRepository.findBySupplyId(supplyId);
        supplyFileList
                .forEach(
                        supplyFile -> {
                            supplyFile.setFileName(supplyFile.getFileName().split("_")[1]);
                        }
                );
        return supplyFileList;
    }

    // 파일 단건 조회
    public Optional<SupplyFile> getSupplyFile(Long fileId) {
        return supplyFileRepository.findById(fileId);
    }

    public List<SupplyFile> getSupplyFileList() {
        return supplyFileRepository.findAll();
    }
}
