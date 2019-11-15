package org.restlet.fact;

import org.restlet.resource.Finder;

public interface FinderFactory {
    /**
     * Returns a Finder that will obtain a dependency-injected instance of
     * the ServerResource subtype bound to the type associated with the given class.
     * @throws com.google.inject.ProvisionException if {@code cls} is not bound to ServerResource or a subclass.
     */
    Finder finder(Class<?> cls);

}
