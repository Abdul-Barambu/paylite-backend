package com.abdul.paylitebackend.payer.repository;

import com.abdul.paylitebackend.payer.entities.PayerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayerRepository extends JpaRepository<PayerDetails, Long> {
}
