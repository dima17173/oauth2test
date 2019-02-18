package com.impltech.testoauth.service;

import com.impltech.testoauth.domain.Wallet;
import com.impltech.testoauth.exception.LowBalanceException;
import com.impltech.testoauth.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by dima.
 * Creation date 14.02.19.
 */
@Service
@Transactional
public class WalletService {

    private final Logger log = LoggerFactory.getLogger(WalletService.class);

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional
    public boolean replenishBalance(Long fromWallet, Long toWallet, Double amount) {
        Assert.notNull(amount, "amount can't be null");
            try {
                subtract(fromWallet, amount);
                add(toWallet, amount);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

    @Transactional
    public void add(Long userWalletId, Double amount) {
        if (amount != null && amount > 0) {
            Wallet userWallet = walletRepository.getOne(userWalletId);
            userWallet.setAmount(userWallet.getAmount() + amount);
            walletRepository.save(userWallet);
        }
    }

    @Transactional
    public void subtract(Long userWalletId, Double amount) {
        if (amount != null && amount > 0) {
            Wallet userWallet = walletRepository.getOne(userWalletId);
            Double currentAmount = userWallet.getAmount();
            if (currentAmount < amount) {
                throw new LowBalanceException(String.format("Low balance", currentAmount));
            }
            currentAmount -= amount;
            userWallet.setAmount(currentAmount);
            walletRepository.save(userWallet);
        }
    }

    /**
     * Delete the wallet by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Wallet : {}", id);
        walletRepository.deleteById(id);
    }
}
