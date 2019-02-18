package com.impltech.testoauth.dto;


import com.impltech.testoauth.enumeration.Currency;

/**
 * Created by dima.
 * Creation date 15.02.19.
 */
public class WalletDTO {
    private Long id;
    private Currency currency;
    private Double amount;

    public WalletDTO() {
    }

    public WalletDTO(Long id, Currency currency, Double amount) {
        this.id = id;
        this.currency = currency;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
