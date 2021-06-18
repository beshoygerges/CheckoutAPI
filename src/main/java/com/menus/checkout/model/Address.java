package com.menus.checkout.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    private String description;

    private Integer floorNumber;

    private Integer flatNumber;

    @JoinColumn(name = "customer_id")
    @ManyToOne(optional = false)
    private Customer customer;


}
