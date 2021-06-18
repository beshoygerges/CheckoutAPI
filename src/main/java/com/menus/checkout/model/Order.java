package com.menus.checkout.model;

import com.menus.checkout.constants.PaymentMethod;
import com.menus.checkout.constants.ShippingMethod;
import com.menus.checkout.constants.Status;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "[Order]")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal subTotalPrice;
    private BigDecimal totalPrice;
    private boolean useCustomerBalance;
    private BigDecimal shippingFees;
    private BigDecimal codFees;
    private BigDecimal vatFees;
    private BigDecimal usedCustomerBalance;
    private BigDecimal paidAmount;
    @Enumerated(EnumType.STRING)
    private ShippingMethod shippingMethod;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Enumerated(EnumType.STRING)
    private Status status = Status.DECLINED;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "customer_id")
    @ManyToOne(optional = false)
    private Customer customer;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pk.order")
    private List<OrderItem> orderItems;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "order")
    private Transaction transaction;


}
