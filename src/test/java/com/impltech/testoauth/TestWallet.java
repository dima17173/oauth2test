package com.impltech.testoauth;

import com.impltech.testoauth.domain.User;
import com.impltech.testoauth.domain.Wallet;
import com.impltech.testoauth.enumeration.Currency;
import com.impltech.testoauth.service.WalletService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by dima.
 * Creation date 19.02.19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestOauthApplication.class)
@Transactional
@AutoConfigureMockMvc
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

        Double currAmount = 20.00;

        Wallet wallet1 = new Wallet();
        wallet1.setId(1L);
        wallet1.setAmount(currAmount);
        wallet1.setCurrency(Currency.UAH);
        wallet1.setUser(new User("dima", "pass"));

        walletService.add(1L, 33.3);
        Double amountAfterAdd = currAmount + 33.3;
        assertEquals(currAmount, amountAfterAdd);
    }
}
