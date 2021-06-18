package com.menus.checkout.service.impl;

import com.menus.checkout.exception.CustomerAlreadyExistException;
import com.menus.checkout.model.Cart;
import com.menus.checkout.model.Customer;
import com.menus.checkout.repository.CustomerRepository;
import com.menus.checkout.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    @Override
    public Customer addCustomer(Customer customer) {
        isCustomerExist(customer);
        createCart(customer);
        return customerRepository.save(customer);
    }

    private void createCart(Customer customer) {
        Cart cart = new Cart();
        cart.setCustomer(customer);
        customer.setCart(cart);
    }

    private void isCustomerExist(Customer customer) {
        if (customerRepository
                .existsByEmailOrMobileNumber(customer.getEmail(), customer.getMobileNumber())) {
            throw new CustomerAlreadyExistException("Customer is already exist");
        }
    }
}
