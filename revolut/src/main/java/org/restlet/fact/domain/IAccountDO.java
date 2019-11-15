package org.restlet.fact.domain;

import org.restlet.fact.type.AccountTO;
import org.restlet.fact.type.CreateAccountRequest;
import org.restlet.fact.type.TransferAccountRequest;

public interface IAccountDO {
    AccountTO createAccount(CreateAccountRequest request);

    AccountTO getAccount(int id);

    void deleteAccount(int id);

    AccountTO transfer(TransferAccountRequest transferAccountRequest);
}
