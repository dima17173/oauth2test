package com.impltech.testoauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by dima.
 * Creation date 15.02.19.
 */
@Configuration
@ConfigurationProperties(prefix = "currency.uah")
public class UahValue {

        private Double usd;
        private Double eur;

        public Double getUsd() {
            return usd;
        }

        public void setUsd(Double usd) {
            this.usd = usd;
        }

        public Double getEur() {
            return eur;
        }

        public void setEur(Double eur) {
            this.eur = eur;
        }
    }
