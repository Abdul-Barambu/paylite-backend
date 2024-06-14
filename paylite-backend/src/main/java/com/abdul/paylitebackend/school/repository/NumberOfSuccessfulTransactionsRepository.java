package com.abdul.paylitebackend.school.repository;

import com.abdul.paylitebackend.school.entity.NumberOfSuccessfulTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NumberOfSuccessfulTransactionsRepository extends JpaRepository<NumberOfSuccessfulTransactions, Long> {
    Optional<NumberOfSuccessfulTransactions> findBySchoolsId(Long schoolId);

    List<NumberOfSuccessfulTransactions> findAll();
}
