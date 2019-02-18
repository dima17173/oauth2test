package com.impltech.testoauth.repository;

import com.impltech.testoauth.domain.User;
import com.impltech.testoauth.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by dima.
 * Creation date 14.02.19.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("SELECT " +
            "NEW com.impltech.testoauth.dto.WalletDTO( " +
            "wal.id, wal.currency, wal.amount) " +
            "FROM User u " +
            "INNER JOIN u.wallets wal " +
            "WHERE u.id = :id")
    List<Wallet> getAllUsersWallets(@Param("id") Long id);
}
