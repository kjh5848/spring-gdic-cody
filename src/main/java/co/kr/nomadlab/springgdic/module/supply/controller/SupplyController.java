package co.kr.nomadlab.springgdic.module.supply.controller;

import co.kr.nomadlab.springgdic.exception.CustomException;
import co.kr.nomadlab.springgdic.module.supply.model.Supply;
import co.kr.nomadlab.springgdic.module.supply.model.SupplyFile;
import co.kr.nomadlab.springgdic.module.supply.service.SupplyFileService;
import co.kr.nomadlab.springgdic.module.supply.service.SupplyService;
import co.kr.nomadlab.springgdic.util.PageUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/supply")
public class SupplyController {

    private final SupplyService supplyService;

    private final SupplyFileService supplyFileService;

    public SupplyController(SupplyService supplyService, SupplyFileService supplyFileService) {
        this.supplyService = supplyService;
        this.supplyFileService = supplyFileService;
    }

    // 토지이용계획 페이지
    @GetMapping("/usePlan")
    public String usePlan() {
        return "supply/usePlan";
    }

    // 용지별 공급 정보 페이지
    @GetMapping("informationByPaper")
    public String informationByPaper() {
        return "supply/informationByPaper";
    }


    // 공급정보 페이지
    @GetMapping("/notice")
    public String notice(@PageableDefault(sort = "id" ,direction = Sort.Direction.DESC, size = 15) Pageable pageable, Model model) {
        Page<Supply> supplyPage = supplyService.findAll(pageable);
//        PageUtil.set(pageable, model, supplyPage.getSort(), supplyPage.getTotalPages());
        PageUtil.set(pageable, model, supplyPage.getTotalPages());
        model.addAttribute("supplyPage", supplyPage);
        return "supply/notice";
    }

    @GetMapping("/notice/{id}")
    public String getSupply(@PathVariable Long id, Model model, Pageable pageable) {

        Page<Supply> supplyPage = supplyService.findAll(pageable);
        PageUtil.set(pageable, model, supplyPage.getTotalPages());
        model.addAttribute("supplyPage", supplyPage);

        Optional<Supply> optionalSupply = supplyService.findById(id);

        if (optionalSupply.isEmpty()) {
            throw new CustomException("해당 공급공고가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        List<SupplyFile> supplyFileList = supplyService.getSupplyFileList(optionalSupply.get().getId());

        model.addAttribute("fileList", supplyFileList);
        model.addAttribute("supply", optionalSupply.get());

        return "supply/detail";
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) throws IOException {

        // fileId를 사용하여 SupplyFile 엔티티를 조회합니다.
        Optional<SupplyFile> optionalSupplyFile = supplyFileService.getSupplyFile(fileId);
        if (optionalSupplyFile.isEmpty()) {
            // 파일이 존재하지 않는 경우 에러 처리
            throw new CustomException("파일이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        SupplyFile supplyFile = optionalSupplyFile.get();

        // 파일 다운로드를 위한 헤더 설정
        String encodedFileName = URLEncoder.encode(supplyFile.getFileName().split("_")[1], StandardCharsets.UTF_8)
                .replace("+", "%20")
                .replace("%2F", "/")
                .replace("%5C", "\\")
                .replace("%7C", "|");
        String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);

        String content = supplyFile.getFileContent();
        byte[] decoder = Base64.getDecoder().decode(content);
        InputStream is = new ByteArrayInputStream(decoder);
        InputStreamResource resource = new InputStreamResource(is);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
