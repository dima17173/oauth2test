package com.impltech.testoauth.web.rest;

import com.impltech.testoauth.domain.Wallet;
import com.impltech.testoauth.exception.LimitException;
import com.impltech.testoauth.service.UserService;
import com.impltech.testoauth.web.rest.util.HeaderUtil;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by dima.
 * Creation date 14.02.19.
 */
public class WalletResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private static final String ENTITY_NAME = "wallet";

    private final UserService userService;

    public WalletResource(UserService userService) {
        this.userService = userService;
    }

    /**
     * POST  /wallet : Create a new wallet.
     *
     * @param userId the wallet to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request) if the wallet has already an ID
     */
    @PostMapping("/{id}/wallet/new")
    public ResponseEntity<?> createUserWallet(@PathVariable("id") Long userId,
                                              @RequestBody Wallet wallet) throws LimitException {
        Wallet createdWallet = userService.addWallet(userId, wallet);
        return ResponseEntity.ok().body(createdWallet);
    }

    /**
     * DELETE  /wallet/:id : delete the "id" wallet.
     *
     * @param id the id of the wallet to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{id}")
    @Timed
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("REST request to delete wallet : {}", id);
        userService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
