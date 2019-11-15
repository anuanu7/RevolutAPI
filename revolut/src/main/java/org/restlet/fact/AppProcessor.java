/*
Bank API main application to start the service
@auth Anu Gorla
 */
package org.restlet.fact;

import com.google.inject.Injector;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;

import org.restlet.data.Protocol;
import org.restlet.engine.Engine;
import org.restlet.fact.resource.AccountCreateServerResource;
import org.restlet.fact.resource.AccountServerResource;
import org.restlet.fact.resource.BalanceTransferServerResource;
import org.restlet.fact.resource.PingServerResource;
import org.restlet.routing.Router;

import java.util.logging.Logger;

public class AppProcessor extends Application {
    private Injector mInjector;

    public AppProcessor(Injector injector) {
        mInjector = injector;
    }

    public static void main(String[] args) throws Exception {
        LOGGER.info("Bank account transfer API application starting...");
        Injector injector = RestletGuice.createInjector(new AccountModule());
        AppProcessor app = new AppProcessor(injector);
        // Attach application to http://localhost:8080
        Component component = new Component();
        component.getServers().add(Protocol.HTTP, 8080);

        // Declare client connector based on the classloader
        component.getClients().add(Protocol.CLAP);

        // Look for the log configuration file in the current classloader
        component.getLogService().setLogPropertiesRef("clap:///logging.properties");

        component.getDefaultHost().attach("/v1", app);

        component.start();

        LOGGER.info(" Banking API started, Access using the following URL ");
        LOGGER.info("URL: http://localhost:8080/v1");
    }


    public static final Logger LOGGER = Engine.getLogger(AppProcessor.class);
    public static final String PING = "Version: 1.0.0 running";

    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext());

        FinderFactory finderFactory = this.mInjector.getInstance(FinderFactory.class);
        router.attach("/account", finderFactory.finder(AccountCreateServerResource.class));
        router.attach("/account/{id}", finderFactory.finder(AccountServerResource.class));
        router.attach("/account/balance/transfer", finderFactory.finder(BalanceTransferServerResource.class));
        router.attach("/ping", PingServerResource.class);
        return router;
    }
}
