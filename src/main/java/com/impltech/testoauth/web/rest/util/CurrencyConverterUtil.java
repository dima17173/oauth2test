package com.impltech.testoauth.web.rest.util;

import com.impltech.testoauth.config.EurValue;
import com.impltech.testoauth.config.UahValue;
import com.impltech.testoauth.config.UsdValue;
import com.impltech.testoauth.enumeration.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dima.
 * Creation date 15.02.19.
 */
@Component
public class CurrencyConverterUtil {

    private final EurValue eurValue;

    private final UsdValue usdValue;

    private final UahValue uahValue;

    @Autowired
    public CurrencyConverterUtil(EurValue eurValue, UsdValue usdValue, UahValue uahValue) {
        this.eurValue = eurValue;
        this.usdValue = usdValue;
        this.uahValue = uahValue;
    }

    private Double convertFromUah(Double amount, Currency fromCurrency, Currency toCurrency) {
        Double result = 0.0;
        if (fromCurrency.getCurrency().equals("uah")) {
            if (toCurrency.getCurrency().equals("usd")) {
                result = amount * uahValue.getUsd();
            } else {
                result = amount * uahValue.getEur();
            }
        }
        return result;
    }

    private Double convertFromUsd(Double amount, Currency fromCurrency, Currency toCurrency) {
        Double result = 0.0;
        if (fromCurrency.getCurrency().equals("usd")) {
            if (toCurrency.getCurrency().equals("eur")) {
                result = amount * usdValue.getEur();
            } else {
                result = amount * usdValue.getUah();
            }
        }
        return result;
    }

    private Double convertFromEur(Double amount, Currency fromCurrency, Currency toCurrency) {
        Double result = 0.0;
        if (fromCurrency.getCurrency().equals("eur")) {
            if (toCurrency.getCurrency().equals("usd")) {
                result = amount * eurValue.getUsd();
            } else {
                result = amount * eurValue.getUah();
            }
        }
        return result;
    }
}
