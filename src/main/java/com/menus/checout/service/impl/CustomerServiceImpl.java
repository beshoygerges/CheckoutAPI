package com.menus.checout.service.impl;

import com.menus.checout.dto.CustomerDto;
import com.menus.checout.model.Cart;
import com.menus.checout.model.Customer;
import com.menus.checout.repository.CustomerRepository;
import com.menus.checout.service.CustomerService;
import com.menus.checout.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    @Override
    public CustomerDto addCustomer(CustomerDto customerDto) {
        isCustomerExist(customerDto);
        Customer customer = createCustomer(customerDto);
        createCart(customer);
        return ModelMapperUtil.map(customerRepository.save(customer), CustomerDto.class);
    }

    private void createCart(Customer customer) {
        Cart cart = new Cart();
        cart.setCustomer(customer);
        customer.setCart(cart);
    }

    private Customer createCustomer(CustomerDto customerDto) {
        return ModelMapperUtil.map(customerDto, Customer.class);
    }

    private void isCustomerExist(CustomerDto customerDto) {
        if (customerRepository
            .existsByEmailOrMobileNumber(customerDto.getEmail(), customerDto.getMobileNumber())) {
            throw new RuntimeException("Customer is already exist");
        }
    }
}
