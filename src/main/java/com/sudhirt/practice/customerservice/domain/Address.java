package com.sudhirt.practice.customerservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"})
public class Address {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    private String id;
    @Column(nullable = false)
    private String address1;
    private String address2;
    @Column(nullable = false)
    private String city;
    private String state;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String zip;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
