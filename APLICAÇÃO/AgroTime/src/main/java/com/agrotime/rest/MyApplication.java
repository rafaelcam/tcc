
package com.agrotime.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 * JAX-RS application.
 *
 * @author Jonathan Benoit (jonathan.benoit at oracle.com)
 */
@ApplicationPath("/rest/")
public class MyApplication extends Application {
	
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(PessoaRecurso.class);
        classes.add(TemperaturaDiariaRecurso.class);
        return classes;
    }

    @Override
    public Set<Object> getSingletons() {
        final Set<Object> instances = new HashSet<Object>();
        instances.add(new JacksonFeature());
        instances.add(new LoggingFilter());
        return instances;
    }
}