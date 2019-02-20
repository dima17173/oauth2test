package com.impltech.testoauth.enumeration;

/**
 * Created by dima.
 * Creation date 15.02.19.
 */
public enum Currency {

    UAH("uah"),
    USD("usd"),
    EUR("eur");

    private final String currency;

    Currency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public static Currency getValue(String currency) {
        switch (currency) {
            case "uah":
                return UAH;
            case "usd":
                return USD;
            default:
                return EUR;
        }
    }
}
