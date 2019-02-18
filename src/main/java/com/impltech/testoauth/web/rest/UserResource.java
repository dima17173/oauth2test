package com.impltech.testoauth.web.rest;

import com.impltech.testoauth.domain.User;
import com.impltech.testoauth.domain.Wallet;
import com.impltech.testoauth.service.UserService;
import com.impltech.testoauth.service.WalletService;
import com.impltech.testoauth.web.rest.util.HeaderUtil;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * Created by dima.
 * Creation date 14.02.19.
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private static final String ENTITY_NAME = "user";

    private final UserService userService;

    private final WalletService walletService;

    public UserResource(UserService userService, WalletService walletService) {
        this.userService = userService;
        this.walletService = walletService;
    }

    /**
     * POST  /users : Create a new user.
     *
     * @param user the user to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request) if the user has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user")
    @Timed
    public ResponseEntity<User> createUser(@RequestBody User user) throws URISyntaxException {
        log.debug("REST request to save User : {}", user);
        if (user.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new user cannot already have an ID")).body(null);
        }
        User result = userService.create(user);
        return ResponseEntity.created(new URI("/api/user/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /users : Updates an existing user.
     *
     * @param user the user to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user,
     * or with status 400 (Bad Request) if the user is not valid,
     * or with status 500 (Internal Server Error) if the user couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user")
    @Timed
    public ResponseEntity<User> updateUser(@RequestBody User user) throws URISyntaxException {
        log.debug("REST request to update User : {}", user);
        if (user.getId() == null) {
            return createUser(user);
        }
        User result = userService.create(user);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, user.getId().toString()))
                .body(result);
    }

    /**
     * GET  /users : get all the users.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of users in body
     */
    @GetMapping("/users")
    @Timed
    public List<User> getAllUsers() {
        log.debug("REST request to get all Users");
        return userService.findAll();
    }

    /**
     * GET  /users/:id : get the "id" users.
     *
     * @param id the id of the user to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the user, or with status 404 (Not Found)
     */
    @GetMapping("/users/{id}")
    @Timed
    public Optional<User> getUser(@PathVariable Long id) {
        log.debug("REST request to get user : {}", id);
        return userService.findOne(id);
    }

    /**
     * DELETE  /users/:id : delete the "id" user.
     *
     * @param id the id of the user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{id}")
    @Timed
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("REST request to delete user : {}", id);
        userService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /users/wallets : get the "id" users/wallets.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the users/wallets, or with status 404 (Not Found)
     */
    @GetMapping("/{id}/wallets")
    public ResponseEntity<?> getUserWallets(@PathVariable("id") Long userId) {
        return ResponseEntity.ok().body(userService.getAllUserWallets(userId));
    }

    /**
     * PUT  /amount : Updates balance after add.
     */
    @PutMapping("/{id}/amount/add")
    public ResponseEntity<?> addBalance(@PathVariable("id") Long userId,
                                        @RequestParam("amount") Double amount) {
        List<Wallet> userWallets = userService.getAllUserWallets(userId);
        Wallet wallet = userWallets.get(0);
        walletService.add(wallet.getId(), amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * PUT  /amount : Updates balance after reduce.
     */
    @PutMapping("/{id}/amount/reduce")
    public ResponseEntity<?> reduceBalance(@PathVariable("id") Long userId,
                                           @RequestParam("amount") Double amount) {
        List<Wallet> userWallets = userService.getAllUserWallets(userId);
        Wallet wallet = userWallets.get(0);
        walletService.subtract(wallet.getId(), amount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{fromId}/replenish/{toId}")
    public ResponseEntity<?> replenishUserBalance(@PathVariable("fromId") Long fromId,
                                                    @RequestParam("amount") Double amount,
                                                    @PathVariable("toId") Long toId) {
        List<Wallet> fromWallets = userService.getAllUserWallets(fromId);
        Wallet fromWallet = fromWallets.get(0);
        List<Wallet> toWallets = userService.getAllUserWallets(toId);
        Wallet toWallet = toWallets.get(0);
        boolean isReplenished = walletService.replenishBalance(fromWallet.getId(), toWallet.getId(), amount);
        return ResponseEntity.ok().body(isReplenished);
    }

}