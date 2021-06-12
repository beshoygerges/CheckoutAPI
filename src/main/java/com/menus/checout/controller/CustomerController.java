package com.menus.checout.controller;

import com.menus.checout.dto.CustomerDto;
import com.menus.checout.dto.Response;
import com.menus.checout.service.CustomerService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public Response<CustomerDto> addCustomer(@Valid @RequestBody CustomerDto customerDto) {
        return Response.of(customerService.addCustomer(customerDto));
    }

}
