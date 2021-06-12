package com.menus.checout.model;

import com.menus.checout.constants.PaymentMethod;
import com.menus.checout.constants.Status;
import com.menus.checout.constants.ShippingMethod;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @JoinColumn(name = "customer_id")
    @ManyToOne(optional = false)
    private Customer customer;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pk.order")
    private List<OrderItem> orderItems;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "order")
    private Transaction transaction;


}
