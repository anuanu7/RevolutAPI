package org.restlet.fact;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;

public class RestletGuice {

    /**
     * Creates an injector from the given modules with FinderFactory
     * bound to an implementation that uses the injector's bindings to create
     * Finder instances.
     */
    public static Injector createInjector(com.google.inject.Module... modules) {
        return injectorFor(null, new AccountModule(modules));
    }

    private static Injector injectorFor(Stage stage, Module rootModule) {
        if (stage == null) {
            return Guice.createInjector(rootModule);
        } else {
            return Guice.createInjector(stage, rootModule);
        }
    }
}