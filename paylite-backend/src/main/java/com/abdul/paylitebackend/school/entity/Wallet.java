package com.abdul.paylitebackend.school.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor
public class Wallet {

    @Id
    private Long id;

    @MapsId
    @ManyToOne
    @JoinColumn(name = "school_id")
    private Schools schools;
    private Double balance;

    public Wallet(Schools schools, Double balance) {
        this.schools = schools;
        this.id = schools.getId();
        this.balance = balance;
    }
}
