
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
        classes.add(TemperaturaDiariaRecurso.class);
        classes.add(VelocidadeVentoRecurso.class);
        classes.add(TemperaturaRecurso.class);
        classes.add(AlturaNuvensRecurso.class);
        classes.add(CoberturaNuvensRecurso.class);
        classes.add(UmidadeRelativaRecurso.class);
        classes.add(VarsHojeRecurso.class);
        
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