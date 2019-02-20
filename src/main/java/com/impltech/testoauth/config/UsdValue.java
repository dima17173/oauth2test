package com.impltech.testoauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by dima.
 * Creation date 15.02.19.
 */
@Configuration
@ConfigurationProperties(prefix = "currency.usd")
public class UsdValue {

    private Double uah;
    private Double eur;

    public Double getUah() {
        return uah;
    }

    public void setUah(Double uah) {
        this.uah = uah;
    }

    public Double getEur() {
        return eur;
    }

    public void setEur(Double eur) {
        this.eur = eur;
    }
}
