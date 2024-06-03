package com.abdul.paylitebackend.school.repository;

import com.abdul.paylitebackend.school.entity.Schools;
import com.abdul.paylitebackend.school.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
