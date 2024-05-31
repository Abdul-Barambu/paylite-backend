package com.abdul.paylitebackend.school.repository;

import com.abdul.paylitebackend.school.entity.Schools;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<Schools, Long> {

    Optional<Schools> findByEmail(String email);
    List<Schools> findAll();
}
