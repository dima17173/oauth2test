package com.impltech.testoauth.repository;

import com.impltech.testoauth.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by dima.
 * Creation date 14.02.19.
 */
public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
