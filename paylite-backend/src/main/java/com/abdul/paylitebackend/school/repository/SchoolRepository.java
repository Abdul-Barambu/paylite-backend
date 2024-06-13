package com.abdul.paylitebackend.school.repository;

import com.abdul.paylitebackend.school.entity.Schools;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<Schools, Long> {

    Optional<Schools> findByEmail(String email);
    List<Schools> findAll();


    @Transactional
    @Modifying
    @Query("UPDATE Schools s SET s.enabled = TRUE WHERE s.email = ?1")
    int enabledSchool(String email);
}
