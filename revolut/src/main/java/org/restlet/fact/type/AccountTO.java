package org.restlet.fact.type;

import java.util.concurrent.atomic.AtomicReference;

public class AccountTO {
    int userId;
    public AtomicReference<Double> amount;

    public AccountTO(int userId, AtomicReference<Double> amount) {
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
    public String toString() {
        return "Account{" +
                "userId=" + userId +
                ", amount=" + amount +
                '}';
    }
}
