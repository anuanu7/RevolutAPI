package org.restlet.fact.persistence;

import org.restlet.fact.exception.TransferException;
import org.restlet.fact.persistence.entity.Account;

import javax.inject.Singleton;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Singleton
public class AccountRepoImpl implements IAccountRepo {
    private AtomicInteger userCounter = new AtomicInteger(0);
    private static Map<Integer, Account> users = new ConcurrentHashMap<>();

    @Override
    public Account createAccount(Double amount) {
        int userId = userCounter.incrementAndGet();
        users.put(userId, new Account(userId, new AtomicReference<>(amount)));
        return users.get(userId);

    }

    @Override
    public Account getAccount(int userId) {
        return users.get(userId);
    }

    @Override
    public Account deleteAccount(int userId) {
        return users.remove(userId);
    }

    @Override
    public Account transfer(Account from, Account to, Double amount) throws TransferException {

        Account accountFrom = users.get(from.getUserId());
        Account accountTo = users.get(to.getUserId());
        if (accountFrom == null || accountTo == null || accountFrom.equals(accountTo)) {

            throw new TransferException("Invalid Request, Please specify valid account details");

        } else if (accountFrom.getAmount().get() < amount) {
            throw new TransferException("Invalid Request, Insufficient funds");

        }
        transferAmount(accountFrom, accountTo, amount);
        return accountFrom;


    }

    private synchronized void transferAmount(Account from, Account to, Double amount) {
        from.getAmount().updateAndGet(balance -> balance - amount);
        to.getAmount().updateAndGet(balance -> balance + amount);
    }
}
