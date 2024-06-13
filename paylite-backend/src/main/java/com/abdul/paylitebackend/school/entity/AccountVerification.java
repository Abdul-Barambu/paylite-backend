package com.abdul.paylitebackend.school.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountVerification {

    @Id
    private Long id;
    private String verificationToken;
    private LocalDateTime createdAt;
    private LocalDateTime confirmedAt;
    private LocalDateTime expiresAt;
    @MapsId
    @ManyToOne
    @JoinColumn(name = "school_id")
    private Schools schools;

    public AccountVerification(String verificationToken,
                               LocalDateTime createdAt,
                               LocalDateTime expiresAt,
                               Schools schools) {
        this.verificationToken = verificationToken;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.schools = schools;
        this.id = schools.getId();
    }
}
