package com.impltech.testoauth.service;

import com.impltech.testoauth.domain.User;
import com.impltech.testoauth.domain.Wallet;
import com.impltech.testoauth.exception.LimitException;
import com.impltech.testoauth.repository.UserRepository;
import com.impltech.testoauth.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created by dima.
 * Creation date 14.02.19.
 */
@Service
@Transactional
public class WalletService {

    private final Logger log = LoggerFactory.getLogger(WalletService.class);

    private final WalletRepository walletRepository;

    private final UserRepository userRepository;

    public WalletService(WalletRepository walletRepository, UserRepository userRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    /**
     * Replenish balance from one wallet to another wallet
     */
    @Transactional
    public boolean replenishBalance(Long fromWallet, Long toWallet, Double amount) {
        subtract(fromWallet, amount);
        add(toWallet, amount);
        return true;
    }

    /**
     * Add balance
     */
    @Transactional
    public void add(Long userWalletId, Double amount) {
        if (amount != null) {
            Wallet userWallet = walletRepository.getOne(userWalletId);
            Double currentAmount = userWallet.getAmount();
            currentAmount += amount;
            userWallet.setAmount(currentAmount);
            walletRepository.save(userWallet);
        }
    }

    /**
     * Subtract balance
     */
    @Transactional
    public void subtract(Long userWalletId, Double amount) {
        if (amount != null) {
            Wallet userWallet = walletRepository.getOne(userWalletId);
            Double currentAmount = userWallet.getAmount();
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

    /**
     * Add new wallet.
     */
    @Transactional
    public Wallet addWallet(Long userId, Wallet wallet) {
        List<Wallet> userWallets = walletRepository.getAllUsersWallets(userId);
        User user = userRepository.getOne(userId);

        if (userWallets.size() < 3) {
            Wallet newWallet = walletRepository.save(wallet);
            user.getWallets().add(newWallet);
            userRepository.save(user);
            return newWallet;
        } else {
            throw new LimitException("You can create only 3 wallets");
        }
    }

    /**
     * Get allUsersWallets by userId.
     */
    @Transactional
    public List<Wallet> getAllUserWallets(Long userId) {
        return walletRepository.getAllUsersWallets(userId);
    }
}
