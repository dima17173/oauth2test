package com.impltech.testoauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by dima.
 * Creation date 15.02.19.
 */
@Configuration
@ConfigurationProperties(prefix = "currency.eur")
public class EurValue {

    private Double uah;
    private Double usd;

    public Double getUah() {
        return uah;
    }

    public void setUah(Double uah) {
        this.uah = uah;
    }

    public Double getUsd() {
        return usd;
    }

    public void setUsd(Double usd) {
        this.usd = usd;
    }
}
