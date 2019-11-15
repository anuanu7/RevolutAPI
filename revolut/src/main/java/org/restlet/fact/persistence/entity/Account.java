package org.restlet.fact.persistence.entity;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class Account {
    int userId;
    public AtomicReference<Double> amount;

    public Account(int userId, AtomicReference<Double> amount) {
        this.userId = userId;
        this.amount = amount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public AtomicReference<Double> getAmount() {
        return amount;
    }

    public void setAmount(AtomicReference<Double> amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return userId == account.userId &&
                amount.equals(account.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, amount);
    }

    @Override
    public String toString() {
        return "Account{" +
                "userId=" + userId +
                ", amount=" + amount +
                '}';
    }
}
