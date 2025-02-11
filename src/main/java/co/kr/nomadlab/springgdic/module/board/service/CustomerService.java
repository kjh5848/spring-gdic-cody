package co.kr.nomadlab.springgdic.module.board.service;

import co.kr.nomadlab.springgdic.module.board.dto.CustomerRequest;
import co.kr.nomadlab.springgdic.module.board.model.Customer;
import co.kr.nomadlab.springgdic.module.board.model.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer saveRegister(CustomerRequest customerRequest) {
        return customerRepository.save(customerRequest.toEntity());
    }

    public List<Customer> getCustomer() {
        return customerRepository.findAll();
    }

    public Page<Customer> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }
}
