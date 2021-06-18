package com.menus.checkout;

import com.menus.checkout.model.Product;
import com.menus.checkout.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@RequiredArgsConstructor
@SpringBootApplication
public class CheckoutApplication implements CommandLineRunner {

    private final ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(CheckoutApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        productRepository.save(Product.builder()
            .available(true)
            .name("Nike shoes")
            .price(BigDecimal.valueOf(250))
            .quantity(5)
            .sku("AB1000")
            .available(true)
            .build());

        productRepository.save(Product.builder()
            .available(true)
            .name("Adidas shoes")
            .price(BigDecimal.valueOf(350))
            .quantity(6)
            .sku("AB1001")
            .available(true)
            .build());

        productRepository.save(Product.builder()
            .available(true)
            .name("Puma shoes")
            .price(BigDecimal.valueOf(450))
            .quantity(3)
            .sku("AB1002")
            .available(true)
            .build());
    }
}
