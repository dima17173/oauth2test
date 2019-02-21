package com.impltech.testoauth.domain;


import com.impltech.testoauth.enumeration.Currency;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dima.
 * Creation date 14.02.19.
 */
@Entity
@Table(name = "wallet")
public class Wallet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency")
    @Enumerated(value = EnumType.STRING)
    private Currency currency;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="users_id")
    private User user;

    public Wallet() {
    }

    public Wallet(Currency currency, Double amount, User user) {
        this.currency = currency;
        this.amount = amount;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getAmount() {

        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wallet wallet = (Wallet) o;

        if (Double.compare(wallet.amount, amount) != 0) return false;
        if (id != null ? !id.equals(wallet.id) : wallet.id != null) return false;
        if (currency != null ? !currency.equals(wallet.currency) : wallet.currency != null) return false;
        return user != null ? user.equals(wallet.user) : wallet.user == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", currency=" + currency +
                ", amount=" + amount +
                ", user=" + user +
                '}';
    }
}
