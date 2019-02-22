package com.impltech.testoauth;

import com.impltech.testoauth.domain.User;
import com.impltech.testoauth.domain.Wallet;
import com.impltech.testoauth.enumeration.Currency;
import com.impltech.testoauth.repository.WalletRepository;
import com.impltech.testoauth.web.rest.WalletResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static junit.framework.TestCase.*;
import static org.mockito.Mockito.when;

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
    private WalletResource walletResource;

    @Autowired
    private WalletRepository walletRepository;


    @Before
    public void setUp() {
    }

    @Test
    public void addBalance() {

        Double walletAmount = 20.00;

        Wallet wallet1 = new Wallet();
        wallet1.setId(1L);
        wallet1.setAmount(walletAmount);
        wallet1.setCurrency(Currency.UAH);
        wallet1.setUser(new User("dima", "pass"));

    }
}
