package co.kr.nomadlab.springgdic.module.user.admin.popup;

import co.kr.nomadlab.springgdic.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PopupService {

    private final PopupFileRepository popupFileRepository;

    private final FileUtil fileUtil;

    public PopupService(PopupFileRepository popupFileRepository, FileUtil fileUtil) {
        this.popupFileRepository = popupFileRepository;
        this.fileUtil = fileUtil;
    }

    @Transactional
    public PopupFile savePopup(PopupRequest popupRequest) throws IOException {
        if (popupRequest.file() != null ) {

            if (popupRequest.file().getSize() > 0) {
                popupFileRepository.deleteAll();

                MultipartFile file = popupRequest.file();
                String fileName = fileUtil.getFileName(file);
                String fileContent = Base64.getEncoder().encodeToString(file.getBytes());
                String fileExtension = fileUtil.getFileExtension(file);
                long fileSize = file.getSize();

                PopupFile popupFile = new PopupFile(null, fileName, fileContent, fileExtension, fileSize, popupRequest.showYn(), popupRequest.link());
                return popupFileRepository.save(popupFile);
            } else {
                PopupFile popupFile = getPopup().get(0);
                popupFile.setShowYn(popupRequest.showYn());
                popupFile.setLink(popupRequest.link());
                return popupFile;
            }
        } else {
            return null;
        }
    }

    public List<PopupFile> getPopup() {
        return popupFileRepository.findAll();
    }

    public void showPopup() {

    }
}
