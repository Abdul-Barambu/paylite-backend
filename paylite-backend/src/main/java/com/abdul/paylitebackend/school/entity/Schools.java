package com.abdul.paylitebackend.school.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Schools implements UserDetails {

    @Id
    @SequenceGenerator(name = "school_sequence", sequenceName = "school_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "school_sequence")
    private long id;
    @Column(
            nullable = false
    )
    private String name;
    @Column(
            nullable = false
    )
    private String email;
    @Column(
            nullable = false
    )
    private String password;

//    nullable fields
    private String address;
    private String phoneNumber;
    private String state;
    private String bankName;
    private String accountName;
    private String accountNumber;
    @Lob
    private byte[] logo;
    @Lob
    private byte[] documents;
    private boolean enabled = true;
    private boolean locked = false;
    @Enumerated(EnumType.STRING)
    private SchoolRole schoolRole;


    public Schools(String name, String email, String password, SchoolRole schoolRole) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.schoolRole = schoolRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(schoolRole.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
