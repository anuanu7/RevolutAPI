package org.restlet.fact.resource;

import org.restlet.data.Status;
import org.restlet.fact.domain.IAccountDO;
import org.restlet.fact.exception.TransferException;
import org.restlet.fact.type.AccountTO;
import org.restlet.fact.type.TransferAccountRequest;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.inject.Inject;

public class BalanceTransferServerResource extends ServerResource {

    private final IAccountDO accountDO;

    @Inject
    BalanceTransferServerResource(IAccountDO accountDO) {
        this.accountDO = accountDO;
    }

    @Post("json")
    public AccountTO transfer(TransferAccountRequest transferAccountRequest) {
        try {
            return accountDO.transfer(transferAccountRequest);
        } catch (TransferException e) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e.getMessage());
        }
    }

}
