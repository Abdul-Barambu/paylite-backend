package com.abdul.paylitebackend.school.service;

import com.abdul.paylitebackend.school.entity.Wallet;
import com.abdul.paylitebackend.school.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    public void saveWallet(Wallet wallet) {
        walletRepository.save(wallet);
    }
}
