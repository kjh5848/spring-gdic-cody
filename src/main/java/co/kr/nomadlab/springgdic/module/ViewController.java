package co.kr.nomadlab.springgdic.module;


import co.kr.nomadlab.springgdic.module.user.admin.popup.PopupFile;
import co.kr.nomadlab.springgdic.module.user.admin.popup.PopupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewController {

    private final PopupService popupService;

    public ViewController(PopupService popupService) {
        this.popupService = popupService;
    }

    @GetMapping({"/", ""})
    public String main(Model model) {

        List<PopupFile> popupList = popupService.getPopup();
        PopupFile popupFile = popupList.get(0);
        model.addAttribute("popupFile", popupFile);
        return "index.html";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
