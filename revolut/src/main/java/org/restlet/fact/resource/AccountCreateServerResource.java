package org.restlet.fact.resource;

import org.restlet.fact.domain.IAccountDO;
import org.restlet.fact.exception.AccountCreationException;
import org.restlet.fact.type.AccountTO;
import org.restlet.fact.type.CreateAccountRequest;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.inject.Inject;
import javax.ws.rs.core.Response;


public class AccountCreateServerResource extends ServerResource {

    private final IAccountDO accountDO;

    @Inject
    AccountCreateServerResource(IAccountDO accountDO) {
        this.accountDO = accountDO;
    }

    @Post("json")
    public AccountTO createAccount(CreateAccountRequest accountRequest) {
        try {
            return accountDO.createAccount(accountRequest);
        } catch (AccountCreationException e) {
            throw new ResourceException(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage());
        }
    }

}
