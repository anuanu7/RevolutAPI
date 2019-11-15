package org.restlet.fact.resource;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.fact.AppProcessor;

public class PingServerResource extends ServerResource {

    @Get("txt")
    public String ping() {
        return AppProcessor.PING;
    }

}
