package com.abdul.paylitebackend.school.repository;

import com.abdul.paylitebackend.school.entity.AccountVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AccountVerificationRepository extends JpaRepository<AccountVerification, Long> {

    Optional<AccountVerification> findByVerificationToken(String verificationToken);

    @Transactional
    @Modifying
    @Query("UPDATE AccountVerification av SET av.confirmedAt = ?2 WHERE av.verificationToken = ?1")
    int updateConfirmedAt(String verificationToken, LocalDateTime confirmedAt);
}
