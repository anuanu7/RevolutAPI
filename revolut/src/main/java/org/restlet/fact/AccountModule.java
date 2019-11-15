package org.restlet.fact;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;

import com.google.inject.ProvisionException;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.fact.domain.AccountDOImpl;
import org.restlet.fact.domain.IAccountDO;
import org.restlet.fact.persistence.AccountRepoImpl;
import org.restlet.fact.persistence.IAccountRepo;
import org.restlet.resource.Finder;
import org.restlet.resource.ServerResource;

import static java.util.Arrays.asList;

import java.lang.reflect.Type;

public class AccountModule extends AbstractModule implements FinderFactory {
    private final Iterable<? extends com.google.inject.Module> modules;

    /**
     * Creates a RestletGuice.Module that will install the given modules.
     */
    public AccountModule(com.google.inject.Module... modules) {
        this.modules = asList(modules);
    }

    /**
     * Creates a RestletGuice.Module that will install the given modules.
     */
    public AccountModule(Iterable<? extends com.google.inject.Module> modules) {
        this.modules = modules;
    }

    @Override
    protected void configure() {
        bind(IAccountDO.class).to(AccountDOImpl.class);
        bind(IAccountRepo.class).to(AccountRepoImpl.class);
        bind(FinderFactory.class).toInstance(this);

    }

    @Override
    public Finder finder(Class<?> cls) {
        return new ServerResourceKeyFinder(Key.get(cls));
    }

    class KeyFinder extends Finder {
        private final Class<?> targetClass;

        KeyFinder(Type type) {
            this.targetClass = (Class<?>) type;
        }

        @Override
        public final Context getContext() {
            return getInjector().getInstance(Context.class);
        }

        public final Class<? extends ServerResource> getTargetClass() {

            // If the key type is a subtype of ServerResource, return it.

            Class<ServerResource> src = ServerResource.class;
            if (src != null && targetClass != null && src.isAssignableFrom(targetClass)) {
                @SuppressWarnings("unchecked")
                Class<? extends ServerResource> result =
                        (Class<? extends ServerResource>) targetClass;
                return result;
            }

            return (Class<? extends ServerResource>) super.getTargetClass();
        }

        @Inject
        private volatile Injector injector;

        protected final Injector getInjector() {
            Injector inj = injector;
            if (inj == null) {
                synchronized (AccountModule.this) {
                    inj = injector;
                    if (inj == null) {
                        //System.err.println("Automatically creating injector.");
                        injector = inj = Guice.createInjector(AccountModule.this);
                    }
                }
            }
            return inj;
        }
    }


    class ServerResourceKeyFinder extends KeyFinder {
        private final Key<?> serverResourceKey;

        ServerResourceKeyFinder(Key<?> serverResourceKey) {
            super(serverResourceKey.getTypeLiteral().getType());
            this.serverResourceKey = serverResourceKey;
        }

        @Override
        public ServerResource create(Request request, Response response) {
            try {
                return ServerResource.class.cast(getInjector().getInstance(serverResourceKey));
            } catch (ClassCastException ex) {
                String msg = String.format("Must bind %s to ServerResource (or subclass)", serverResourceKey);
                throw new ProvisionException(msg, ex);
            }
        }
    }
}
