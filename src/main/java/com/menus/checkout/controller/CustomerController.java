package com.menus.checkout.controller;

import com.menus.checkout.dto.CustomerDto;
import com.menus.checkout.dto.Response;
import com.menus.checkout.model.Customer;
import com.menus.checkout.service.CustomerService;
import com.menus.checkout.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public Response<CustomerDto> addCustomer(@Valid @RequestBody CustomerDto customerDto) {
        Customer customer = customerService.addCustomer(ModelMapperUtil.map(customerDto, Customer.class));
        return Response.of(ModelMapperUtil.map(customer, CustomerDto.class));
    }

}
