package com.abdul.paylitebackend.payer.repository;

import com.abdul.paylitebackend.payer.entities.PayerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayerRepository extends JpaRepository<PayerDetails, Long> {

    List<PayerDetails> findAll();
}
