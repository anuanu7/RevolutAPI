package org.restlet.fact.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.restlet.fact.exception.TransferException;
import org.restlet.fact.persistence.AccountRepoImpl;
import org.restlet.fact.persistence.entity.Account;

import static org.junit.jupiter.api.Assertions.*;

class AccountRepoImplTest {
    AccountRepoImpl accountRepo;

    @BeforeEach
    void setUp() {
        accountRepo = new AccountRepoImpl();
    }

    @Test
    void createAccount() {
       Account accountCreated = accountRepo.createAccount(444.44);
        Account account = accountRepo.getAccount(accountCreated.getUserId());
        assertEquals(444.44, account.getAmount().get());
    }

    @Test
    void deleteAccount() {
        Account accountCreated = accountRepo.createAccount(444.44);
        accountRepo.deleteAccount(accountCreated.getUserId());
        Account deletedAcc = accountRepo.getAccount(accountCreated.getUserId());
        assertNull(deletedAcc);
    }

    @Test
    void transfer() {
        Account user1 = accountRepo.createAccount(444.44);
        Account account1 = accountRepo.getAccount(user1.getUserId());

       Account user2 = accountRepo.createAccount(444.44);
        Account account2 = accountRepo.getAccount(user2.getUserId());
        accountRepo.transfer(account1, account2, 41.00);
        assertEquals(accountRepo.getAccount(user1.getUserId()).getAmount().get(), 403.44);
        assertEquals(accountRepo.getAccount(user2.getUserId()).getAmount().get(), 485.44);
    }

    @Test
    void transferThrowsException() {
        Account user1 = accountRepo.createAccount(444.44);
        Account account1 = accountRepo.getAccount(user1.getUserId());
        Account user2 = accountRepo.createAccount(444.44);
        Account account2 = accountRepo.getAccount(user2.getUserId());
        try {
            accountRepo.transfer(account1, account2, 541.00);
            fail("Expected Transfer exception");
        } catch (TransferException e) {
            assertTrue(e.getMessage() != null);
        }
    }


}
