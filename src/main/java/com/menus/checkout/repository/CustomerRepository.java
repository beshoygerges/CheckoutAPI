package com.menus.checkout.repository;

import com.menus.checkout.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByEmailOrMobileNumber(String email, String mobileNumber);

}
