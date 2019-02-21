package com.impltech.testoauth;

import com.impltech.testoauth.domain.User;
import com.impltech.testoauth.domain.Wallet;
import com.impltech.testoauth.enumeration.Currency;
import com.impltech.testoauth.repository.WalletRepository;
import com.impltech.testoauth.service.WalletService;
import com.impltech.testoauth.web.rest.WalletResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static junit.framework.TestCase.*;

/**
 * Created by dima.
 * Creation date 19.02.19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestOauthApplication.class)
@Transactional
@AutoConfigureMockMvc
public class TestWallet {

    @Mock
    private WalletService walletService;

    @Mock
    private WalletResource walletResource;

    @Mock
    private WalletRepository walletRepository;

    @Before
    public void setUp() {
    }

    @Test
    public void addBalance() {

        Double walletAmount = 20.00;
        Double amountToAdd = 30.00;

        Wallet wallet1 = new Wallet();
        wallet1.setId(1L);
        wallet1.setAmount(walletAmount);
        wallet1.setCurrency(Currency.UAH);
        wallet1.setUser(new User("dima", "pass"));

        walletResource.addBalance(wallet1.getId(), amountToAdd);
        Double currentAmount = wallet1.getAmount();
        assertEquals(50.0, currentAmount);
    }
}
