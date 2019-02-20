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

    @Query(value = "SELECT w.id, w.amount, w.currency, w.user_id FROM wallet w " +
            "LEFT JOIN users u ON w.user_id = u.id " +
            "WHERE u.id = :userId", nativeQuery = true)
    List<Wallet> getAllUsersWallets(@Param("userId") Long userId);

}
