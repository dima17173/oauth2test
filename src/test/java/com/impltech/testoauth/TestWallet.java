package com.impltech.testoauth;

import com.impltech.testoauth.domain.User;
import com.impltech.testoauth.domain.Wallet;
import com.impltech.testoauth.enumeration.Currency;
import com.impltech.testoauth.service.WalletService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by dima.
 * Creation date 19.02.19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TestWallet {

    @Autowired
    private WalletService walletService;

    public Wallet createWallet() {
        Wallet wallet = new Wallet();
        wallet.setAmount(22.2);
        wallet.setCurrency(Currency.UAH);
        wallet.setUser(new User("dima", "pass"));
        return wallet;
    }

    @Before
    public void setUp() {
    }

    @Test
    public void addBalance() {
        Wallet wallet = new Wallet();
        wallet.setAmount(22.2);
        wallet.setCurrency(Currency.UAH);
        wallet.setUser(new User("dima", "pass"));

        walletService.add(wallet.getId(), 33.3);
    }
}
