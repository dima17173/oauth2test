package com.impltech.testoauth.service;

import com.impltech.testoauth.domain.User;
import com.impltech.testoauth.domain.Wallet;
import com.impltech.testoauth.exception.LimitException;
import com.impltech.testoauth.repository.UserRepository;
import com.impltech.testoauth.repository.WalletRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by dima.
 * Creation date 14.02.19.
 */
@Service("userDetailsService")
@Transactional
public class UserService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final WalletRepository walletRepository;

    public UserService(UserRepository userRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    /**
     * Save a user.
     *
     * @param user the entity to save
     * @return the persisted entity
     */
    public User create(User user) {
        log.debug("Request to save User : {}", user);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        log.debug("Request to get all Users");
        return userRepository.findAll();
    }

    /**
     * Get one user by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<User> findOne(Long id) {
        log.debug("Request to get Users : {}", id);
        return userRepository.findById(id);
    }

    /**
     * Delete the user by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Users : {}", id);
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public Wallet addWallet(Long userId, Wallet wallet) {
        List<Wallet> userWallets = userRepository.getAllUsersWallets(userId);
        User user = userRepository.getOne(userId);

        if (userWallets.size() < 3) {
            Wallet savedWallet = walletRepository.save(wallet);
            user.getWallets().add(savedWallet);
            userRepository.save(user);
            return savedWallet;
        } else {
            throw new LimitException("You can create only 3 different wallets!");
        }
    }

    @Transactional
    public List<Wallet> getAllUserWallets(Long userId) {
        return userRepository.getAllUsersWallets(userId);
    }

}