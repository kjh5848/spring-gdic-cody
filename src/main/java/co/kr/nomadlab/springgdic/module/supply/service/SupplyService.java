package co.kr.nomadlab.springgdic.module.supply.service;

import co.kr.nomadlab.springgdic.exception.CustomException;
import co.kr.nomadlab.springgdic.module.supply.model.Supply;
import co.kr.nomadlab.springgdic.module.supply.model.SupplyFile;
import co.kr.nomadlab.springgdic.module.supply.model.SupplyFileRepository;
import co.kr.nomadlab.springgdic.module.supply.model.SupplyRepository;
import co.kr.nomadlab.springgdic.module.supply.request.SupplySaveRequest;
import co.kr.nomadlab.springgdic.module.supply.request.SupplyUpdateRequest;
import co.kr.nomadlab.springgdic.module.supply.status.SupplyStatus;
import co.kr.nomadlab.springgdic.util.FileUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SupplyService {

    private final SupplyRepository supplyRepository;

    private final SupplyFileRepository supplyFileRepository;

    private final FileUtil fileUtil;

    private final String dirName = "supply";

    public SupplyService(SupplyRepository supplyRepository, SupplyFileRepository supplyFileRepository, FileUtil fileUtil) {
        this.supplyRepository = supplyRepository;
        this.supplyFileRepository = supplyFileRepository;
        this.fileUtil = fileUtil;
    }

    public List<Supply> findAll() {
        return supplyRepository.findAll();
    }

    public Page<Supply> findAll(Pageable pageable) {
        return supplyRepository.findAllBySupplyStatus(pageable, SupplyStatus.ACTIVE);
    }

    public Optional<Supply> findById(Long id) {
        return supplyRepository.findById(id);
    }

    public Optional<Supply> getSupply(Long supplyId) {
        return supplyRepository.findById(supplyId);
    }

    public List<SupplyFile> getSupplyFileList(Long supplyId) {
        List<SupplyFile> supplyFileList = supplyFileRepository.findBySupplyId(supplyId);
        supplyFileList
                .forEach(
                        supplyFile -> {
                            supplyFile.setFileName(supplyFile.getFileName().split("_")[1]);
                        }
                );
        return supplyFileList;
    }

    @Transactional
    public void saveSupply(SupplySaveRequest request) {
        // TODO: Security 적용 후 User 정보를 가져와서 등록해야함.
        Supply supply = supplyRepository.save(request.toEntity());

        if (request.supplyFiles() != null) {
            supplyFileSave(supply, request.supplyFiles());
        }
    }

    private void supplyFileSave(Supply supply, List<MultipartFile> files) {
        if (!files.get(0).isEmpty()) {
            files.forEach(file -> {
                SupplyFile supplyFile = null;
                try {
                    supplyFile = new SupplyFile(
                            null,
                            fileUtil.getFileName(file),
                            Base64.getEncoder().encodeToString(file.getBytes()),
                            fileUtil.getFileExtension(file),
                            file.getSize(),
                            supply
                    );
                } catch (IOException e) {
                    throw new CustomException("파일 저장에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
                }
                supplyFileRepository.save(supplyFile);
            });
        }
    }

    @Transactional
    public void updateSupply(SupplyUpdateRequest request, Supply supply) {
        if (request.deleteIds() != null) {
            request.deleteIds()
                    .forEach(id -> {
                        supplyRepository.findById(id).orElseThrow(()-> new CustomException("존재하지 않는 파일입니다.", HttpStatus.BAD_REQUEST));
                        supplyFileRepository.deleteById(id);
                    });
        }

        if (request.supplyFiles() != null) {
            supplyFileSave(supply, request.supplyFiles());
        }

        supply.setTitle(request.title());
        supply.setContent(request.content());

        // TODO: Security 적용 후 User 정보를 가져와서 등록해야함.
        supply.setUser(null);

        supplyRepository.save(supply);
    }

    @Transactional
    public void deleteSupply(Supply supply) {
        supply.setSupplyStatus(SupplyStatus.DELETE);
        supplyRepository.save(supply);
    }
}
