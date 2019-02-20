package com.impltech.testoauth.web.rest;

import com.impltech.testoauth.domain.Wallet;
import com.impltech.testoauth.exception.LimitException;
import com.impltech.testoauth.service.WalletService;
import com.impltech.testoauth.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.micrometer.core.annotation.Timed;

/**
 * Created by dima.
 * Creation date 14.02.19.
 */
@RestController
@RequestMapping("/api")
public class WalletResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private static final String ENTITY_NAME = "wallet";

    private final WalletService walletService;

    public WalletResource(WalletService walletService) {
        this.walletService = walletService;
    }

    /**
     * POST  /wallet : Create a new wallet.
     *
     * @param userId the wallet to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request) if the wallet has already an ID
     */
    @PostMapping("/{id}/wallet/new")
    public ResponseEntity<?> createWallet(@PathVariable("id") Long userId, @RequestBody Wallet wallet) throws LimitException {
        Wallet createdWallet = walletService.addWallet(userId, wallet);
        return ResponseEntity.ok().body(createdWallet);
    }

    /**
     * DELETE  /wallet/:id : delete the "id" wallet.
     *
     * @param id the id of the wallet to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wallet/{id}")
    @Timed
    public ResponseEntity<Void> deleteWallet(@PathVariable Long id) {
        log.debug("REST request to delete wallet : {}", id);
        walletService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * PUT  /amount : Updates balance after replenish.
     */
    @PutMapping("/{fromId}/replenish/{toId}")
    public ResponseEntity<?> replenishUserBalance(@PathVariable("fromId") Long fromId, @RequestParam("amount") Double amount, @PathVariable("toId") Long toId) {
        return ResponseEntity.ok().body(walletService.replenishBalance(fromId, toId, amount));
    }

    /**
     * PUT  /amount : Updates balance after add.
     */
    @PutMapping("/{id}/amount/add")
    public ResponseEntity<?> addBalance(@PathVariable("id") Long walletId, @RequestParam("amount") Double amount) {
        walletService.add(walletId, amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * PUT  /amount : Updates balance after reduce.
     */
    @PutMapping("/{id}/amount/reduce")
    public ResponseEntity<?> reduceBalance(@PathVariable("id") Long walletId, @RequestParam("amount") Double amount) {
        walletService.subtract(walletId, amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
