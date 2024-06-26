package com.abdul.paylitebackend.payer.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayerDetails {

    @Id
    @SequenceGenerator(name = "payerDetails_sequence", sequenceName = "payerDetails_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payerDetails_sequence")
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private Integer amount;
    private String service;
    private Long school_id;
    private String referenceNumber;

    public PayerDetails(String name, String email, String phoneNumber, Integer amount, String service, Long school_id, String referenceNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.amount = amount;
        this.service = service;
        this.school_id = school_id;
        this.referenceNumber = referenceNumber;
    }
}
