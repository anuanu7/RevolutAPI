package org.restlet.fact.domain;

import org.restlet.fact.exception.AccountCreationException;
import org.restlet.fact.exception.AccountNotFoundException;
import org.restlet.fact.exception.TransferException;
import org.restlet.fact.persistence.IAccountRepo;
import org.restlet.fact.persistence.entity.Account;
import org.restlet.fact.type.AccountTO;
import org.restlet.fact.type.CreateAccountRequest;
import org.restlet.fact.type.TransferAccountRequest;
import javax.inject.Inject;

public class AccountDOImpl implements IAccountDO {
    private final IAccountRepo accountRepo;

    @Inject
    AccountDOImpl(IAccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public AccountTO createAccount(CreateAccountRequest request) {
        if (request.getAmount() < 0) {
            throw new AccountCreationException("Invalid amount specified");
        } else {
            Account account = accountRepo.createAccount(request.getAmount());
            return convert(account);
        }

    }

    @Override
    public AccountTO getAccount(int id) {
        Account account = accountRepo.getAccount(id);
        if (account != null) {
            return convert(account);
        } else {
            throw new AccountNotFoundException("Account not found");
        }
    }

    @Override
    public void deleteAccount(int id) {
        accountRepo.deleteAccount(id);
    }

    @Override
    public AccountTO transfer(TransferAccountRequest transferAccountRequest) {
        Account from = accountRepo.getAccount(transferAccountRequest.getFromId());
        Account to = accountRepo.getAccount(transferAccountRequest.getToId());
        Double amount = transferAccountRequest.getAmount();
        if (from == null) {
            throw new TransferException("From Account is invalid");
        }
        if (to == null) {
            throw new TransferException("TO Account is invalid");
        }
        if (amount <= 0) {
            throw new TransferException("Invalid amount specified");
        }
        return convert(accountRepo.transfer(from, to, amount));

    }

    private AccountTO convert(Account account) {
        AccountTO accountTO = new AccountTO(account.getUserId(), account.getAmount());
        return accountTO;
    }
}
