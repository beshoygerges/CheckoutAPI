package com.menus.checkout.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "[Transaction]")
public class Transaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cardToken;
    private BigDecimal amount;
    private String status;
    private String currency;
    private boolean refunded;
    private boolean paid;
    private String paymentRequestId;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "Order_Id")
    @OneToOne(optional = false)
    private Order order;
}
