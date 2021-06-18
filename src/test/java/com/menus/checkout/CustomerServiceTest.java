package com.menus.checkout;

import com.menus.checkout.exception.CustomerAlreadyExistException;
import com.menus.checkout.model.Customer;
import com.menus.checkout.repository.CustomerRepository;
import com.menus.checkout.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void addCustomerShouldPass() {
        Customer customer = new Customer();
        customer.setEmail("beshoy@gmail.com");
        customer.setName("beshoy gerges");
        customer.setMobileNumber("01205459968");

        given(customerRepository.existsByEmailOrMobileNumber(customer.getEmail(), customer.getMobileNumber())).willReturn(false);
        given(customerRepository.save(customer)).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        Customer savedCustomer = customerService.addCustomer(customer);

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getCart()).isNotNull();
    }

    @Test
    void addExistCustomerShouldThrow() {
        Customer customer = new Customer();
        customer.setEmail("beshoy@gmail.com");
        customer.setName("beshoy gerges");
        customer.setMobileNumber("01205459968");

        given(customerRepository.existsByEmailOrMobileNumber(customer.getEmail(), customer.getMobileNumber())).willReturn(true);

        assertThrows(CustomerAlreadyExistException.class, () -> customerService.addCustomer(customer));

        verify(customerRepository, never()).save(any(Customer.class));
    }

}
