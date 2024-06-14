package com.abdul.paylitebackend.school.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NumberOfSuccessfulTransactions {

    @Id
    private Long id;
    private String numberOfTransactions;
    @MapsId
    @ManyToOne
    @JoinColumn(name = "school_id")
    private Schools schools;

    public NumberOfSuccessfulTransactions(String numberOfTransactions, Schools schools) {
        this.numberOfTransactions = numberOfTransactions;
        this.schools = schools;
        this.id = schools.getId();
    }
}
