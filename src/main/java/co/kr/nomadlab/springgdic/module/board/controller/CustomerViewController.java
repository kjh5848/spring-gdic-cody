package co.kr.nomadlab.springgdic.module.board.controller;

import co.kr.nomadlab.springgdic.exception.CustomRestException;
import co.kr.nomadlab.springgdic.module.board.dto.CustomerRequest;
import co.kr.nomadlab.springgdic.module.board.model.Customer;
import co.kr.nomadlab.springgdic.module.board.service.CustomerService;
import co.kr.nomadlab.springgdic.util.PageUtil;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerViewController {

    private final CustomerService customerService;

    public CustomerViewController(CustomerService customerService) {
        this.customerService = customerService;
    }
    // 관심고객등록 페이지
    @GetMapping("/registerCustomer")
    public String registerCustomer() {
        return "board/registerCustomer";
    }


    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> register(@RequestBody @Valid CustomerRequest customerRequest, Errors errors) {
        if (errors.hasErrors()){
            throw new CustomRestException(errors.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        customerService.saveRegister(customerRequest);

        return ResponseEntity.ok("success");
    }

}
