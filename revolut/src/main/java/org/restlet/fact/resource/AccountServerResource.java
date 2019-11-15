package org.restlet.fact.resource;

import org.restlet.fact.domain.IAccountDO;
import org.restlet.fact.exception.AccountNotFoundException;
import org.restlet.fact.type.AccountTO;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;

import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.Map;


public class AccountServerResource extends ServerResource {

    private final IAccountDO accountDO;
    private int id;

    @Override
    public void doInit() {
        Map<String, Object> attributes = this.getRequest().getAttributes();
        this.id = Integer.parseInt((String) attributes.get("id"));
    }

    @Inject
    AccountServerResource(IAccountDO accountDO) {
        this.accountDO = accountDO;
    }

    @Get("json")
    public AccountTO getAccount() {
        try {
            return accountDO.getAccount(id);
        } catch (AccountNotFoundException e) {
            throw new ResourceException(Response.Status.NOT_FOUND.getStatusCode(), e.getMessage());
        }
    }

    @Delete
    public void deleteAccount() {
        try {
            accountDO.deleteAccount(id);
        } catch (AccountNotFoundException e) {
            throw new ResourceException(Response.Status.NOT_FOUND.getStatusCode(), e.getMessage());
        }
    }

}
