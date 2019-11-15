package org.restlet.fact.persistence;

import org.restlet.fact.exception.TransferException;
import org.restlet.fact.persistence.entity.Account;

public interface IAccountRepo {
    Account createAccount(Double amount);

    Account getAccount(int userId);

    Account deleteAccount(int userId);

    Account transfer(Account from, Account to, Double amount) throws TransferException;
}
